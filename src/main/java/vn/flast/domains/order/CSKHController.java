package vn.flast.domains.order;
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




import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.entities.order.OrderCare;
import vn.flast.models.FlastNote;
import vn.flast.searchs.OrderFilter;

@Log4j2
@RestController
@RequestMapping("/customer-order")
@RequiredArgsConstructor
public class CSKHController {

    private final OrderService orderService;

    @GetMapping("/fetch-co-hoi")
    public MyResponse<?> fetchCoHoi(OrderFilter filter) {
        return MyResponse.response(orderService.fetchList(filter));
    }

    @GetMapping("/fetch-co-hoi-not-care")
    public MyResponse<?> fetchCoHoiNotCare(OrderFilter filter) {
        return MyResponse.response(orderService.fetchListCoHoiNotCare(filter));
    }

    @GetMapping("/fetch-co-hoi-care")
    public MyResponse<?> fetchCoHoiCare(OrderFilter filter) {
        return MyResponse.response(orderService.fetchListCoHoiCare(filter));
    }

    @GetMapping("/find-co-hoi-care")
    public MyResponse<?> findCoHoiCare(@RequestParam Long orderId) {
        var order = orderService.findById(orderId);
        return MyResponse.response(order);
    }

    @PostMapping("/take-care-co-hoi")
    public MyResponse<?> takeCareCoHoi(@RequestBody OrderCare input){
        input.setType(FlastNote.TYPE_COHOI);
        var data = orderService.takeCareNoteCoHoi(input);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-order-completed")
    public MyResponse<?> fetchOrderCompleted(OrderFilter filter) {
        return MyResponse.response(orderService.fetchLisOrderNotCare(filter));
    }

    @GetMapping("/fetch-order-cancel")
    public MyResponse<?> fetchOrderCancel(OrderFilter filter) {
        return MyResponse.response(orderService.fetchLisOrderCancel(filter));
    }

    @PostMapping("/take-care-order")
    public MyResponse<?> takeCareOrder(@RequestBody OrderCare input){
        input.setType(FlastNote.TYPE_ORDER);
        var data = orderService.takeCareNoteCoHoi(input);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-order-take-care")
    public MyResponse<?> fetchOrderTakeCare(OrderFilter filter) {
        return MyResponse.response(orderService.fetchLisOrderCare(filter));
    }
}
