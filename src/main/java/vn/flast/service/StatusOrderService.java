package vn.flast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.models.StatusOrder;
import vn.flast.repositories.StatusOrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusOrderService {

    private final StatusOrderRepository statusOrderRepository;

    public void create(StatusOrder input){
        if(statusOrderRepository.existsByName(input.getName())){
            return;
        }
       statusOrderRepository.save(input);
    }
    public List<StatusOrder> fetchStatus(){
        var data = statusOrderRepository.findAll();
        return data;
    }

}
