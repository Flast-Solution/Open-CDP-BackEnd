package vn.flast.service.cskh;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.controller.common.BaseController;
import vn.flast.entities.ComplaintFilter;
import vn.flast.models.DataComplaint;
import vn.flast.models.UserKpi;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataComplaintRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

import java.util.List;
import java.util.Optional;

@Service
public class DataComplaintService extends BaseController {

    @Autowired
    private CustomerPersonalRepository customerRepository;

    @Autowired
    private DataComplaintRepository dataComplaintRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public DataComplaint createComplaint(DataComplaint input){
        input.setStaff(getUsername());
        var customer = customerRepository.findByPhone(input.getCustomerMobile());
        input.setCustomerMobile(Optional.ofNullable(customer.getMobile()).orElse("0"));
        input.setCustomerEmail(Optional.ofNullable(customer.getEmail()).orElse(""));
        input.setCustomerFacebook(Optional.ofNullable(customer.getFacebookId()).orElse("0"));
        input.setCustomerName(Optional.ofNullable(customer.getName()).orElse(""));
        return dataComplaintRepository.save(input);
    }

    public DataComplaint updateComplaint(DataComplaint input) {
        var data = dataComplaintRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("no record exists")
        );
        CopyProperty.CopyIgnoreNull(input, data);
        input.setStaff(getUsername());
        return dataComplaintRepository.save(data);
    }
    public Ipage<?> fetchComplaint(ComplaintFilter filter){
        EntityQuery<DataComplaint> et = EntityQuery.create(entityManager, DataComplaint.class);
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.getPage() * filter.getLimit());
        et.stringEqualsTo("customerMobile", filter.getPhone());
        et.integerEqualsTo("saleId", filter.getSaleId());
        et.stringEqualsTo("knOrderCode", filter.getCode());
        et.addDescendingOrderBy("id");
        List<DataComplaint> results = et.list();
        long countItems = et.count();
        return new Ipage<>(filter.getLimit(), Math.toIntExact(countItems), filter.getPage(), results);
    }
}
