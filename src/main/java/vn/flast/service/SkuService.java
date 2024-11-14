package vn.flast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.models.ProductSkus;
import vn.flast.repositories.ProductSkusDetailsRepository;
import vn.flast.repositories.ProductSkusRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkuService {

    @Autowired
    private ProductSkusRepository productSkusRepository;

    @Autowired
    private ProductSkusDetailsRepository productSkusDetailsRepository;


    List<ProductSkus> listProductSkuAndDetail(Long productId){
        List<ProductSkus> skusList = productSkusRepository.findByProductId(productId);
        skusList.forEach(productSkus -> {
            var skuDetails = productSkusDetailsRepository.findBySkuId(productSkus.getId());
            productSkus.setSkuDetail(skuDetails);
        });
        return skusList;
    }
}
