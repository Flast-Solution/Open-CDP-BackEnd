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




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.CustomerOrderStatus;
import vn.flast.repositories.CustomerOrderStatusRepository;
import java.util.List;

@RestController
@RequestMapping("/order-status")
public class OrderStatusController {

    @Autowired
    private CustomerOrderStatusRepository statusRepository;

    @PostMapping(value = "/save")
    public MyResponse<?> create(@RequestBody List<CustomerOrderStatus> orderStatus) {
        var data = statusRepository.saveAll(orderStatus);
        return  MyResponse.response(data, "Cập nhật trạng thái thành công !");
    }

    @GetMapping(value = "/fetch")
    public MyResponse<?> fetch() {
        Sort sort = Sort.by(Sort.Direction.ASC, "order");
        return  MyResponse.response(statusRepository.findAll(sort));
    }
}
