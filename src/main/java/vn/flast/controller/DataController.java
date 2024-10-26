package vn.flast.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.DataFilter;
import vn.flast.entities.MyResponse;
import vn.flast.models.Data;
import vn.flast.models.User;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.UserRepository;
import vn.flast.service.DataService;
import vn.flast.validator.ValidationErrorBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/data")
public class DataController extends BaseController {

    @Autowired
    private DataService dataService;

    @Autowired
    private UserRepository userRepository;


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MyResponse<?> create(@Valid @RequestParam(defaultValue = "0") Integer sessionId, @RequestBody Data iodata, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        iodata.setFromDepartment(Data.FROM_DEPARTMENT.FROM_DATA.value());
        iodata.setStaff(getUsername());
        iodata.setStatus(DataService.DATA_STATUS.CREATE_DATA.getStatusCode());
        dataService.saveData(iodata);
        int dataId = Optional.ofNullable(iodata.getId())
                .map(Long::intValue)
                .orElse(0);
        if(dataId != 0) {
            dataService.createAndUpdateDataMedias(iodata.getFileUrls(), sessionId, dataId);
        }
        return MyResponse.response(iodata);
    }

    @RequestMapping(value = "/lists", method = RequestMethod.POST)
    public MyResponse<?> fetch(
            @RequestBody DataFilter data,
            @RequestParam(defaultValue = "false") String ofWorkSummary,
            @RequestParam(defaultValue = "1") Integer page
    ) {
        Ipage<?> dataRet;
        if(Boolean.parseBoolean(ofWorkSummary)) {
            User user = userRepository.findById(getUserId()).orElseThrow(() -> new RuntimeException("Khôn tồn tại user này"));
            if(!user.ruleCskh()) {
                data.setSaleId(getUserId());
            }
            dataRet = dataService.leadOfWork(data);
        } else {
            data.setFromDepartment(Data.FROM_DEPARTMENT.FROM_DATA.value());
            dataRet = dataService.getListDataFromCustomerService(data);
        }
        return MyResponse.response(dataRet);
    }


}
