package vn.flast.domains.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.flast.orchestration.MessageInterface;
import vn.flast.orchestration.PubSubService;
import vn.flast.orchestration.Subscriber;
import java.util.ListIterator;

@Slf4j
@Service("customerService")
public class CustomerService extends Subscriber {

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
}
