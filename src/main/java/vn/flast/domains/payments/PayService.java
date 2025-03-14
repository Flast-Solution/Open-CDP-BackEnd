package vn.flast.domains.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.domains.order.OrderService;
import vn.flast.domains.order.OrderUtils;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderPayment;
import vn.flast.repositories.CustomerOrderDetailRepository;
import vn.flast.repositories.CustomerOrderPaymentRepository;
import vn.flast.repositories.CustomerOrderRepository;
import vn.flast.repositories.StatusOrderRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("payService")
public class PayService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerOrderPaymentRepository paymentRepository;

    @Autowired
    private StatusOrderRepository statusOrderRepository;

    @Autowired
    private CustomerOrderDetailRepository detailRepository;

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    public CustomerOrderPayment manualMethod(OrderPaymentInfo info) {
        var order = orderService.view(info.id());
        var model = new CustomerOrderPayment();
        info.transformPayment(model);
        Double paid = order.getPaid();
        if( (order.getTotal() - paid) < model.getAmount()) {
            throw new RuntimeException("Paid not valid .!");
        }
        model.setCode(order.getCode());
        model.setConfirmTime(new Date());
        model.setIsConfirm(OrderUtils.PAYMENT_IS_CONFIRM);
        paymentRepository.save(model);
        order.setType(CustomerOrder.TYPE_ORDER);
        order.setPaid(paid + model.getAmount());
        if(order.getPaid() >= order.getTotal()) {
            order.setPaymentStatus(OrderUtils.PAYMENT_STATUS_DONE);
        }
        orderRepository.save(order);
        return model;
    }

    public void create(OrderPaymentInfo info){
        var model = new CustomerOrderPayment();
        info.transformPayment(model);
        var order = orderService.view(info.id());
        Double paid = order.getPaid();
        if( (order.getTotal() - paid) < model.getAmount()) {
            throw new RuntimeException("Paid not valid .!");
        }
        model.setCode(order.getCode());
        model.setIsConfirm(OrderUtils.PAYMENT_IS_NOT_CONFIRM);
        paymentRepository.save(model);
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

    @Transactional(rollbackFor = Exception.class)
    public void confirmPayment(CustomerOrderPayment input){
        var model = paymentRepository.findById(input.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Not found AccountPayOrder")
        );
        if (model.getIsConfirm() == OrderUtils.PAYMENT_IS_CONFIRM) {
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
        Integer statusStartOrder = statusOrderRepository.findStartOrder().getId();
        order.setStatus(statusStartOrder);
        if(order.getPaidTime() == null) {
            order.setPaidTime(new Date());
        }
        List<Long> orderIds = List.of(order.getId()); // Chuẩn hơn và dễ mở rộng
        detailRepository.fetchDetailOrdersId(orderIds).forEach(detail -> {
            detail.setStatus(statusStartOrder);
            detailRepository.save(detail);
        });
        orderRepository.save(order);
    }
}
