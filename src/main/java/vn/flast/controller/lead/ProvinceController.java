package vn.flast.controller.lead;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.repositories.ProvinceRepository;
import vn.flast.searchs.DataFilter;

@Log4j2
@RestController
@RequestMapping("/province")
@RequiredArgsConstructor
public class ProvinceController {


    private final ProvinceRepository provinceRepository;

    @GetMapping(value = "/find")
    public MyResponse<?> findByParent(@RequestParam(defaultValue = "0") Integer id ) {
        var data = provinceRepository.listByParent(id);
        return MyResponse.response(data);
    }
}
