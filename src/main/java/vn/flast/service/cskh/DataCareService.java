package vn.flast.service.cskh;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.controller.BaseController;
import vn.flast.entities.lead.CskhLeadData;
import vn.flast.entities.lead.LeadCareFilter;
import vn.flast.entities.lead.NoOrderFilter;
import vn.flast.models.Data;
import vn.flast.models.DataCare;
import vn.flast.models.Product;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataCareRepository;
import vn.flast.repositories.DataRepository;
import vn.flast.repositories.ProductRepository;
import vn.flast.service.DataService;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.SqlBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DataCareService extends BaseController {

    @Autowired
    private DataCareRepository dataCareRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private CustomerPersonalRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Ipage<?> fetchLeadTookCare(LeadCareFilter filter){
        int currentPage =  filter.page();
        String initQuery = "FROM `data_care` a LEFT JOIN `data` b on a.`data_id` = b.`id`";
        SqlBuilder sqlCondiBuilder = SqlBuilder.init(initQuery);
        if(StringUtils.isNotEmpty(filter.getPhone())) {
            sqlCondiBuilder.addStringEquals("b.customer_mobile", filter.getPhone());
        }
        if(StringUtils.isNotEmpty(filter.getCause())) {
            sqlCondiBuilder.addStringEquals("a.cause", filter.getCause());
        }
        sqlCondiBuilder.addDateBetween("b.in_time", filter.getFrom(), filter.getTo());
        sqlCondiBuilder.addOrderBy("ORDER BY a.id DESC");
        String finalQuery = sqlCondiBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlCondiBuilder.countQueryString());
        Long count = sqlCondiBuilder.countOrSumQuery(countQuery);
        var ccvs = entityManager.createNativeQuery("SELECT a.* " + finalQuery, DataCare.class);
        ccvs.setMaxResults(filter.getLimit());
        ccvs.setFirstResult(filter.getLimit() * currentPage);
        if(count.equals(0L)) {
            return new Ipage<>(filter.getLimit(), 0, currentPage, null);
        }
        var dataCcvs = EntityQuery.getListOfNativeQuery(ccvs, DataCare.class);
        var listIds = dataCcvs.stream().map(DataCare::getDataId).toList();
        var datas = dataRepository.fetchDataIds(listIds);
        List<CskhLeadData> listRet = new ArrayList<>();
        for(DataCare cs : dataCcvs) {
            var cskhLeadData = new CskhLeadData();
            cskhLeadData.setDataCare(cs);
            var data = datas.stream().filter(item -> item.getId().equals(cs.getDataId())).findFirst().orElse(null);
            cskhLeadData.setData(data);
            listRet.add(cskhLeadData);
        }
        return new Ipage<>(filter.getLimit(), Math.toIntExact(count), currentPage, listRet);
    }

    public Ipage<?> fetchLeadNoCare(NoOrderFilter filter){
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

    public DataCare createLeadCare(DataCare dataCare){
        if(Optional.ofNullable(dataCare.getDataId()).isEmpty()) {
            throw new RuntimeException("Lead id does not exist .!");
        }
        var lead = dataRepository.findById(dataCare.getDataId()).orElseThrow(
                () -> new RuntimeException("No lead exists .!")
        );
        var model = new DataCare();
        CopyProperty.CopyIgnoreNull(dataCare, model);
        model.setDataId(lead.getId());
        model.setUserNote(getInfo().getSsoId());
        model.setSale(Optional.ofNullable(lead.getAssignTo()).orElse("N/A"));
        if(lead.getProductId() != null) {
            Product product = productRepository.findById(lead.getProductId()).orElseThrow(
                    () -> new RuntimeException("No Product exists !.")
            );
            model.setProductName(Optional.ofNullable(product.getName()).orElse("--"));
        }
        var customer = customerRepository.findByPhone(lead.getCustomerMobile());
        model.setCustomerId(Optional.ofNullable(customer.getId()).orElse(0L));
        dataCareRepository.save(model);
        lead.setPreSaleCall(Data.PreSaleCall.DA_LIEN_HE.value());
        lead.setCsCause(model.getCause());
        lead.setCsTime(new Date());
        dataRepository.save(lead);
        return model;
    }

    public List<DataCare> findByCustomerId(Integer cid){
        var data = dataCareRepository.findByCustomerId(cid);
        return data;
    }
}
