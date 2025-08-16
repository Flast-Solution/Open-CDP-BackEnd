package vn.flast.service;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/





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
