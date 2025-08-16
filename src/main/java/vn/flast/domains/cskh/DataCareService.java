package vn.flast.domains.cskh;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.controller.BaseController;
import vn.flast.entities.lead.CSLeadData;
import vn.flast.entities.lead.LeadCareFilter;
import vn.flast.entities.lead.NoOrderFilter;
import vn.flast.models.Data;
import vn.flast.models.DataCare;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataCareRepository;
import vn.flast.repositories.DataRepository;
import vn.flast.service.DataService;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.MapUtils;
import vn.flast.utils.SqlBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DataCareService extends BaseController {

    @Autowired
    private DataCareRepository dataCareRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private CustomerPersonalRepository customerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Ipage<?> fetchLead3DayReady(LeadCareFilter filter) {

        int currentPage =  filter.page();
        String initQuery = "FROM `data_care` a LEFT JOIN `data` b on a.`data_id` = b.`id`";
        SqlBuilder sqlBuilder = SqlBuilder.init(initQuery);
        sqlBuilder.addStringEquals("a.object_type", DataCare.OBJECT_TYPE_LEAD);
        if(StringUtils.isNotEmpty(filter.getPhone())) {
            sqlBuilder.addStringEquals("b.customer_mobile", filter.getPhone());
        }
        if(StringUtils.isNotEmpty(filter.getCause())) {
            sqlBuilder.addStringEquals("a.cause", filter.getCause());
        }
        sqlBuilder.addDateBetween("b.in_time", filter.getFrom(), filter.getTo());
        sqlBuilder.addOrderByDesc("a.id");
        String finalQuery = sqlBuilder.builder();

        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT a.* " + finalQuery, DataCare.class);
        nativeQuery.setMaxResults(filter.getLimit());
        nativeQuery.setFirstResult(filter.getLimit() * currentPage);
        if(count.equals(0L)) {
            return Ipage.empty();
        }

        var listDataCare = EntityQuery.getListOfNativeQuery(nativeQuery, DataCare.class);
        var listIds = listDataCare.stream().map(DataCare::getObjectId).toList();
        var leads = dataRepository.fetchDataIds(listIds);
        Map<Long, Data> mLeads = MapUtils.toIdentityMap(leads, Data::getId);

        List<CSLeadData> listRet = new ArrayList<>();
        for(DataCare cs : listDataCare) {
            var csLeadData = new CSLeadData();
            csLeadData.setDataCare(cs);
            csLeadData.setData(mLeads.get(cs.getObjectId()));
            listRet.add(csLeadData);
        }
        return new Ipage<>(filter.getLimit(), Math.toIntExact(count), currentPage, listRet);
    }

    public Ipage<?> fetchLead3Day(NoOrderFilter filter){
        int LIMIT = filter.getLimit();
        int OFFSET = filter.page() * LIMIT;

        final String totalSQL = "FROM `data` d left join `data_care` r on d.id = r.data_id";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);;
        sqlBuilder.addIntegerEquals("d.sale_id", filter.getUserId());
        sqlBuilder.addStringEquals("d.customer_mobile", filter.getPhone());
        sqlBuilder.addIntegerEquals("d.source", filter.getSource());
        sqlBuilder.addIntegerEquals("d.from_department", Data.FROM_DEPARTMENT.FROM_DATA.value());
        sqlBuilder.addDateBetween("d.in_time", filter.getFrom(), filter.getTo());
        sqlBuilder.addIsEmpty("r.data_id");
        sqlBuilder.addNotIn("d.status", DataService.DATA_STATUS.THANH_CO_HOI.getStatusCode());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -3);
        Date dayBeforeYesterday = calendar.getTime();
        sqlBuilder.addDateLessThan("d.in_time", dayBeforeYesterday);

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT d.* " + finalQuery , Data.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.getListOfNativeQuery(nativeQuery, Data.class);
        return Ipage.generator(LIMIT, count, filter.page(), listData);
    }

    public DataCare update(DataCare dataCare) {
        if(Optional.ofNullable(dataCare.getObjectId()).isEmpty()) {
            throw new RuntimeException("Lead id does not exist .!");
        }
        var lead = dataRepository.findById(dataCare.getObjectId()).orElseThrow(
            () -> new RuntimeException("No lead exists .!")
        );
        var model = new DataCare();
        CopyProperty.CopyIgnoreNull(dataCare, model);
        model.setObjectId(lead.getId());
        model.setObjectType(DataCare.OBJECT_TYPE_LEAD);
        model.setUserName(getInfo().getSsoId());

        var customer = customerRepository.findByPhone(lead.getCustomerMobile());
        model.setCustomerId(Optional.ofNullable(customer.getId()).orElse(0L));
        dataCareRepository.save(model);

        lead.setPreSaleCall(Data.PreSaleCall.DA_LIEN_HE.value());
        dataRepository.save(lead);
        return model;
    }
}
