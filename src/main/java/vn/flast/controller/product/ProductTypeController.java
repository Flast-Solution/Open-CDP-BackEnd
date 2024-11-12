package vn.flast.controller.product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import vn.flast.models.ProductType;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ProductTypeRepository;
import vn.flast.searchs.ProductServiceFilter;
import vn.flast.utils.EntityQuery;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/product-type")
public class ProductTypeController {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/save")
    public MyResponse<?> updated(@Valid @RequestBody ProductType input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = productTypeRepository.save(input);
        return MyResponse.response(data, "Cập nhật thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(ProductServiceFilter filter) {
        int LIMIT = filter.limit();
        int currentPage = filter.page();
        var et = EntityQuery.create(entityManager, ProductType.class);
        et.like("name", filter.name());
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        var ipage = Ipage.generator(LIMIT, et.count(), currentPage, lists);
        return MyResponse.response(ipage);
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Long id) {
        productTypeRepository.deleteById(id);
        return MyResponse.response("Xáo bản ghi thành công .!");
    }
}
