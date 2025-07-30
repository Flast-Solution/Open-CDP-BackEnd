package vn.flast.domains.stock;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.Transporter;
import vn.flast.repositories.TransporterRepository;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/transporter")
public class TransporterController {

    @Autowired
    private TransporterRepository transporterRepository;

    @PostMapping("/save")
    public MyResponse<?> created(@Valid  @RequestBody Transporter input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Invalid input .!");
        }
        var attributed = transporterRepository.save(input);
        return MyResponse.response(attributed, "Tạo mới thuộc tính thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch() {
        var data = transporterRepository.findAll();
        return MyResponse.response(data);
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Long id) {
        transporterRepository.deleteById(id);
        return MyResponse.response("Xáo bản ghi thành công .!");
    }
}
