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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.controller.BaseController;
import vn.flast.entities.MyResponse;
import vn.flast.entities.SaleKpiProperty;
import vn.flast.models.UserKpi;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.UserRepository;
import vn.flast.service.marketting.KpiService;
import vn.flast.user.MyUserService;

@RestController
@RequestMapping("/kpi-marketing")
public class KpiMarkettingController extends BaseController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KpiService kpiService;

    @Autowired
    private MyUserService myUserService;

    public MyResponse<?> usersOfMarketing() {
        var data = myUserService.getUsersByRole("ROLE_DBA");
        return MyResponse.response(data);
    }


    @GetMapping(value = "/list")
    public MyResponse<?> listKpi(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "1") Integer limit,
            @RequestParam(required = false) Integer idFilter,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(name = "department", required = false, defaultValue = "0") Integer department
    ) {
        Ipage<UserKpi> iPage = kpiService.listKpi(getUserId(), page, idFilter, month, year, department, limit);
        return MyResponse.response(iPage);
    }

    @PostMapping(value = "/create")
    public MyResponse<?> setData(@RequestBody SaleKpiProperty input){
        var data = kpiService.create(input);
        return MyResponse.response(data);
    }

    @PostMapping(value = "/update")
    public MyResponse<?> update(@RequestBody SaleKpiProperty input){
        var data = kpiService.update(input);
        return MyResponse.response(data);
    }


}
