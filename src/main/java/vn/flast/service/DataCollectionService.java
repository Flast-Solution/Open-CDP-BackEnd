package vn.flast.service;
/**************************************************************************/
/*  DataCollectionService.java                                            */
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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.models.DataCollection;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.DataCollectionRepository;
import vn.flast.searchs.DataCollectionFilter;
import vn.flast.utils.EntityQuery;

@Service
@RequiredArgsConstructor
public class DataCollectionService {

    private final DataCollectionRepository collectionRepository;

    @Autowired
    private EntityManager entityManager;

    public DataCollection save(DataCollection input) {
        collectionRepository.save(input);
        return input;
    }

    public Ipage<DataCollection> fetch(DataCollectionFilter filter) {
        int LIMIT = 10;
        int PAGE = filter.page();
        var et = EntityQuery.create(entityManager, DataCollection.class);
        if(filter.onlyMobile()) {
            et.dataIsNotNull("mobile");
        }
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * PAGE);
        var lists = et.list();
        return Ipage.generator(LIMIT, et.count(), PAGE, lists);
    }
}
