package vn.flast.domains.works;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import vn.flast.controller.BaseController;
import vn.flast.entities.MyResponse;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.FlastProjectList;
import vn.flast.models.FlastProjectTask;
import vn.flast.repositories.FlastProjectListRepository;
import vn.flast.searchs.WorkFilter;
import vn.flast.validator.ValidationErrorBuilder;
import java.util.Objects;

@RestController
@RequestMapping("/works")
@RequiredArgsConstructor
public class WorksController extends BaseController {

    private final WorkService workService;
    private final FlastProjectListRepository flastProjectListRepository;

    @GetMapping("/fetch")
    public MyResponse<?> fetch(WorkFilter filter) {
        var iPage = workService.fetch(filter);
        return MyResponse.response(iPage);
    }

    @GetMapping("/find/{id}")
    public MyResponse<?> fetch(@PathVariable Integer id) {
        var data = workService.findId(id);
        return MyResponse.response(data);
    }

    @PostMapping("/delete/{id}")
    public MyResponse<?> delete(@PathVariable Integer id) {
        var data = flastProjectListRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Not found !")
        );
        if(!Objects.equals(data.getManagerId(), getUserId())) {
            throw new RuntimeException("Not delete !");
        }
        flastProjectListRepository.delete(data);
        return MyResponse.response(200, "Xóa dự án thành công !");
    }

    @PostMapping("/save")
    public MyResponse<?> save(@Valid @RequestBody FlastProjectList model, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = workService.save(model);
        return MyResponse.response(data, "Cập nhật dự án thành công !");
    }

    @PostMapping("/save/task")
    public MyResponse<?> saveTask(@Valid @RequestBody FlastProjectTask model, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = workService.saveTask(model);
        return MyResponse.response(data, "Cập nhật công việc thành công !");
    }
}
