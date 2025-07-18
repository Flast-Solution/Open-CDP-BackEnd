package vn.flast.controller.marketting;

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
@RequestMapping("/data-marketing")
public class DataMarketingController {

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
