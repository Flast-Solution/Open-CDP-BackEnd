package vn.flast.domains.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.domains.order.OrderService;
import vn.flast.domains.order.OrderUtils;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.CustomerOrderPayment;
import vn.flast.repositories.CustomerOrderPaymentRepository;

import java.util.List;

@Service("payService")
public class PayService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerOrderPaymentRepository paymentRepository;

    @Transactional(rollbackFor = Exception.class)
    public CustomerOrderPayment manualMethod(OrderPaymentInfo info) {
        var model = new CustomerOrderPayment();
        info.transformPayment(model);
        var order = orderService.view(info.id());
        Double paid = order.getPaid();
        if( (order.getTotal() - paid) < model.getAmount()) {
            throw new RuntimeException("Paid not valid .!");
        }
        model.setCode(order.getCode());
        model.setIsConfirm(OrderUtils.PAYMENT_IS_CONFIRM);
        paymentRepository.save(model);

        order.setPaid(paid + model.getAmount());
        if(order.getPaid() >= order.getTotal()) {
            order.setPaymentStatus(OrderUtils.PAYMENT_STATUS_DONE);
        }
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
}
