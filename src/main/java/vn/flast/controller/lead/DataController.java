package vn.flast.controller.lead;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.controller.common.BaseController;
import vn.flast.searchs.DataFilter;
import vn.flast.entities.MyResponse;
import vn.flast.models.Data;
import vn.flast.pagination.Ipage;
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
            .map(Long::intValue).orElse(0);
        if(dataId != 0) {
            dataService.createAndUpdateDataMedias(iodata.getFileUrls(), sessionId, dataId);
        }
        return MyResponse.response(iodata);
    }

    @GetMapping(value = "/lists")
    public MyResponse<?> fetch(@RequestBody DataFilter data) {
        data.setFromDepartment(Data.FROM_DEPARTMENT.FROM_DATA.value());
        Ipage<?> dataRet = dataService.getListDataFromCustomerService(data);
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
        }

        dataService.Update(data);
        List<String> urls = ioData.getFileUrls();
        if(leadId != 0 && urls != null) {
            dataService.createAndUpdateDataMedias(data.getFileUrls(), sessionId, leadId);
        }
        return MyResponse.response("OK");
    }

    @PostMapping(value = "/delete")
    public MyResponse<?> delete(@RequestParam Long id) {
        log.info("IoDataRequest delete id: {}", id);
        Boolean isUpdate = dataService.delete(id);
        return MyResponse.response(isUpdate ? "OK" : "FALSE");
    }
}
