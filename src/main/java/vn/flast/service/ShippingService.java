package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.Shipping;
import vn.flast.models.ShippingStatus;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.ShippingRepository;
import vn.flast.repositories.ShippingStatusRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import java.util.List;

@Service
public class ShippingService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private ShippingStatusRepository shippingStatusRepository;


    public Shipping created(Shipping input){
        return shippingRepository.save(input);
    }

    public Shipping updated(Shipping input) {
        var shipping = shippingRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, shipping);
        return shippingRepository.save(shipping);
    }

    public Ipage<?> fetch(Integer page){
        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, Shipping.class);
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id){
        var data = shippingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        shippingRepository.delete(data);
    }

    public ShippingStatus createStatus(ShippingStatus input){
        if(shippingStatusRepository.existsByName(input.getName())){
            return null;
        }

        return shippingStatusRepository.save(input);
    }

    public List<ShippingStatus> fetchStatus(){
        var data = shippingStatusRepository.findAll();
        return data;
    }
}
