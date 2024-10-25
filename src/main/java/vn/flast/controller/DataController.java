package vn.flast.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.Data;
import vn.flast.service.DataService;

import java.util.Optional;

@RestController
@RequestMapping("/data")
public class DataController extends BaseController {

    @Autowired
    private DataService dataService;


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MyResponse<?> create(@RequestParam(defaultValue = "0") Integer sessionId, @RequestBody Data iodata ) {

        iodata.setFromDepartment(Data.FROM_DEPARTMENT.FROM_DATA.value());
        iodata.setStaff(getUsername());
        iodata.setStatus(DataService.DATA_STATUS.CREATE_DATA.getStatusCode());
//        if(iodata.getType() == Data.DataType.SANPHAM_DICHVU.value()) {
//            dataService.createByCustomerService(iodata);
//        } else {
//            dataService.saveData(iodata);
//        }
        int dataId = Optional.ofNullable(iodata.getId())
                .map(Long::intValue)
                .orElse(0);
        if(dataId != 0) {
            dataService.createAndUpdateDataMedias(iodata.getFileUrls(), sessionId, dataId);
        }
        return MyResponse.response(iodata);
    }


}
