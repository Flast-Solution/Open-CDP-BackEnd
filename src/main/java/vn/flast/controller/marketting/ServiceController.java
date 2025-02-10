package vn.flast.controller.marketting;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.ComplaintFilter;
import vn.flast.entities.MyResponse;
import vn.flast.models.Service;
import vn.flast.service.ServiceDataService;

@Log4j2
@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceDataService serviceDataService;


    @GetMapping(value = "/list")
    public MyResponse<?> listService(){
        var data = serviceDataService.listService();
        return MyResponse.response(data);
    }

    @PostMapping(value = "/create")
    public MyResponse<?> createService(@RequestBody Service input){
        var data = serviceDataService.createService(input);
        return MyResponse.response(data);
    }

    @PostMapping(value = "/update")
    public MyResponse<?> updateService(@RequestBody Service input){
        var data = serviceDataService.updateService(input);
        return MyResponse.response(data);
    }
}
