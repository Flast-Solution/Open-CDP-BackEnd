package vn.flast.domains.customer;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.*;
import vn.flast.orchestration.MessageInterface;
import vn.flast.orchestration.PubSubService;
import vn.flast.orchestration.Subscriber;
import vn.flast.repositories.*;

import vn.flast.searchs.CustomerFilter;
import vn.flast.utils.DateUtils;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.NumberUtils;
import vn.flast.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("customerPersonalService")
public class CustomerPersonalService extends Subscriber {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CustomerTagsRepository tagsRepository;

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
        customerPersonalRepository.save(entity);
        var listAddress = entity.getCustomerAddress();
        if(Objects.nonNull(listAddress)) {
            listAddress.forEach(address -> address.setCustomerPersonal(entity));
            listAddress.forEach(address -> address.setCustomerId(entity.getId()));
        }
        return entity;
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
            if(StringUtils.isNull(customer.getAddress()) && StringUtils.isNotNull(order.getCustomerAddress())) {
                customer.setAddress(order.getCustomerAddress());
            }
            if(NumberUtils.isNull(customer.getProvinceId()) && NumberUtils.isNotNull(order.getCustomerProvinceId())) {
                customer.setProvinceId(order.getCustomerProvinceId());
                customer.setWardId(order.getCustomerWardId());
            }
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

    @Transactional
    public void saveTags(Long customerId, List<String> tags) {
        tagsRepository.deleteByCustomerId(customerId);
        List<CustomerTags> listTags = new ArrayList<>();
        for(String tag : tags) {
            CustomerTags customerTag = new CustomerTags();
            customerTag.setCustomerId(customerId);
            customerTag.setTag(tag);
            listTags.add(customerTag);
        }
        tagsRepository.saveAll(listTags);
    }

    public Map<Long, List<CustomerTags>> fetchTags(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }
        return Optional.ofNullable(tagsRepository.findByListId(ids))
        .orElse(Collections.emptyList())
        .stream()
        .collect(Collectors.groupingBy(CustomerTags::getCustomerId));
    }
}
