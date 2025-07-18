package vn.flast.controller.marketting;


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
