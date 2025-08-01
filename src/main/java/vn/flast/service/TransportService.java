package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.entities.TransportFilter;
import vn.flast.models.Transporter;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.TransporterRepository;
import vn.flast.utils.EntityQuery;

@Service
@RequiredArgsConstructor
public class TransportService {

    @PersistenceContext
    protected EntityManager entityManager;

    private final TransporterRepository transportRepository;

    public Transporter create(Transporter transport) {
        return transportRepository.save(transport);
    }

    public Transporter update(Transporter transport) {
        return transportRepository.save(transport);
    }

    public Ipage<?> fetch(TransportFilter filter) {
        var et = EntityQuery.create(entityManager, Transporter.class);
        et.longEqualsTo("orderId", filter.getOrderId());
        et.between("inTime", filter.getFrom(), filter.getTo());
        et.addDescendingOrderBy("id");
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.getLimit() * filter.page());
        var lists = et.list();
        return Ipage.generator(filter.getLimit(), et.count(), filter.page(), lists);
    }
}
