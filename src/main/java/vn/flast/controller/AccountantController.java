package vn.flast.controller;
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




import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.domains.order.OrderService;
import vn.flast.domains.payments.PayService;
import vn.flast.entities.MyResponse;
import vn.flast.entities.payment.PaymentFilter;
import vn.flast.service.AccountantService;

@Log4j2
@RestController
@RequestMapping("/accountant")
public class AccountantController {

    @Autowired
    private AccountantService accountantService;

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/fetch-payment")
    public MyResponse<?> fetchPayment(PaymentFilter filter){
        var data = accountantService.fetch(filter);
        return MyResponse.response(data);
    }
}
