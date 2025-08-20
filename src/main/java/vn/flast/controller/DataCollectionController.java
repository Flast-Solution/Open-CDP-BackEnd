package vn.flast.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.DataCollection;
import vn.flast.pagination.Ipage;
import vn.flast.searchs.DataCollectionFilter;
import vn.flast.service.DataCollectionService;

@RestController
@Slf4j
@RequestMapping("/data-collection")
public class DataCollectionController extends BaseController {

    @Autowired
    private DataCollectionService dataCollectionService;

    @GetMapping("/fetch")
    public MyResponse<?> getData(DataCollectionFilter filter){
        Ipage<DataCollection> ipage = dataCollectionService.fetch(filter);
        return MyResponse.response(ipage);
    }

    @PostMapping("/save")
    public MyResponse<?> saveData(@RequestBody DataCollection dataCollection){
        var entity = dataCollectionService.save(dataCollection);
        return MyResponse.response(entity);
    }
}
