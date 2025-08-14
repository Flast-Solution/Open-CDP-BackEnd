package vn.flast.service;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/




import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.Attributed;
import vn.flast.models.AttributedValue;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.AttributedRepository;
import vn.flast.searchs.AttributedFilter;
import vn.flast.utils.EntityQuery;

@Service
public class AttributedService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private AttributedRepository attributedRepository;

    @Transactional(rollbackFor = Exception.class)
    public Attributed save(Attributed input) {
        attributedRepository.findOneByName(input.getName()).ifPresentOrElse(
            (item) -> { /* Đã có rồi thì không lưu nữa */ },
            () -> attributedRepository.save(input)
        );
        return input;
    }

    public Ipage<?> fetch(AttributedFilter filter) {
        int LIMIT = 20;
        int currentPage = filter.page();
        var et = EntityQuery.create(entityManager, Attributed.class);
        et.like("name", filter.name());
        et.like("value", filter.value());
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    public Ipage<?> fetchAttributedValue(AttributedFilter filter) {
        int LIMIT = 20;
        int currentPage = filter.page();
        var et = EntityQuery.create(entityManager, AttributedValue.class);
        et.integerEqualsTo("attributedId", filter.attributedId());
        et.like("value", filter.value());
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        return  et.toPage();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        var data = attributedRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        attributedRepository.delete(data);
    }
}
