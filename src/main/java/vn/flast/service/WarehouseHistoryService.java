package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.WareHouseHistory;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.WareHouseHistoryRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

@Service
public class WarehouseHistoryService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private WareHouseHistoryRepository wareHouseHistoryRepository;

    public WareHouseHistory created(WareHouseHistory input){
        if(input.getProductId() == null){
            throw new RuntimeException("Data null");
        }
        var data = wareHouseHistoryRepository.save(input);
        return data;
    }

    public WareHouseHistory updated(WareHouseHistory input) {
        var warehouseHistory = wareHouseHistoryRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        CopyProperty.CopyIgnoreNull(input, warehouseHistory);
        var data = wareHouseHistoryRepository.save(warehouseHistory);
        return data;
    }

    public Ipage<?> fetch(Integer page){
        int LIMIT = 10;
        int currentPage = page - 1;
        var et = EntityQuery.create(entityManager, WareHouseHistory.class);
        et.setMaxResults(LIMIT).setFirstResult(LIMIT * currentPage);
        var lists = et.list();
        return  Ipage.generator(LIMIT, et.count(), currentPage, lists);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id){
        var data = wareHouseHistoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bản ghi không tồn tại !")
        );
        wareHouseHistoryRepository.delete(data);
    }
}
