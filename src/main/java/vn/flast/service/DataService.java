package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.orchestration.EventDelegate;
import vn.flast.orchestration.EventTopic;
import vn.flast.orchestration.MessageInterface;
import vn.flast.orchestration.PubSubService;
import vn.flast.orchestration.Publisher;
import vn.flast.orchestration.Subscriber;
import vn.flast.searchs.DataFilter;
import vn.flast.models.Data;
import vn.flast.models.DataMedia;
import vn.flast.models.DataWork;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.DataMediaRepository;
import vn.flast.repositories.DataRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.DateUtils;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.SqlBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service("dataService")
public class DataService extends Subscriber implements Publisher {

    @Autowired
    private DataMediaRepository dataMediaRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DataWorkService dataWorkService;

    @PersistenceContext
    private EntityManager entityManager;

    private EventDelegate eventDelegate;

    @Override
    public void setDelegate(EventDelegate eventDelegate) {
        this.eventDelegate = eventDelegate;
        this.eventDelegate.addEvent(this, EventTopic.ORDER_CHANGE);
    }

    @Override
    public void publish(MessageInterface message) {
        if(Objects.nonNull(eventDelegate)) {
            eventDelegate.sendEvent(message);
        }
    }

    @Override
    public void addSubscriber(String topic, PubSubService pubSubService) {
        pubSubService.addSubscriber(topic, this);
    }

    @Override
    public void unSubscribe(String topic, PubSubService pubSubService) {
        pubSubService.removeSubscriber(topic, this);
    }

    @Override
    public void executeMessage() {
        ListIterator<MessageInterface> iterator = subscriberMessages.listIterator();
        while(iterator.hasNext()){
            var message = iterator.next();
            log.info("Message Topic -> "+ message.getTopic() + " : " + message.getPayload());
            iterator.remove();
        }
    }

    public enum DATA_STATUS {

        CREATE_DATA(0),
        DO_NOT_MANUFACTORY(1),
        IS_CONTACT(2),
        CONTACT_LATER(6),
        KO_LIEN_HE_DUOC(4),
        THANH_CO_HOI(7);

        private final int statusCode;

        DATA_STATUS(int levelCode) {
            this.statusCode = levelCode;
        }

        public int getStatusCode() {
            return this.statusCode;
        }

        public String getString() {
            return String.valueOf(this.statusCode);
        }
    }

    public enum DATA_SOURCE {
        WEB(0),
        FACEBOOK(1),
        ZALO(2),
        HOTLINE(3),
        DIRECT(4),
        EMAIL(5),
        MKT0D(6),
        GIOITHIEU(7),
        CSKH(8),
        WHATSAPP(11),
        PARTNER(9),
        SHOPEE(10);
        private final int source;
        DATA_SOURCE(int source) {
            this.source = source;
        }
        public int getSource() {
            return this.source;
        }
    }

    public void createAndUpdateDataMedias(List<String> urls, int sessionId, int dataId) {
        if(!urls.isEmpty()) {
            urls.forEach(url -> dataMediaRepository.save(new DataMedia(dataId, sessionId, url)));
        }
    }

    public void saveData(Data data) {
        dataRepository.save(data);
    }

    public Boolean delete(Long id) {
        Data dd = this.findById(id);
        if(dd != null) {
            this.remove(dd);
        }
        return dd != null;
    }

    @Transactional
    public void remove(Data data) {
        Integer priToRep = DATA_STATUS.CREATE_DATA.getStatusCode();
        if (!priToRep.equals(data.getStatus())) {
            throw new RuntimeException("Lead không thể xoá vì đã được xử lý .!");
        }
        entityManager.remove(data);
    }


    @Transactional
    public void Update(Data input) {
        var data = dataRepository.findById(input.getId()).orElseThrow(
            () -> new RuntimeException("Không tồn tại bản ghi này")
        );
        CopyProperty.CopyIgnoreNull(input, data);
        dataRepository.save(data);
    }

    public Ipage<?> getAllData(DataFilter filter) {
        var LIMIT = filter.getLimit();
        var offset = filter.page() * LIMIT;
        EntityQuery<Data> et = EntityQuery.create(entityManager, Data.class);
        List<Data> lists = et.setFirstResult(offset)
            .stringEqualsTo("customerMobile", filter.getCustomerMobile())
            .integerEqualsTo("status", filter.getStatus())
            .integerEqualsTo("fromDepartment", filter.getFromDepartment())
            .integerEqualsTo("saleId", filter.getSaleMember())
            .integerEqualsTo("partnerId", filter.getPartnerId())
            .between("inTime", filter.getFrom(), filter.getTo())
            .addDescendingOrderBy("inTime")
            .setMaxResults(LIMIT)
            .list();

        return new Ipage<>(LIMIT, et.count(), filter.page(), lists);
    }

    public Ipage<Data> getListDataFromCustomerService(DataFilter filter) {
        var LIMIT = filter.getLimit();
        var offset = filter.page() * LIMIT;
        EntityQuery<Data> et = EntityQuery.create(entityManager, Data.class);
        et.between("inTime", filter.getFrom(), filter.getTo());
        et.integerEqualsTo("source", filter.getSource());
        List<Data> lists = et.integerEqualsTo("fromDepartment", filter.getFromDepartment())
            .stringEqualsTo("customerMobile", filter.getCustomerMobile())
            .stringEqualsTo("customerEmail", filter.getCustomerEmail())
            .integerEqualsTo("status", filter.getStatus())
            .integerEqualsTo("saleId", filter.getSaleId())
            .setFirstResult(offset)
            .setMaxResults(LIMIT)
            .addDescendingOrderBy("inTime")
            .list();
        return new Ipage<>(LIMIT,et.count(), filter.page(), lists);
    }

    public Ipage<Data> leadOfWork(DataFilter filter) {
        int LimitInWork = 20;
        List<DataWork> listWork = dataWorkService.findDataWork(LimitInWork, filter.page() * LimitInWork, filter);
        var leadIds = listWork.stream().map(DataWork::getDataId).toList();
        List<Data> data = this.findIdIn(leadIds);
        return new Ipage<>(LimitInWork, data.size(), filter.page(), data);
    }

    public List<Data> findIdIn(List<Integer> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        EntityQuery<Data> et = EntityQuery.create(entityManager, Data.class);
        return et.in("id", ids).list();
    }

    public Ipage<?> leadOfOrder(DataFilter filter) {
        int LIMIT = 20;
        int OFFSET = ( filter.page() - 1 ) * LIMIT;
        final String totalSQL = "FROM `data` d left join `customer_order` c on d.id = c.data_id ";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addStringEquals("c.code", filter.getOrderCode());
        sqlBuilder.addIntegerEquals("d.sale_id", filter.getSaleId());
        sqlBuilder.addStringEquals("d.customer_mobile", filter.getCustomerMobile());
        sqlBuilder.addIntegerEquals("d.source", filter.getSource());
        sqlBuilder.addIntegerEquals("d.from_department", Data.FROM_DEPARTMENT.FROM_DATA.value());
        sqlBuilder.addDateBetween("d.in_time", filter.getFrom(), filter.getTo());
        sqlBuilder.addOrderBy("ORDER BY d.id DESC");
        String ft = Optional.ofNullable(filter.getFilterOrderType()).orElse("");
        switch (ft) {
            case "notCohoi" -> sqlBuilder.addIsEmpty("c.id");
            case "cohoi" -> sqlBuilder.addNotEmpty("c.id");
            case "cohoiNotOrder" -> sqlBuilder.addStringEquals("c.type", "cohoi");
            case "order" -> sqlBuilder.addStringEquals("c.type", "order");
        }
        String finalQuery = sqlBuilder.builder();

        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT d.* " + finalQuery, Data.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.getListOfNativeQuery(nativeQuery, Data.class);
        return Ipage.generator(LIMIT, count, filter.page(), listData);
    }

    public Ipage<Data> getDataBySale(int currentPage, int saleId, boolean isLeader, DataFilter filter) {
        var LIMIT = filter.getLimit();
        EntityQuery<Data> et = EntityQuery.create(entityManager, Data.class);

        /* Lọc theo báo cáo hàng ngày thì không cần lọc theo sale và thời gian chỉ lấy trong ngày, bao gồm lead và cơ hội, đơn hàng */
        this.filterForSaleId(et, isLeader, filter, saleId);
        et.stringEqualsTo("customerMobile", filter.getCustomerMobile());
        et.integerEqualsTo("status", filter.getStatus());
        et.integerEqualsTo("source", filter.getSource());
        et.integerEqualsTo("fromDepartment", filter.getFromDepartment());
        et.addDescendingOrderBy("inTime");
        et.setMaxResults(LIMIT).setFirstResult(currentPage * LIMIT);

        var data = et.list();
        var totalItems = (int) et.count();
        return new Ipage<>(LIMIT, totalItems, currentPage, data);
    }

    private void filterForSaleId(EntityQuery<Data> et, boolean isLeader, DataFilter filter, int saleId) {
        if (filter.ofDaily()) {
            et.integerEqualsTo("saleId", filter.getSaleMember());
            Date currentDate = new Date();
            Date from = DateUtils.atStartOfDay(currentDate);
            Date to = DateUtils.atEndOfDay(currentDate);
            et.between("inTime", from, to);
            return;
        }
        et.between("inTime", filter.getFrom(), filter.getTo());
    }

    public Data findById(Long id) {
        var data = dataRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Không tồn tại bản ghi này")
        );
        if(data != null) {
            data.createListFileUploads();
        }
        return data;
    }

    public Data findByPhone(String phone) {
        var data = dataRepository.findFirstByPhone(phone);
        if(data != null) {
            data.createListFileUploads();
        }
        return data;
    }
}
