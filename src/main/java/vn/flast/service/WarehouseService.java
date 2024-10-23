package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.WareHouseHistory;
import vn.flast.models.Warehouse;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.WarehouseRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

@Service
public class WarehouseService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private WarehouseRepository wareHouseRepository;


    public Warehouse created(Warehouse input){
        if(input.getProductId() == null){
            throw new RuntimeException("Data null");
        }
        var data = wareHouseRepository.save(input);
        return data;
    }

    public Warehouse updated(Warehouse input) {
        var warehouse = wareHouseRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, warehouse);
        var data = wareHouseRepository.save(warehouse);
        return data;
    }

    public Ipage<?> fetch(Integer page){
        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, Warehouse.class);
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id){
        var data = wareHouseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        wareHouseRepository.delete(data);
    }
}
