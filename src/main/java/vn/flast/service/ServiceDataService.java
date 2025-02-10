package vn.flast.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.repositories.ServiceRepository;
import vn.flast.utils.CopyProperty;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceDataService {


    private final ServiceRepository serviceRepository;

    public vn.flast.models.Service createService(vn.flast.models.Service input){
        if(input.getName() == null || serviceRepository.existsByName(input.getName())){
            throw new RuntimeException("Lỗi đăng ký dịch vụ!!!");
        }
        return serviceRepository.save(input);
    }

    public vn.flast.models.Service updateService(vn.flast.models.Service input){
        var service = serviceRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("does not exist at this service")
        );
        CopyProperty.CopyIgnoreNull(input, service);
        return serviceRepository.save(service);
    }

    public List<vn.flast.models.Service> listService(){
        return serviceRepository.findServiceOn();
    }
}
