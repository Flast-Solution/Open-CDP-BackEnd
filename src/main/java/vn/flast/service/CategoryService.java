package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.Category;
import vn.flast.models.ProductAttributed;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CategoryRepository;
import vn.flast.repositories.ProductAttributedRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

@Service
public class CategoryService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;


    public Category created(Category input){
        return categoryRepository.save(input);
    }

    public Category updated(Category input) {
        var category = categoryRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, category);
        return categoryRepository.save(category);
    }

    public Ipage<?> fetch(Integer page){
        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, Category.class);
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        var data = categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        categoryRepository.delete(data);
    }
}
