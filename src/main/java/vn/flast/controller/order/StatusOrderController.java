package vn.flast.controller.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.StatusOrder;
import vn.flast.service.StatusOrderService;

@Log4j2
@RestController
@RequestMapping("/status-order")
@RequiredArgsConstructor
public class StatusOrderController {

    private final StatusOrderService statusOrderService;


    @PostMapping("/save")
    public MyResponse<?> create(@RequestBody StatusOrder input){
        statusOrderService.create(input);
        return  MyResponse.response("oke");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(){
        var data = statusOrderService.fetchStatus();
        return MyResponse.response(data);
    }
}
