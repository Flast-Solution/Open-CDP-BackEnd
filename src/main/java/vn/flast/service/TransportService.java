package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.entities.TransportFilter;
import vn.flast.models.DetailItem;
import vn.flast.models.Transport;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.TransportRepository;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.JsonUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final TransportRepository transportRepository;

    public Transport create(Transport transport) {
        return transportRepository.save(transport);
    }

    public Transport update(Transport transport) {
        return transportRepository.save(transport);
    }
    public Ipage<?> fetch(TransportFilter filter) {
        var et = EntityQuery.create(entityManager, Transport.class);
        et.longEqualsTo("orderId", filter.getOrderId());
        et.between("inTime", filter.getFrom(), filter.getTo());
        et.addDescendingOrderBy("id");
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.getLimit() * filter.page());
        var lists = et.list();
        lists.forEach(item -> {
            item.setItems(JsonUtils.Json2ListObject(item.getInfo(), DetailItem.class));
        });
        return Ipage.generator(filter.getLimit(), et.count(), filter.page(), lists);
    }

    public List<Transport> findOrederId(Long orderId) {
        return transportRepository.findByOrderId(orderId);
    }
}
