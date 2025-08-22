package vn.flast.controller;
/**************************************************************************/
/*  DataCollectionController.java                                         */
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.DataCollection;
import vn.flast.pagination.Ipage;
import vn.flast.searchs.DataCollectionFilter;
import vn.flast.service.DataCollectionService;

@RestController
@Slf4j
@RequestMapping("/data-collection")
public class DataCollectionController extends BaseController {

    @Autowired
    private DataCollectionService dataCollectionService;

    @GetMapping("/fetch")
    public MyResponse<?> getData(DataCollectionFilter filter){
        Ipage<DataCollection> ipage = dataCollectionService.fetch(filter);
        return MyResponse.response(ipage);
    }

    @PostMapping("/save")
    public MyResponse<?> saveData(@RequestBody DataCollection dataCollection){
        var entity = dataCollectionService.save(dataCollection);
        return MyResponse.response(entity, "Đã cập nhật bộ sưu tập dữ liệu thô !");
    }
}
