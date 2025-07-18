package vn.flast.domains.customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.models.CustomerPersonal;
import vn.flast.models.Data;
import vn.flast.orchestration.EventTopic;
import vn.flast.orchestration.MessageInterface;
import vn.flast.orchestration.PubSubService;
import vn.flast.orchestration.Subscriber;
import vn.flast.repositories.CustomerOrderRepository;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataOwnerRepository;
import vn.flast.searchs.CustomerFilter;
import vn.flast.utils.EntityQuery;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

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
    private DataOwnerRepository dataOwnerRepository;

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
            log.info("Message Topic -> {} : {}", message.getTopic(), message.getPayload());
            if(EventTopic.DATA_CHANGE.equals(message.getTopic()) && message.getPayload() instanceof Data) {
                createCustomerOnData((Data) message.getPayload());
            }
            iterator.remove();
        }
    }

    private void createCustomerOnData(Data data) {
        var customer = customerPersonalRepository.findByPhone(data.getCustomerMobile());
        if(Objects.nonNull(customer)) {
            var owner = dataOwnerRepository.findByMobile(data.getCustomerMobile());
            customer.setSaleId(owner.getSaleId());
            customerPersonalRepository.save(customer);
            return;
        }
        customer = new CustomerPersonal();
        customer.setSaleId(data.getSaleId());
        customer.setMobile(data.getCustomerMobile());
        customer.setName(data.getCustomerName());
        customer.setFacebookId(data.getCustomerFacebook());
        customer.setSourceId(data.getServiceId());
        customer.setEmail(data.getCustomerEmail());
        customerPersonalRepository.save(customer);
    }

    public void increaseNumOfOrder(Long id) {
        CustomerPersonal customer = customerPersonalRepository.findById(id).orElseThrow(
            () -> new RuntimeException("no record found")
        );
        increaseNumOfOrder(customer);
        customerPersonalRepository.save(customer);
    }

    public void increaseNumOfOrder(CustomerPersonal customer) {
        if(customer != null) {
            int numOrderOfCustomer = customerOrderRepository.countOrder(customer.getId(), "order");
            customer.setNumOfOrder(numOrderOfCustomer + 1);
        }
    }
}
