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
import org.springframework.stereotype.Service;
import vn.flast.searchs.DataFilter;
import vn.flast.models.DataWork;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.SqlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataWorkService {


    @PersistenceContext
    protected EntityManager entityManager;

    public List<DataWork> findDataWork(int limit, int offset, DataFilter filter) {
        final String totalSQL = "from `data_work` d left join `customer_order` c on d.order_id = c.id ";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIntegerEquals("d.sale_id", filter.getSaleMember());
        sqlBuilder.addDateBetween("d.in_time", filter.getFrom(), filter.getTo());
        sqlBuilder.addStringEquals("c.code", filter.getCustomerOrder());
        EntityQuery<DataWork> et = EntityQuery.create(entityManager, DataWork.class);
        String ft = Optional.ofNullable(filter.getFilterOrderType()).orElse("");
        switch (ft) {
            case "notCohoi" -> et.lessThanOrEqualsTo("orderId", 0);
            case "cohoi" -> et.greaterThan("orderId", 0);
            case "cohoiNotOrder" -> et.stringEqualsTo("orderType", "cohoi");
            case "order" -> et.stringEqualsTo("orderType", "order");
        }
        String finalQuery = sqlBuilder.builder();
        var entityQuery =  EntityQuery.create(entityManager, DataWork.class);
        Long count = entityQuery.countOrSumWithNoParams("SELECT COUNT(DISTINCT d.`id`) as 'total'" + finalQuery);
        if(count.equals(0L)) {
            return new ArrayList<>();
        }
        var nativeQuery = entityManager.createNativeQuery("SELECT d.* " + finalQuery + "  ORDER BY d.in_time DESC", DataWork.class);
        nativeQuery.setMaxResults(limit);
        nativeQuery.setFirstResult(offset);
        return EntityQuery.getListOfNativeQuery(nativeQuery, DataWork.class);
    }
}
