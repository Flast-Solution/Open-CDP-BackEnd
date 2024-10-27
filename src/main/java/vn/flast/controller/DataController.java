package vn.flast.controller;


import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.DataFilter;
import vn.flast.entities.ErrorData;
import vn.flast.entities.MyResponse;
import vn.flast.entities.RObject;
import vn.flast.models.Data;
import vn.flast.models.User;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.UserRepository;
import vn.flast.service.DataService;
import vn.flast.validator.ValidationErrorBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/data")
public class DataController extends BaseController {

    @Autowired
    private DataService dataService;

    @Autowired
    private UserRepository userRepository;


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @PostMapping(value = "/create")
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

    @GetMapping(value = "/lists")
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @PostMapping(value = "/update")
    public MyResponse<?> update(
            @RequestParam(defaultValue = "0") Integer sessionId,
            @RequestParam(defaultValue = "0") Integer leadId,
            @RequestBody Data ioData
    ) {
        String currentNote = ioData.getNote();
        Data data = Optional.ofNullable(dataService.findById(leadId.longValue())).orElseThrow(
                ()->new RuntimeException("Không tìm thấy Lead!")
        );
        String preNote = data.getNote();

        int saleId = ioData.getSaleId();
        User sale = userRepository.findById(saleId != 0 ? saleId : data.getSaleId()).orElseThrow(
                ()->new RuntimeException("Sale không tồn tại")
        );

        /* groupSaleId = saleLeaderId */
//        int leaderId  = userGroupDao.findLeaderIdBySaleId(sale.getId());
//
//        CopyProperties.CopyIgnoreNullWithZero(ioData, data,
//                "fromDepartment", "processTime", "serviceId", "partnerId", "isOrder", "type");
//        data.setSaleId(sale.getId());
//        data.setGroupSaleId(leaderId);
//        data.setAssignTo(sale.getSsoId());
//        data.setChuyenMucId(ioData.getChuyenMucId());

        if(StringUtils.isNotEmpty(currentNote)) {
            StringBuilder sb = new StringBuilder();
            if(StringUtils.isNotEmpty(preNote)) {
                sb.append(preNote);
            }
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(date);

            sb.append("<div class='i-note'>")
                    .append("<p class='note-edit'>")
                    .append(getUserSso()).append(" ").append("(").append(formattedDate).append(")")
                    .append("</p>")
                    .append("<div class='content-note'>").append(currentNote).append("</div>")
                    .append("</div>");

            data.setNote(sb.toString());
//            notificationService.saveOnCommandLead(data, currentNote);
        }

        dataService.Update(data);
        List<String> urls = ioData.getFileUrls();
        if(leadId != 0 && urls != null) {
            dataService.createAndUpdateDataMedias(data.getFileUrls(), sessionId, leadId);
        }
        return MyResponse.response("OK");
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        log.info("IoDataRequest delete id: {}", id);
        Boolean isUpdate = dataService.delete(id);
        RObject<?> responseObject = new RObject<>(ErrorData.SUCCESS, isUpdate ? "OK" : "FALSE");
        return ResponseEntity.ok(responseObject);
    }



}
