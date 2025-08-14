package vn.flast.controller.marketting;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.flast.controller.BaseController;
import vn.flast.models.DataMedia;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataMediaRepository;
import vn.flast.repositories.DataRepository;
import vn.flast.searchs.DataFilter;
import vn.flast.entities.MyResponse;
import vn.flast.models.Data;
import vn.flast.pagination.Ipage;
import vn.flast.security.UserPrinciple;
import vn.flast.service.DataService;
import vn.flast.utils.BuilderParams;
import vn.flast.utils.UploadsUtils;
import vn.flast.validator.ValidationErrorBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
@RequestMapping("/data")
public class LeadController extends BaseController {

    @Autowired
    private DataService dataService;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DataMediaRepository mediaRepository;

    @Autowired
    private CustomerPersonalRepository customerPersonalRepository;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @PostMapping(value = "/create")
    public MyResponse<?> create(
        @Valid
        @RequestParam(defaultValue = "0") Integer sessionId,
        @RequestBody Data iodata,
        Errors errors
    ) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        UserPrinciple userPrinciple = getInfo();
        iodata.setStaff(getUsername());
        boolean isAdminOrManager = userPrinciple.getAuthorities().stream().anyMatch(auth
            -> auth.getAuthority().equals("ROLE_SALE") || auth.getAuthority().equals("ROLE_SALE_MANAGER")
        );
        if(dataRepository.existsByCustomerMobile(iodata.getCustomerMobile())){
            iodata.setFromDepartment(Data.FROM_DEPARTMENT.FROM_RQL.value());
        } else if(isAdminOrManager) {
            iodata.setFromDepartment(Data.FROM_DEPARTMENT.FROM_SALE.value());
        } else {
            iodata.setFromDepartment(Data.FROM_DEPARTMENT.FROM_DATA.value());
        }
        iodata.setStatus(DataService.DATA_STATUS.CREATE_DATA.getStatusCode());
        var entity = dataService.saveData(iodata);

        Long dataId = (Long) entity.getParams().get("dataId");
        dataService.createAndUpdateDataMedias(sessionId, dataId);
        return MyResponse.response(entity.getParams());
    }

    @GetMapping(value = "/lists")
    public MyResponse<?> fetch(DataFilter data) {
        data.setFromDepartment(Data.FROM_DEPARTMENT.FROM_DATA.value());
        Ipage<?> dataRet = dataService.getListDataFromCustomerService(data);
        return MyResponse.response(dataRet);
    }

    @GetMapping(value = "/view")
    public MyResponse<?> viewData(@RequestParam Long dataId){
        var data = dataService.findById(dataId);
        return MyResponse.response(data);
    }

    @GetMapping(value = "/get-customer")
    public MyResponse<?> getCustomer(@RequestParam Long dataId){
        var lead = dataService.findById(dataId);
        var customer = customerPersonalRepository.findByPhone(lead.getCustomerMobile());
        BuilderParams body = BuilderParams.create()
            .addParam("lead", lead)
            .addParam("customer", customer);
        return MyResponse.response(body.getParams());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @PostMapping(value = "/update")
    public MyResponse<?> update(
        @RequestParam(defaultValue = "0") Integer sessionId,
        @RequestParam(defaultValue = "0") Long leadId,
        @RequestBody Data ioData
    ) {
        dataService.Update(ioData);
        List<String> urls = ioData.getFileUrls();
        if(Objects.nonNull(urls)) {
            dataService.createAndUpdateDataMedias(sessionId, leadId);
        }
        return MyResponse.response("OK");
    }

    @PostMapping(value = "/uploads-files", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public MyResponse<?> uploadsFiles(
        @RequestParam Long sessionId,
        @RequestParam(value = "files") List<MultipartFile> files
    ) throws Exception {
        List<String> medias = new ArrayList<>();
        for(MultipartFile mediaFile : files) {
            DataMedia dataMedia = new DataMedia();
            dataMedia.setSessionId(sessionId);
            String media = UploadsUtils.upload(dataMedia, mediaFile);
            dataMedia.setFile(media);
            mediaRepository.save(dataMedia);
            medias.add(media);
        }
        return MyResponse.response(medias);
    }

    @PostMapping(value = "/delete")
    public MyResponse<?> delete(@RequestParam Long id) {
        log.info("IoDataRequest delete id: {}", id);
        Boolean isUpdate = dataService.delete(id);
        return MyResponse.response(isUpdate ? "OK" : "FALSE");
    }

    @PostMapping("/re-assign")
    public MyResponse<?> reassign(@RequestParam Integer dataId, @RequestParam Integer saleId) {
        dataService.reAssignLeadManual(dataId, saleId);
        return MyResponse.response("Okie");
    }
}
