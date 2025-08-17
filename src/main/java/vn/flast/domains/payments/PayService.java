package vn.flast.domains.payments;
/**************************************************************************/
/*  app.java                                                              */
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.domains.order.OrderService;
import vn.flast.domains.order.OrderUtils;
import vn.flast.entities.payment.OrderPayment;
import vn.flast.entities.payment.PaymentFilter;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderPayment;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CustomerOrderPaymentRepository;
import vn.flast.utils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("payService")
public class PayService {

    @Autowired
    @Lazy
    private OrderService orderService;

    @Autowired
    private CustomerOrderPaymentRepository paymentRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional(rollbackFor = Exception.class)
    public CustomerOrderPayment manualMethod(OrderPaymentInfo info) {
        var orderResponse = orderService.view(info.orderId());
        var model = new CustomerOrderPayment();
        info.transformPayment(model);
        Double paid = orderResponse.getPaid();
        if( (orderResponse.getTotal() - paid) < model.getAmount()) {
            throw new RuntimeException("Paid not valid .!");
        }
        model.setCode(orderResponse.getCode());
        model.setConfirmTime(info.date());
        model.setIsConfirm(info.getConfirm());
        model.setSsoId(Common.getSsoId());
        model.setContent(info.content());
        paymentRepository.save(model);

        orderResponse.setType(CustomerOrder.TYPE_ORDER);
        orderResponse.setPaid(paid + model.getAmount());
        boolean isPaid = NumberUtils.gteZero(orderResponse.getPaid());
        orderResponse.setType(isPaid ? CustomerOrder.TYPE_ORDER: CustomerOrder.TYPE_CO_HOI);
        orderResponse.setCreatedAt(new Date());

        CustomerOrder order = new CustomerOrder();
        CopyProperty.CopyIgnoreNull(orderResponse, order);
        boolean reCalculatorOrder = false;
        if(NumberUtils.isNotNull(info.vat())) {
            order.setVat(info.vat());
            reCalculatorOrder = true;
        }
        if(NumberUtils.isNotNull(info.shippingCost())) {
            order.setShippingCost(info.shippingCost());
            reCalculatorOrder = true;
        }
        if(reCalculatorOrder) {
            OrderUtils.calculatorPrice(order);
        }
        orderService.save(order);
        return model;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteId(Long id){
        var model = paymentRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Not Found !")
        );
        var order = orderService.findByCode(model.getCode());
        double remain = order.getPaid() - model.getAmount();
        order.setPaid(remain < 0 ? 0 : remain);
        paymentRepository.delete(model);
    }

    public List<CustomerOrderPayment> listByOrderId(Long orderId) {
        var order = orderService.view(orderId);
        return paymentRepository.findCodes(order.getCode());
    }

    public Ipage<?>listOrderPayment(PaymentFilter filter) {

        int LIMIT = filter.getLimit();
        int PAGE = filter.page();
        int OFFSET = (filter.page()) * LIMIT;

        final String totalSQL = "FROM customer_order c left join customer_order_payment p on p.code = c.code";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addStringEquals("c.customer_mobile_phone", filter.getPhone());

        sqlBuilder.addIntegerEquals("p.is_confirm", 0);
        sqlBuilder.addStringEquals("p.code", filter.getCode());
        sqlBuilder.addDateBetween("p.in_time", filter.getFrom(), filter.getTo());
        sqlBuilder.addOrderByDesc("p.id");

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT c.* " + finalQuery, CustomerOrder.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var lists = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerOrder.class);
        List<CustomerOrder> orders = orderService.transformDetails(lists);

        List<OrderPayment> payments = new ArrayList<>();
        for(CustomerOrder order : orders) {
            OrderPayment orderPayment = new OrderPayment();
            CopyProperty.CopyIgnoreNull(order, orderPayment);
            List<CustomerOrderPayment> payment = listByOrderId(order.getId());
            orderPayment.setPayments(payment);
            payments.add(orderPayment);
        }
        return  Ipage.generator(LIMIT, count, PAGE, payments);
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirmPayment(CustomerOrderPayment input){
        var model = paymentRepository.findById(input.getId()).orElseThrow(
            () -> new ResourceNotFoundException("Not found AccountPayOrder")
        );
        if (model.getIsConfirm().equals(OrderUtils.PAYMENT_IS_CONFIRM)) {
            return;
        }
        var order = Optional.ofNullable(orderService.findByCode(model.getCode())).orElseThrow(
            () -> new RuntimeException("Not found CustomerOrder")
        );
        if (order.getTotal() <= 0) {
            throw new RuntimeException("Chưa thể confirm khi chưa chốt giá của đơn");
        }
        long totalPaid = paymentRepository.countByOrderCodeAndIsConfirm(input.getCode());
        order.setType(CustomerOrder.TYPE_ORDER);
        order.setPaid(totalPaid + model.getAmount());
        orderService.save(order);
    }
}
