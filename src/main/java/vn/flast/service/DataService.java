package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.controller.BaseController;
import vn.flast.domains.customer.CustomerPersonalService;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.*;
import vn.flast.orchestration.EventDelegate;
import vn.flast.orchestration.EventTopic;
import vn.flast.orchestration.Message;
import vn.flast.orchestration.MessageInterface;
import vn.flast.orchestration.PubSubService;
import vn.flast.orchestration.Publisher;
import vn.flast.orchestration.Subscriber;
import vn.flast.repositories.*;
import vn.flast.searchs.DataFilter;
import vn.flast.pagination.Ipage;
import vn.flast.utils.BuilderParams;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.DateUtils;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.NumberUtils;
import vn.flast.utils.SqlBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service("dataService")
@RequiredArgsConstructor
public class DataService extends Subscriber implements Publisher {

    private final DataMediaRepository dataMediaRepository;
    private final DataRepository dataRepository;
    private final DataWorkService dataWorkService;
    private final UserRepository userRepository;
    private final DataOwnerRepository dataOwnerRepository;
    private final BaseController baseController;
    private final ProductRepository productRepository;

    @Autowired
    @Lazy
    private CustomerPersonalService customerPersonalService;

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
        while(iterator.hasNext()) {
            var message = iterator.next();
            log.info("Message Topic -> {} : {}", message.getTopic(), message.getPayload());
            iterator.remove();
        }
    }

    @Getter
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

        public String getString() {
            return String.valueOf(this.statusCode);
        }
    }

    public void createAndUpdateDataMedias(int sessionId, Long dataId) {
        updateDataMedias(dataId, sessionId);
    }

    public BuilderParams saveData(Data model) {
        if(NumberUtils.isNull(model.getProductId())) {
            throw new RuntimeException("Sản phẩm chưa được cấu hình trên hệ thống ");
        }
        Product product = productRepository.findById(model.getProductId()).orElseThrow(
            () -> new ResourceNotFoundException("Sản phẩm Not Found !")
        );
        model.setProductName(product.getName());
        if(NumberUtils.isNull(model.getServiceId())) {
            model.setServiceId(product.getServiceId());
        }

        DataOwner dataOwner = updateOwner(model);
        model.setSaleId(dataOwner.getSaleId());
        model.setAssignTo(dataOwner.getSaleName());

        Data entity = dataRepository.save(model);
        CustomerPersonal customerPersonal = customerPersonalService.createCustomerFromData(entity);
        sendMessageOnOrderChange(entity);
        return BuilderParams.create()
            .addParam("dataId", entity.getId())
            .addParam("data", entity)
            .addParam("mCustomer", customerPersonal);
    }

    private void sendMessageOnOrderChange(Data model) {
        this.publish(Message.create(EventTopic.DATA_CHANGE, model.clone()));
    }

    public Boolean delete(Long id) {
        Data model = this.findById(id);
        if(Objects.nonNull(model)) {
            this.remove(model);
        }
        return model != null;
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

    public Ipage<Data> getListDataFromCustomerService(DataFilter filter) {

        var user = baseController.getInfo();
        var LIMIT = filter.getLimit();
        var offset = filter.page() * LIMIT;

        EntityQuery<Data> et = EntityQuery.create(entityManager, Data.class);
        et.between("inTime", filter.getFrom(), filter.getTo());
        et.integerEqualsTo("source", filter.getSource());
        if (user.getAuthorities().stream().anyMatch(auth
            -> auth.getAuthority().equals("ROLE_ADMIN")) || user.getAuthorities().stream().anyMatch(auth
            -> auth.getAuthority().equals("ROLE_SALE_MANAGER"))
        ) {
            et.integerEqualsTo("saleId", filter.getSaleId());
        } else {
            et.integerEqualsTo("saleId", user.getId());
        }

        List<Data> lists = et.stringEqualsTo("customerMobile", filter.getCustomerMobile())
            .stringEqualsTo("customerEmail", filter.getCustomerEmail())
            .integerEqualsTo("status", filter.getStatus())
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

        var nativeQuery = entityManager.createNativeQuery("SELECT d.* " + finalQuery + " ORDER BY d.in_time DESC", Data.class);
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
        } else {
            et.between("inTime", filter.getFrom(), filter.getTo());
        }
    }

    public Data findById(Long id) {
        var data = dataRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Không tồn tại bản ghi này")
        );
        List<String> medias = dataMediaRepository.findByDataId(id)
            .map(item -> String.valueOf(item.stream()
            .map(DataMedia::getFile))).stream().toList();
        data.setFileUrls(medias);
        return data;
    }

    public void updateDataMedias(Long dataId, Integer sessionId) {
        final String sql = "UPDATE data_media SET data_id = :dataId WHERE data_id = 0 AND session_id = :sessionId";
        entityManager.createNativeQuery(sql)
            .setParameter("dataId", dataId)
            .setParameter("sessionId", sessionId)
            .executeUpdate();
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void reAssignLeadManual(int leadId, int saleId) {
        var iodata = dataRepository.findById((long) leadId).orElseThrow(
            () -> new RuntimeException("Lead does not exist")
        );
        var sale = userRepository.findById(saleId).orElseThrow(
            () -> new RuntimeException("user does not exist")
        );
        iodata.setSaleId(sale.getId());
        iodata.setAssignTo(sale.getSsoId());

        DataOwner dataOwner = updateOwner(iodata);
        dataOwner.setSaleId(sale.getId());
        dataOwner.setSaleName(sale.getSsoId());
    }

    public DataOwner updateOwner(Data data) {
        DataOwner dataOwner = dataOwnerRepository.findByMobile(data.getCustomerMobile());
        if(Objects.isNull(dataOwner)) {
            User sale = userRepository.findById(data.getSaleId()).orElseThrow(
                () -> new RuntimeException("user does not exist")
            );
            dataOwner = new DataOwner();
            dataOwner.setCustomerMobile(data.getCustomerMobile());
            dataOwner.setDepartmentId(dataOwner.getDepartmentId());
            dataOwner.setSaleId(sale.getId());
            dataOwner.setSaleName(sale.getSsoId());
            dataOwner.setInTime(new Date());
            dataOwnerRepository.save(dataOwner);
        }
        return dataOwner;
    }
}
