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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.entities.TransportFilter;
import vn.flast.models.Transporter;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.TransporterRepository;
import vn.flast.utils.EntityQuery;

@Service
@RequiredArgsConstructor
public class TransportService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final TransporterRepository transportRepository;

    public Transporter create(Transporter transport) {
        return transportRepository.save(transport);
    }

    public Transporter update(Transporter transport) {
        return transportRepository.save(transport);
    }

    public Ipage<?> fetch(TransportFilter filter) {
        var et = EntityQuery.create(entityManager, Transporter.class);
        et.longEqualsTo("orderId", filter.getOrderId());
        et.between("inTime", filter.getFrom(), filter.getTo());
        et.addDescendingOrderBy("id");
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.getLimit() * filter.page());
        var lists = et.list();
        return Ipage.generator(filter.getLimit(), et.count(), filter.page(), lists);
    }
}
