package vn.flast.service.marketting;
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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.dao.DaoImpl;
import vn.flast.entities.KpiInput;
import vn.flast.models.UserKpi;
import vn.flast.pagination.Ipage;
import vn.flast.searchs.KPIFilter;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.DateUtils;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;
import java.time.LocalDate;
import java.util.List;

@Service
public class KpiService extends DaoImpl<Integer, UserKpi> {

    public KpiService() {
        super(UserKpi.class);
    }

    public Ipage<UserKpi> listKpi(KPIFilter filter) {

        int LIMIT = filter.limit();
        int PAGE = filter.page();

        EntityQuery<UserKpi> et = EntityQuery.create(entityManager, UserKpi.class);
        et.setMaxResults(LIMIT).setFirstResult(PAGE * LIMIT);
        et.in("userId", filter.userIds());
        et.integerEqualsTo("month", filter.month());
        et.integerEqualsTo("year", filter.year());
        et.addDescendingOrderBy("id");

        long countItems = et.count();
        List<UserKpi> results = et.list();
        results.forEach(UserKpi::assignKPI);
        return Ipage.generator(LIMIT, countItems, PAGE, results);
    }

    @Transactional
    public UserKpi save(KpiInput input){
        UserKpi data = new UserKpi();
        CopyProperty.CopyIgnoreNull(input, data);
        LocalDate localDate = DateUtils.Date2LocalDate(input.getInTime());
        data.setYear((long) localDate.getYear());
        data.setMonth((long) localDate.getMonthValue());
        if(!input.getListKpi().isEmpty()) {
            data.setContent(JsonUtils.toJson(input.getListKpi()));
        }
        return this.persist(data);
    }
}
