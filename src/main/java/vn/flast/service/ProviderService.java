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
import vn.flast.models.Provider;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ProviderRepository;
import vn.flast.searchs.ProviderFilter;
import vn.flast.utils.EntityQuery;

@Service
public class ProviderService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ProviderRepository providerRepository;

    public Provider save(Provider input){
        return providerRepository.save(input);
    }

    public Ipage<?> fetch(ProviderFilter filter){

        var et = EntityQuery.create(entityManager, Provider.class);
        et.like("name", filter.name())
            .stringEqualsTo("mobile", filter.mobile());
        var lists = et.list();
        return  Ipage.generator(10, et.count(), 1, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        var data = providerRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        providerRepository.delete(data);
    }
}
