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




import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.flast.entities.MyResponse;
import vn.flast.entities.payment.PaymentFilter;
import vn.flast.exception.InvalidParamsException;
import vn.flast.models.CustomerOrderPayment;
import vn.flast.utils.JsonUtils;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @PostMapping("/manual")
    public MyResponse<?> direct(@RequestBody OrderPaymentInfo paymentInfo) {
        log.info("Pay: {}", JsonUtils.toJson(paymentInfo));
        if(!paymentInfo.validate()) {
            throw new InvalidParamsException("Invalid Params Pay .!");
        }
        var data = payService.manualMethod(paymentInfo);
        return MyResponse.response(data, "Cập nhật thanh toán đơn hàng thành công !");
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Long id) {
        payService.deleteId(id);
        return MyResponse.response("Ok");
    }

    @GetMapping("/list-by-order-id")
    public MyResponse<?> find(@RequestParam Long orderId) {
        var lists = payService.listByOrderId(orderId);
        return MyResponse.response(lists);
    }

    @GetMapping("/list-order-payment")
    public MyResponse<?> listOrderPayment(PaymentFilter filter) {
        var data = payService.listOrderPayment(filter);
        return MyResponse.response(data);
    }

    @PostMapping("/confirm-payment")
    public MyResponse<?> confirmPayment(@RequestBody CustomerOrderPayment input){
        payService.confirmPayment(input);
        return MyResponse.response("oke");
    }
}
