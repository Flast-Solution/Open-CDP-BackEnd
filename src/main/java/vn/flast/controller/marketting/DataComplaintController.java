package vn.flast.controller.marketting;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.ComplaintFilter;
import vn.flast.entities.MyResponse;
import vn.flast.models.DataComplaint;
import vn.flast.service.cskh.DataComplaintService;
import java.util.Objects;

@Log4j2
@RestController
@RequestMapping("/data-complaint")
public class DataComplaintController {

    @Autowired
    private DataComplaintService dataComplaintService;

    @PostMapping(value = "/create-complaint")
    public MyResponse<?> createComplaint(@RequestBody DataComplaint input) {
        var data = Objects.nonNull(input.getId())
            ? dataComplaintService.updateComplaint(input)
            : dataComplaintService.createComplaint(input);
        return MyResponse.response(data);
    }

    @GetMapping(value = "/list-complaint")
    public MyResponse<?> listComplaint(ComplaintFilter filter){
        var data = dataComplaintService.fetchComplaint(filter);
        return MyResponse.response(data);
    }
}
