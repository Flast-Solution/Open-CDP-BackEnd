package vn.flast.domains.order;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.controller.common.BaseController;
import vn.flast.entities.order.OrderCare;
import vn.flast.entities.order.OrderComment;
import vn.flast.entities.order.OrderResponse;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderNote;
import vn.flast.models.CustomerOrderStatus;
import vn.flast.orchestration.EventDelegate;
import vn.flast.orchestration.EventTopic;
import vn.flast.orchestration.Message;
import vn.flast.orchestration.MessageInterface;
import vn.flast.orchestration.Publisher;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ConfigRepository;
import vn.flast.repositories.CustomerOrderDetailRepository;
import vn.flast.repositories.CustomerOrderNoteRepository;
import vn.flast.repositories.CustomerOrderRepository;
import vn.flast.repositories.CustomerOrderStatusRepository;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataRepository;
import vn.flast.repositories.ProductRepository;
import vn.flast.repositories.ProductSkusDetailsRepository;
import vn.flast.repositories.UserRepository;
import vn.flast.searchs.OrderFilter;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.NumberUtils;
import vn.flast.utils.SqlBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

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
    private DataRepository dataRepository;

    @Autowired
    private CustomerOrderDetailRepository detailRepository;

    @Autowired
    private BaseController baseController;

    @Autowired
    private CustomerOrderStatusRepository statusOrderRepository;

    @Autowired
    private CustomerOrderNoteRepository orderNoteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductSkusDetailsRepository productSkusDetailsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ConfigRepository configRepository;

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
            .like("customerMobile", filter.customerPhone())
            .like("customerEmail", filter.customerEmail())
            .like("code", filter.code())
            .addDescendingOrderBy("createdAt")
            .stringEqualsTo("type", filter.type())
            .setMaxResults(filter.limit())
            .setFirstResult(page * filter.limit());
        var lists = transformDetails(et.list());
        return Ipage.generator(filter.limit(), et.count(), page, lists);
    }

    public Ipage<?>fetchListCoHoiNotCare(OrderFilter filter) {
        var sale = baseController.getInfo();
        boolean isAdminOrManager = sale.getAuthorities().stream().anyMatch(auth
            -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_CSKH")
        );
        Integer userCreateId = (filter.saleId() != null) ?
            (isAdminOrManager ? filter.saleId() : sale.getId()) :
            (isAdminOrManager ? null : sale.getId());

        int LIMIT = filter.limit();
        int OFFSET = filter.page() * LIMIT;

        final String totalSQL = " FROM `customer_order` c left join `customer_order_note` n on c.code = n.order_code ";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIntegerEquals("c.user_create_id", userCreateId);
        sqlBuilder.addStringEquals("c.type", CustomerOrder.TYPE_CO_HOI);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date dayBeforeYesterday = calendar.getTime();
        sqlBuilder.addDateLessThan("c.created_at", dayBeforeYesterday);
        sqlBuilder.like("c.customer_name", filter.customerName());
        sqlBuilder.addIntegerEquals("c.customer_id", filter.customerId());
        sqlBuilder.like("c.customer_mobile", filter.customerPhone());
        sqlBuilder.like("c.customer_email", filter.customerEmail());
        sqlBuilder.like("c.code", filter.code());
        sqlBuilder.addIsEmpty("n.order_code");

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT c.* " + finalQuery + " ORDER BY c.created_at DESC" , CustomerOrder.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerOrder.class);
        var lists = transformDetails(listData);
        return Ipage.generator(LIMIT, count, filter.page(), lists);
    }

    public List<OrderComment> fetchListOrderStatus(OrderFilter filter) {
        var sale = baseController.getInfo();
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
            .like("customerMobile", filter.customerPhone())
            .like("customerEmail", filter.customerEmail())
            .like("code", filter.code())
            .dateIsNull("doneAt")
            .addDescendingOrderBy("createdAt")
            .stringEqualsTo("type", filter.type());

        var lists = transformDetails(et.list());
        return lists.stream().map(order -> {
            OrderComment op = new OrderComment();
            CopyProperty.CopyIgnoreNull(order, op);
            op.setNotes(orderNoteRepository.findByOrderCode(order.getCode()));
            return op;
        }).toList();
    }

    public CustomerOrder completeOrder(Long id) {
        CustomerOrder order = orderRepository.findById(id).orElseThrow(
            () -> new RuntimeException("error no record exists")
        );
        if (order.getType().equals(CustomerOrder.TYPE_CO_HOI)) {
            throw new RuntimeException("Không thể hoàn thành đơn hàng cơ hội. Vui lòng cập nhật trạng thái đơn hàng cơ hội thông qua chức năng khác.");
        }
        order.setDoneAt(new Date());
        orderRepository.save(order);
        return order;
    }

    public Ipage<?> fetchListCoHoiCare(OrderFilter filter) {
        var sale = baseController.getInfo();
        boolean isAdminOrManager = sale.getAuthorities().stream().anyMatch( auth
            -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_CSKH")
        );
        Integer userCreateId = (filter.saleId() != null) ?
            (isAdminOrManager ? filter.saleId() : sale.getId()) :
            (isAdminOrManager ? null : sale.getId());

        int LIMIT = filter.limit();
        int OFFSET = filter.page() * LIMIT;
        final String totalSQL = " FROM `customer_order` c left join `customer_order_note` n on c.code = n.order_code ";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIntegerEquals("c.user_create_id", userCreateId);
        sqlBuilder.addStringEquals("c.type", CustomerOrder.TYPE_CO_HOI);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date dayBeforeYesterday = calendar.getTime();
        sqlBuilder.addDateLessThan("c.created_at", dayBeforeYesterday);
        sqlBuilder.like("c.customer_name", filter.customerName());
        sqlBuilder.addIntegerEquals("c.customer_id", filter.customerId());
        sqlBuilder.like("c.customer_mobile", filter.customerPhone());
        sqlBuilder.like("c.customer_email", filter.customerEmail());
        sqlBuilder.like("c.code", filter.code());
        sqlBuilder.addIntegerEquals("n.type", CustomerOrderNote.TYPE_COHOI);

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT c.* " + finalQuery + " ORDER BY c.created_at DESC" , CustomerOrder.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerOrder.class);
        var lists = transformDetails(listData);
        return Ipage.generator(LIMIT, count, filter.page(), lists);
    }

    public Ipage<?>fetchLisOrderNotCare(OrderFilter filter) {
        var sale = baseController.getInfo();
        boolean isAdminOrManager = sale.getAuthorities().stream() .anyMatch(auth
            -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_CSKH")
        );
        Integer userCreateId = (filter.saleId() != null) ?
            (isAdminOrManager ? filter.saleId() : sale.getId()) :
            (isAdminOrManager ? null : sale.getId());

        int LIMIT = filter.limit();
        int OFFSET = filter.page() * LIMIT;

        final String totalSQL = " FROM `customer_order` c left join `customer_order_note` n on c.code = n.order_code ";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIntegerEquals("c.user_create_id", userCreateId);
        sqlBuilder.addStringEquals("c.type", CustomerOrder.TYPE_ORDER);
        sqlBuilder.like("c.customer_name", filter.customerName());
        sqlBuilder.addIntegerEquals("c.customer_id", filter.customerId());
        sqlBuilder.like("c.customer_mobile", filter.customerPhone());
        sqlBuilder.like("c.customer_email", filter.customerEmail());
        sqlBuilder.like("c.code", filter.code());
        sqlBuilder.addIsEmpty("n.order_code");

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT c.* " + finalQuery + " ORDER BY c.created_at DESC" , CustomerOrder.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerOrder.class);
        var lists = transformDetails(listData);
        return Ipage.generator(LIMIT, count, filter.page(), lists);
    }

    public Ipage<?>fetchLisOrderCancel(OrderFilter filter) {
        var sale = baseController.getInfo();
        boolean isAdminOrManager = sale.getAuthorities().stream().anyMatch(auth
            -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_CSKH")
        );
        Integer userCreateId = (filter.saleId() != null) ?
            (isAdminOrManager ? filter.saleId() : sale.getId()) :
            (isAdminOrManager ? null : sale.getId());

        int LIMIT = filter.limit();
        int OFFSET = filter.page() * LIMIT;

        final String totalSQL = " FROM `customer_order` c left join `customer_order_note` n on c.code = n.order_code ";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIntegerEquals("c.user_create_id", userCreateId);
        sqlBuilder.addStringEquals("c.type", CustomerOrder.TYPE_ORDER);
        sqlBuilder.like("c.customer_name", filter.customerName());
        sqlBuilder.addIntegerEquals("c.customer_id", filter.customerId());
        sqlBuilder.like("c.customer_mobile", filter.customerPhone());
        sqlBuilder.like("c.customer_email", filter.customerEmail());
        sqlBuilder.like("c.code", filter.code());
        sqlBuilder.addIsEmpty("n.order_code");
        sqlBuilder.addIntegerEquals("n.type", CustomerOrderNote.TYPE_ORDER);

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT c.* " + finalQuery + " ORDER BY c.created_at DESC" , CustomerOrder.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerOrder.class);
        var lists = transformDetails(listData);
        return Ipage.generator(LIMIT, count, filter.page(), lists);
    }

    public Ipage<?>fetchLisOrderCare(OrderFilter filter) {
        var sale = baseController.getInfo();
        boolean isAdminOrManager = sale.getAuthorities().stream().anyMatch(auth
            -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_CSKH")
        );
        Integer userCreateId = (filter.saleId() != null) ?
            (isAdminOrManager ? filter.saleId() : sale.getId()) :
            (isAdminOrManager ? null : sale.getId());

        int LIMIT = filter.limit();
        int OFFSET = filter.page() * LIMIT;

        final String totalSQL = " FROM `customer_order` c left join `customer_order_note` n on c.code = n.order_code ";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIntegerEquals("c.user_create_id", userCreateId);
        sqlBuilder.addStringEquals("c.type", CustomerOrder.TYPE_ORDER);
        sqlBuilder.like("c.customer_name", filter.customerName());
        sqlBuilder.addIntegerEquals("c.customer_id", filter.customerId());
        sqlBuilder.like("c.customer_mobile", filter.customerPhone());
        sqlBuilder.like("c.customer_email", filter.customerEmail());
        sqlBuilder.like("c.code", filter.code());
        sqlBuilder.addNotNUL("n.order_code");
        sqlBuilder.addIntegerEquals("n.type", CustomerOrderNote.TYPE_ORDER);

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT c.* " + finalQuery + " ORDER BY c.created_at DESC" , CustomerOrder.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerOrder.class);
        var lists = transformDetails(listData);
        return Ipage.generator(LIMIT, count, filter.page(), lists);
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
    public CustomerOrder updateStatusOrder(Long orderId, Integer statusId) {
        CustomerOrder order = orderRepository.findById(orderId).orElseThrow(
            () -> new RuntimeException("error no record exists")
        );
        if (order.getType().equals(CustomerOrder.TYPE_CO_HOI)) {
            throw new RuntimeException("Không thể cập nhật trạng thái vì đơn hàng là cơ hội. Vui lòng cập nhật trạng thái đơn hàng cơ hội thông qua chức năng khác.");
        }
        CustomerOrderStatus status = statusOrderRepository.findById(statusId).orElseThrow(
            () -> new RuntimeException("error no record exists")
        );
        order.setStatus(status.getId());
        orderRepository.save(order);
        this.sendMessageOnOrderChange(order);
        return order;
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
        CopyProperty.CopyNormal(order.clone(), orderRep);
        orderRep.setDetails(new ArrayList<>(order.getDetails()));
        orderRep.setCustomer(customerRepository.findById(orderRep.getCustomerId()).orElse(null));
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

    public static String createOrderCode(String customerMobilePhone, String source) {
        if (customerMobilePhone == null) {
            return null;
        }
        String lastThereDigits = customerMobilePhone.substring(customerMobilePhone.length() - 3);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int day = calendar.get(Calendar.DATE);
        /* month +1 the month for current month */
        int month = calendar.get(Calendar.MONTH) + 1;
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String subYear = year.substring(year.length() - 2);
        String endCode = day + month + subYear + lastThereDigits;
        return source
            + Common.getAlphaNumericString(1, false)
            + Common.getAlphaNumericString(2, true)
            + endCode;
    }

    @Transactional
    public void cancelCoHoi(Long orderId, Boolean isDetail){
        Integer statusCancel = statusOrderRepository.findCancelOrder().getId();
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

    public static long calTotal(CustomerOrder order) {
        try {
            Double subtotal = order.getSubtotal();
            int shippingCost = NumberUtils.numberWithDefaultZero(order.getShippingCost());
            long priceOff = NumberUtils.numberWithDefaultZero(order.getPriceOff().intValue());
            int feeSaleOther = NumberUtils.numberWithDefaultZero(order.getFeeSaleOther());
            int vatPrice = order.calVat();
            long total = (long) (subtotal + shippingCost + feeSaleOther + vatPrice);
            return (long) (total - priceOff - order.calCastBack());
        } catch (Exception ex) {
            log.info("Tính chi phí đơn hàng lỗi: {}", ex.getMessage());
            return 0;
        }
    }

    public CustomerOrderNote takeCareNoteCoHoi(OrderCare input) {
        orderRepository.findByCode(input.getOrderCode()).orElseThrow(
            () -> new RuntimeException("error no record exists")
        );
        var care = orderNoteRepository.findByOrderCode(input.getOrderCode());
        if (care == null) {
            var noteOrder = new CustomerOrderNote();
            CopyProperty.CopyIgnoreNull(input, noteOrder);
            noteOrder.setUserName(baseController.getInfo().getSsoId());
            noteOrder.setUsesId(baseController.getInfo().getId());
            return orderNoteRepository.save(noteOrder);
        } else {
            CopyProperty.CopyIgnoreNull(input, care);
            return orderNoteRepository.save(care);
        }
    }
}
