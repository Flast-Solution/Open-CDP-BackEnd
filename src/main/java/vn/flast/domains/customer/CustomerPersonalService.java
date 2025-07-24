package vn.flast.domains.customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.models.CustomerActivities;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerPersonal;
import vn.flast.models.Data;
import vn.flast.orchestration.MessageInterface;
import vn.flast.orchestration.PubSubService;
import vn.flast.orchestration.Subscriber;
import vn.flast.repositories.*;

import vn.flast.searchs.CustomerFilter;
import vn.flast.utils.DateUtils;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.NumberUtils;

import java.util.*;

@Slf4j
@Service("customerPersonalService")
public class CustomerPersonalService extends Subscriber {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerPersonalRepository customerPersonalRepository;

    @Autowired
    private CustomerActivitiesRepository customerActivitiesRepository;

    @Autowired
    private CustomerEnterpriseRepository enterpriseRepository;

    @Autowired
    private DataOwnerRepository dataOwnerRepository;

    public CustomerPersonal save(CustomerPersonal entity) {
        return customerPersonalRepository.save(entity);
    }

    public List<CustomerPersonal> find(CustomerFilter filter) {
        var et = EntityQuery.create(entityManager, CustomerPersonal.class);
        et.setMaxResults(20);
        et.addDescendingOrderBy("id");
        et.like("mobile", filter.mobile());
        et.like("name", filter.name());
        return et.list();
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
            try {
                if(message.isOrderChange() && message.getPayload() instanceof CustomerOrder order) {
                    onOrderChange(order);
                }
            } catch (Exception e) {
                log.error("Topic: {}, {}", message.getTopic(), e.getMessage());
            }
            iterator.remove();
        }
    }

    private void onOrderChange(CustomerOrder order) {
        var model = customerOrderRepository.findById(order.getId()).orElseThrow();
        var customer = customerPersonalRepository.findById(model.getId()).orElseThrow();
        if(order.isOrder()) {
            increaseNumOfOrder(customer);
            customerPersonalRepository.save(customer);
        }
        if(NumberUtils.isNotNull(customer.getCompanyId())){
            var enterprise = enterpriseRepository.findById(customer.getCompanyId()).orElseThrow();
            model.setEnterpriseName(enterprise.getCompanyName());
            model.setEnterpriseId(enterprise.getId());
            customerOrderRepository.save(model);
        }
    }

    public CustomerPersonal createCustomerFromData(Data data) {
        CustomerPersonal customer = customerPersonalRepository.findByPhone(data.getCustomerMobile());
        if(Objects.nonNull(customer)) {
            var dataOwner = dataOwnerRepository.findByMobile(data.getCustomerMobile());
            customer.setSaleId(dataOwner.getSaleId());
            customerPersonalRepository.save(customer);
        } else {
            customer = new CustomerPersonal();
            customer.setSaleId(data.getSaleId());
            customer.setMobile(data.getCustomerMobile());
            customer.setName(data.getCustomerName());
            customer.setFacebookId(data.getCustomerFacebook());
            customer.setSourceId(data.getServiceId());
            customer.setEmail(data.getCustomerEmail());
            customer.setAddress(data.getProvinceName());
            customerPersonalRepository.save(customer);
            createActivities(customer);
        }
        return customer;
    }

    private void createActivities(CustomerPersonal customerPersonal) {

        List<CustomerActivities> activities = new ArrayList<>();
        var activity = new CustomerActivities();

        activity.setCustomerId(customerPersonal.getId());
        activity.setName("Tư vấn lại 3 ngày chưa Cơ Hộ");
        activity.setUserId(customerPersonal.getSaleId());
        activity.setDueDate(DateUtils.addDays(new Date(), 3));
        activities.add(activity);

        var atBaoGia = activity.clone();
        atBaoGia.setName("Gửi Báo giá");
        atBaoGia.setDueDate(DateUtils.addDays(new Date(), 5));
        activities.add(atBaoGia);

        var atCoHoi = activity.clone();
        atCoHoi.setName("Gọi điện 7 ngày chưa ra đơn");
        atCoHoi.setDueDate(DateUtils.addDays(new Date(), 7));
        activities.add(atCoHoi);
        customerActivitiesRepository.saveAll(activities);
    }

    public void increaseNumOfOrder(CustomerPersonal customer) {
        if(customer != null) {
            int numOrderOfCustomer = customerOrderRepository.countOrder(customer.getId(), "order");
            customer.setNumOfOrder(numOrderOfCustomer + 1);
        }
    }
}
