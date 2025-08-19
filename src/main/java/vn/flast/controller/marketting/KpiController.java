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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.controller.BaseController;
import vn.flast.entities.MyResponse;
import vn.flast.entities.KpiInput;
import vn.flast.models.UserKpi;
import vn.flast.pagination.Ipage;
import vn.flast.searchs.KPIFilter;
import vn.flast.service.marketting.KpiService;

@RestController
@RequestMapping("/kpi")
public class KpiController extends BaseController {

    @Autowired
    private KpiService kpiService;

    @GetMapping(value = "/fetch")
    public MyResponse<?> listKpi(KPIFilter filter) {
        Ipage<UserKpi> iPage = kpiService.listKpi(filter);
        return MyResponse.response(iPage);
    }

    @PostMapping(value = "/save")
    public MyResponse<?> setData(@RequestBody KpiInput input){
        var data = kpiService.save(input);
        return MyResponse.response(data, "Tạo mới KPI thành công !");
    }
}
