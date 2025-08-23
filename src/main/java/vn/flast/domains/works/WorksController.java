package vn.flast.domains.works;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.flast.entities.MyResponse;
import vn.flast.models.FlastProjectList;
import vn.flast.models.FlastProjectTask;
import vn.flast.searchs.WorkFilter;

@RestController
@RequestMapping("/works")
@RequiredArgsConstructor
public class WorksController {

    private final WorkService workService;

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

    @PostMapping("/save")
    public MyResponse<?> save(@RequestBody FlastProjectList model) {
        var data = workService.save(model);
        return MyResponse.response(data);
    }

    @PostMapping("/save/task")
    public MyResponse<?> saveTask(@RequestBody FlastProjectTask model) {
        var data = workService.saveTask(model);
        return MyResponse.response(data);
    }
}
