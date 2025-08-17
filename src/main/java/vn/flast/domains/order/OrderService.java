package vn.flast.domains.order;
/**************************************************************************/
/*  vn.flast.domains.order.OrderService.java                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.controller.BaseController;
import vn.flast.entities.order.OrderDetail;
import vn.flast.entities.order.OrderResponse;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.models.CustomerOrderStatus;
import vn.flast.models.Data;
import vn.flast.orchestration.EventDelegate;
import vn.flast.orchestration.EventTopic;
import vn.flast.orchestration.Message;
import vn.flast.orchestration.MessageInterface;
import vn.flast.orchestration.Publisher;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.*;
import vn.flast.searchs.OrderFilter;
import vn.flast.service.DataService;
import vn.flast.utils.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Service("orderService")
@Log4j2
public class OrderService  implements Publisher, Serializable {

    private EventDelegate eventDelegate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private CustomerPersonalRepository customerRepository;

    @Autowired
    private CustomerContractRepository customerContractRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private CustomerOrderDetailRepository detailRepository;

    @Autowired
    private BaseController baseController;

    @Autowired
    private CustomerOrderStatusRepository statusOrderRepository;

    @Transactional(rollbackFor = Exception.class)
    public CustomerOrder saveOpportunity(OrderInput input) {

        var order = new CustomerOrder();
        order.setCode(OrderUtils.createOrderCode());
        order.setUserCreateUsername(Common.getSsoId());
        order.setUserCreateId(Common.getUserId());
        input.transformOrder(order);

        List<Long> removedIds = new ArrayList<>();
        if(NumberUtils.isNotNull(order.getId())) {
            var entity = orderRepository.findById(order.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Not Found Order .!")
            );
            if(Common.CollectionIsEmpty(input.details())) {
                throw new RuntimeException("Lỗi sửa chưa cập nhật đơn ! ");
            }
            List<Long> detailInputIds = input.details().stream()
                .map(OrderDetail::getDetailId)
                .filter(NumberUtils::isNotNull).toList();
            List<Long> detailIds = entity.getDetails().stream().map(CustomerOrderDetail::getId).toList();

            removedIds = detailIds.stream().filter(id -> !detailInputIds.contains(id)).toList();
            Iterator<CustomerOrderDetail> iterator = entity.getDetails().iterator();
            while (iterator.hasNext()) {
                CustomerOrderDetail detail = iterator.next();
                if (removedIds.contains(detail.getId())) {
                    iterator.remove();
                }
            }
            CopyProperty.CopyIgnoreNull(entity, order);
        } else {
            Data data = null;
            if(NumberUtils.isNotNull(input.dataId())) {
                data = dataRepository.findById(input.dataId()).orElseThrow(
                    () -> new ResourceNotFoundException("Lead Id Not Found .!")
                );
            }
            if(Objects.isNull(data)) {
                data = dataRepository.findFirstByPhone(input.customer().getMobile()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer Lead Not Found .!")
                );
            }
            data.setStatus(DataService.DATA_STATUS.THANH_CO_HOI.getStatusCode());
            dataRepository.save(data);

            order.setDataId(data.getId());
            order.setSource(data.getSource());
            order.setPaid(0.0);
        }

        var modelStatus = statusOrderRepository.findStartOrder().orElseThrow(
            () -> new RuntimeException("Trạng thái mới chưa được cấu hình")
        );
        order.setStatus(modelStatus.getId());

        var listDetails = input.transformOrderDetail(order, order.getStatus());
        if (!removedIds.isEmpty()) {
            Set<Long> removedIdSet = new HashSet<>(removedIds);
            listDetails.removeIf(detail -> removedIdSet.contains(detail.getId()));
        }
        order.setDetails(listDetails);
        OrderUtils.calculatorPrice(order);

        /* CascadeType.ALL thì không cần detail save */
        orderRepository.save(order);
        listDetails.forEach(detail -> detail.setCustomerOrder(order));
        listDetails.forEach(detail -> detail.setCustomerOrderId(order.getId()));

        this.sendMessageOnOrderChange(order);
        return order;
    }

    public void save(CustomerOrder order) {
        orderRepository.save(order);
        this.sendMessageOnOrderChange(order);
    }

    public Map<Integer, List<CustomerOrder>> fetchKanban() {
        List<Integer> status = statusOrderRepository.findAll().stream().map(CustomerOrderStatus::getId).toList();
        if(status.isEmpty()) {
            throw new RuntimeException("Chưa cấu hình trạng thái đơn hàng");
        }

        Map<Integer, List<CustomerOrder>> mOrders = status.stream().collect(Collectors.toMap(
            i -> i,
            i -> new ArrayList<>()
        ));
        var mStatus = detailRepository.findOrderGroupsByStatus(status);
        Map<Integer, List<Long>> result = new HashMap<>();
        for (Object[] row : mStatus) {
            Integer item = ((Number) row[0]).intValue();
            String json = (String) row[1];
            List<Long> ids = JsonUtils.Json2ListObject(json, Long.class);
            result.put(item, ids);
        }
        List<Long> allOrderIds = result.values()
            .stream()
            .flatMap(List::stream)
            .toList();

        List<CustomerOrder> orders = orderRepository.findByIds(allOrderIds);
        var orderWithDetails = transformDetails(orders);
        for (CustomerOrder order : orderWithDetails) {
            List<Integer> detailStatus = order.getDetails().stream().map(CustomerOrderDetail::getStatus).toList();
            for (Map.Entry<Integer, List<CustomerOrder>> entry : mOrders.entrySet()) {
                Integer orderStatus = entry.getKey();
                if(detailStatus.contains(orderStatus)) {
                    mOrders.get(orderStatus).add(order);
                }
            }
        }
        transformDetails(orders);
        return mOrders;
    }

    public List<CustomerOrder>fetchKanbanDetail(OrderFilter filter) {

        int LIMIT = filter.limit();
        final String totalSQL = "FROM `customer_order` c left join `customer_order_detail` d on c.id = d.customer_order_id ";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIntegerEquals("d.status", filter.status());
        sqlBuilder.addStringEquals("c.customer_mobile_phone", filter.customerPhone());
        sqlBuilder.addStringEquals("c.code", filter.code());

        String finalQuery = sqlBuilder.builder();
        var nativeQuery = entityManager.createNativeQuery("SELECT c.* " + finalQuery , CustomerOrder.class);
        nativeQuery.setMaxResults(LIMIT);
        var orders = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerOrder.class);
        return transformDetails(orders);
    }

    public Ipage<?>fetchList(OrderFilter filter) {
        var sale = baseController.getInfo();
        Integer page = filter.page();
        var et = EntityQuery.create(entityManager, CustomerOrder.class);
        boolean isAdminOrManager = sale.getAuthorities().stream().anyMatch(auth
            -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_SALE_MANAGER")
        );
        Integer userCreateId = (filter.saleId() != null) ?
            (isAdminOrManager ? filter.saleId() : sale.getId()) :
            (isAdminOrManager ? null : sale.getId());

        et.integerEqualsTo("userCreateId", userCreateId);
        et.like("customerName", filter.customerName())
            .integerEqualsTo("customerId", filter.customerId())
            .integerEqualsTo("status", filter.status())
            .integerEqualsTo("paidStatus", filter.paidStatus())
            .stringEqualsTo("customerMobile", filter.customerPhone())
            .stringEqualsTo("customerEmail", filter.customerEmail())
            .stringEqualsTo("code", filter.code())
            .stringEqualsTo("type", filter.type())
            .addDescendingOrderBy("createdAt")
            .setMaxResults(filter.limit())
            .setFirstResult(page * filter.limit());
        var lists = transformDetails(et.list());
        return Ipage.generator(filter.limit(), et.count(), page, lists);
    }

    public CustomerOrder completeOrder(Long id) {
        CustomerOrder order = orderRepository.findById(id).orElseThrow(
            () -> new RuntimeException("error no record exists")
        );
        if (order.getType().equals(CustomerOrder.TYPE_CO_HOI)) {
            throw new RuntimeException("Không thể hoàn thành đơn hàng cơ hội.");
        }
        order.setDoneAt(new Date());
        orderRepository.save(order);
        return order;
    }

    public List<CustomerOrder> transformDetails(List<CustomerOrder> orders) {
        if(Common.CollectionIsEmpty(orders)) {
            return new ArrayList<>();
        }
        var newOrders = orders.stream().map(CustomerOrder::cloneNoDetail).toList();
        var orderIds = newOrders.stream().map(CustomerOrder::getId).toList();
        var details = detailRepository.fetchDetailOrdersId(orderIds);
        for( CustomerOrder order : newOrders ) {
            var detailOfOrder = details.stream().filter(
                d -> d.getCustomerOrderId().equals(order.getId())
            );
            order.setDetails(detailOfOrder.toList());
        }
        return newOrders;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatusOrder(Long orderId, Long detailId, Integer statusId) {
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(
            () -> new RuntimeException("error no record exists")
        );
        if (order.getType().equals(CustomerOrder.TYPE_CO_HOI)) {
            throw new RuntimeException("Không thể cập nhật trạng thái vì đơn hàng là cơ hội.");
        }
        CustomerOrderStatus status = statusOrderRepository.findById(statusId).orElseThrow(
            () -> new RuntimeException("error no record exists")
        );
        detailRepository.updateDetailStatus(status.getId(), detailId);
    }

    public void sendMessageOnOrderChange(CustomerOrder order) {
        var message = Message.create(EventTopic.ORDER_CHANGE, order.clone());
        this.publish(message);
    }

    @Transactional
    public OrderResponse view(Long id) {
        var order = orderRepository.fetchWithCustomer(id).orElseThrow(
            () -> new ResourceNotFoundException("Order not found .!")
        );
        return withOrderDetail(order);
    }

    @Transactional
    public OrderResponse withOrderDetail(CustomerOrder order) {
        Hibernate.initialize(order.getDetails());
        var orderRep = new OrderResponse();
        CopyProperty.CopyNormal(order.cloneNoDetail(), orderRep);
        orderRep.setDetails(new ArrayList<>(order.getDetails()));
        orderRep.setCustomer(customerRepository.findById(orderRep.getCustomerId()).orElseThrow(
            () -> new RuntimeException("Customer not found !")
        ));
        return orderRep;
    }

    @Transactional
    public OrderResponse findByCode(String code) {
        var order = orderRepository.findByCode(code).orElseThrow(
            () -> new ResourceNotFoundException("Order not found .!")
        );
        return withOrderDetail(order);
    }

    @Transactional
    public OrderResponse findById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Order not found .!")
        );
        log.info(order.getCustomerId());
        return withOrderDetail(order);
    }

    @Override
    public void setDelegate(EventDelegate eventDelegate) {
        this.eventDelegate = eventDelegate;
    }

    @Override
    public void publish(MessageInterface message) {
        if(Objects.nonNull(eventDelegate)) {
            eventDelegate.sendEvent(message);
        }
    }

    @Transactional
    public void cancelCoHoi(Long orderId, Boolean isDetail) {
        var model = statusOrderRepository.findCancelOrder().orElseThrow(
            () -> new RuntimeException("Chưa thiết lập trạng thái Hủy đơn !")
        );
        Integer statusCancel = model.getStatus();
        if(isDetail) {
            var order = detailRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("error no record exists")
            );
            order.setStatus(statusCancel);
        } else {
            var order = orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("error no record exists")
            );
            order.setStatus(statusCancel);
        }
    }

    @Transactional
    public void deleteContract(String code, String file) {
        customerContractRepository.deleteByOrderCode(code, file);
    }
}
