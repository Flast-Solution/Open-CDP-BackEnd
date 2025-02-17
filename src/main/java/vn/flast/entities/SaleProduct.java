package vn.flast.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.models.Product;
import vn.flast.models.ProductAttributed;
import vn.flast.models.ProductProperty;
import vn.flast.models.ProductSkus;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class SaleProduct extends Product {
    private List<ProductAttributed> listProperties;
    private List<ProductSkus> skus;
    private List<ProductProperty> listOpenInfo;
    private Long sessionId;
}
