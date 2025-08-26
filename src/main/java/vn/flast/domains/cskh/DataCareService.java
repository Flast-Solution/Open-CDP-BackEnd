package vn.flast.domains.cskh;
/**************************************************************************/
/*  package vn.flast.domains.cskh                                         */
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.flast.controller.BaseController;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.lead.CSLeadData;
import vn.flast.entities.lead.LeadCareFilter;
import vn.flast.entities.lead.NoOrderFilter;
import vn.flast.models.CustomerOrder;
import vn.flast.models.Data;
import vn.flast.models.DataCare;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataCareRepository;
import vn.flast.repositories.DataRepository;
import vn.flast.service.DataService;
import vn.flast.utils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataCareService extends BaseController {

    private final DataCareRepository dataCareRepository;
    private final DataRepository dataRepository;
    private final CustomerPersonalRepository customerRepository;
    private final OrderService orderService;

    @PersistenceContext
    private EntityManager entityManager;

    public Ipage<?> fetch(LeadCareFilter filter) {

        int PAGE =  filter.page();
        int LIMIT = filter.getLimit();

        String initQuery = "FROM `data_care`";
        SqlBuilder sqlBuilder = SqlBuilder.init(initQuery);
        sqlBuilder.addStringEquals("object_type", filter.getType());
        sqlBuilder.addStringEquals("cause", filter.getCause());
        sqlBuilder.addDateBetween("created_at", filter.getFrom(), filter.getTo());
        sqlBuilder.addOrderByDesc("id");
        String finalQuery = sqlBuilder.builder();

        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT a.* " + finalQuery, DataCare.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(LIMIT * PAGE);
        if(count.equals(0L)) {
            return Ipage.empty();
        }

        var listDataCare = EntityQuery.<DataCare>getListOfNativeQuery(nativeQuery);
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
        return new Ipage<>(filter.getLimit(), Math.toIntExact(count), PAGE, listRet);
    }

    public Ipage<Data> fetchLead3Day(NoOrderFilter filter){
        int LIMIT = filter.getLimit();
        int OFFSET = filter.page() * LIMIT;

        final String totalSQL = "FROM `data` d left join `data_care` r on d.id = r.object_id AND r.object_type = 'lead'";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIsEmpty("r.object_id");
        sqlBuilder.addIntegerEquals("d.sale_id", filter.getUserId());
        sqlBuilder.addStringEquals("d.customer_mobile", filter.getPhone());
        sqlBuilder.addIntegerEquals("d.source", filter.getSource());
        sqlBuilder.addDateBetween("d.in_time", filter.getFrom(), filter.getTo());
        sqlBuilder.addNotIn("d.status", DataService.DATA_STATUS.THANH_CO_HOI.getStatusCode());

        Date dayBefore = DateUtils.addDays(new Date(), -7);
        sqlBuilder.addDateLessThan("d.in_time", dayBefore);

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT d.* " + finalQuery , Data.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.<Data>getListOfNativeQuery(nativeQuery);
        return Ipage.generator(LIMIT, count, filter.page(), listData);
    }

    public DataCare update3Day(DataCare dataCare) {
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
        model.setInformation(JsonUtils.toJson(dataCare.getLead3Day()));

        var customer = customerRepository.findByPhone(lead.getCustomerMobile());
        model.setCustomerId(Optional.ofNullable(customer.getId()).orElse(0L));
        dataCareRepository.save(model);

        lead.setPreSaleCall(Data.PreSaleCall.DA_LIEN_HE.value());
        dataRepository.save(lead);
        return model;
    }

    public Ipage<CustomerOrder> fetchCoHoiOrder(NoOrderFilter filter){
        int LIMIT = filter.getLimit();
        int OFFSET = filter.page() * LIMIT;

        String totalSQL = "FROM `customer_order` d left join `data_care` r on d.id = r.object_id ";
        totalSQL = totalSQL.concat("AND r.object_type = '").concat(filter.getType()).concat("'");

        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIsEmpty("r.object_id");
        sqlBuilder.addIntegerEquals("d.user_create_id", filter.getUserId());
        sqlBuilder.addStringEquals("d.customer_mobile_phone", filter.getPhone());
        sqlBuilder.addIntegerEquals("d.source", filter.getSource());
        sqlBuilder.addStringEquals("d.type", filter.getType());

        if("cohoi".equals(filter.getType())) {
            Date dayBefore = DateUtils.addDays(new Date(), -7);
            sqlBuilder.addDateLessThan("d.created_at", dayBefore);
        }
        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT d.* " + finalQuery , CustomerOrder.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        List<CustomerOrder> listOrders = EntityQuery.getListOfNativeQuery(nativeQuery);
        List<CustomerOrder> orders = orderService.transformDetails(listOrders);
        return Ipage.generator(LIMIT, count, filter.page(), orders);
    }

    public DataCare updatedOrder(DataCare dataCare) {
        if(Optional.ofNullable(dataCare.getObjectId()).isEmpty()) {
            throw new RuntimeException("ObjectId id does not exist .!");
        }
        var order = orderService.view(dataCare.getObjectId());
        var model = new DataCare();
        CopyProperty.CopyIgnoreNull(dataCare, model);
        model.setObjectId(order.getId());
        model.setUserName(getInfo().getSsoId());
        model.setInformation(JsonUtils.toJson(dataCare.getLead3Day()));

        var customer = customerRepository.findById(order.getCustomerId()).orElseThrow(
            () -> new RuntimeException("")
        );
        model.setCustomerId(customer.getId());
        dataCareRepository.save(model);
        return model;
    }
}
