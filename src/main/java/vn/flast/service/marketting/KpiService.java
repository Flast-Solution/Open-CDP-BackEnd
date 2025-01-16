package vn.flast.service.marketting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.components.GetUserRole;
import vn.flast.dao.DaoImpl;
import vn.flast.entities.SaleKpiProperty;
import vn.flast.models.UserKpi;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.UserKpiRepository;
import vn.flast.service.user.UserService;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.DateUtils;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class KpiService extends DaoImpl<Integer, UserKpi> {

    @Autowired
    private UserService userService;

    @Autowired
    private GetUserRole userRole;

    @Autowired
    private UserKpiRepository userKpiRepository;


    public Ipage<UserKpi> listKpi(int userId, int page, Integer idFilter, Integer month, Integer year, Integer department, Integer limit) {

        EntityQuery<UserKpi> et = EntityQuery.create(entityManager, UserKpi.class);
        et.setMaxResults(limit).setFirstResult(page * limit);
        if(userService.isAdmin(userId) || userService.isSaleManager(userId)) {
            // Admin va Tp Kinh doanh được xem hết và lọc theo sale
            if(idFilter != null) {
                et.integerEqualsTo("userId", idFilter);
            }
        } else {
            List<Integer> userIds = userRole.listUserIds();
            et.in("userId", userIds);
        }
        et.integerEqualsTo("department", department);
        et.integerEqualsTo("month", month);
        et.integerEqualsTo("year", year);
        et.addDescendingOrderBy("id");

        List<UserKpi> results = et.list();
        long countItems = et.count();
        return new Ipage<>(limit, Math.toIntExact(countItems), page, results);
    }

    public UserKpi create(SaleKpiProperty input){
        UserKpi data = new UserKpi();
        CopyProperty.CopyIgnoreNull(input, data);
        LocalDate localDate = DateUtils.Date2LocalDate(input.getInTime());
        data.setYear((long) localDate.getYear());
        data.setMonth((long) localDate.getMonthValue());
        if(!input.getListFee().isEmpty()) {
            data.setListFee(JsonUtils.toJson(input.getListFee()));
            data.setFee(input.calculatorTotalFee());
        }
        return userKpiRepository.save(data);
    }

    public UserKpi update(SaleKpiProperty input){
        if(Optional.ofNullable(input.getId()).isEmpty()) {
            throw new RuntimeException("Spectial Id of SaleKpi Not Found");
        }
        UserKpi data = userKpiRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("no record found")
        );

        CopyProperty.CopyIgnoreNull(input, data);
        LocalDate localDate = DateUtils.Date2LocalDate(input.getInTime());
        data.setYear((long) localDate.getYear());
        data.setMonth((long) localDate.getMonthValue());
        if(!input.getListFee().isEmpty()) {
            data.setListFee(JsonUtils.toJson(input.getListFee()));
            data.setFee(input.calculatorTotalFee());
        }
        return userKpiRepository.save(data);
    }

}
