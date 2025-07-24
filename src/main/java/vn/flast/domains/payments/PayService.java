package vn.flast.domains.payments;

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
import vn.flast.repositories.CustomerOrderDetailRepository;
import vn.flast.repositories.CustomerOrderPaymentRepository;
import vn.flast.repositories.CustomerOrderStatusRepository;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.NumberUtils;

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
    private CustomerOrderStatusRepository statusOrderRepository;

    @Autowired
    private CustomerOrderDetailRepository detailRepository;

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
        model.setIsConfirm(OrderUtils.PAYMENT_IS_CONFIRM);
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

    public List<CustomerOrderPayment> listByOrderId(Long orderId){
        var order = orderService.view(orderId);
        return paymentRepository.findCodes(order.getCode());
    }

    public List<OrderPayment>listOrderPayment(PaymentFilter filter) {
        var et = EntityQuery.create(entityManager, CustomerOrder.class);
        et.stringEqualsTo("code", filter.getCode());
        et.like("customerMobilePhone", filter.getPhone());
        et.like("customerEmail", filter.getEmail());
        et.integerEqualsTo("userCreateId", filter.getSaleId());
        et.between("createdTime", filter.getFrom(), filter.getTo());
        et.addDescendingOrderBy("id");
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.getLimit() * filter.page());
        var listsOrder = et.list();
        return listsOrder.stream().map(order -> {
            OrderPayment op = new OrderPayment();
            CopyProperty.CopyIgnoreNull(order, op);
            List<CustomerOrderPayment> payments = listByOrderId(order.getId());
            op.setPayments(payments);
            return op;
        }).toList();
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
