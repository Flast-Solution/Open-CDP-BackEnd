package vn.flast.entities.order;
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




import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.entities.warehouse.SkuDetails;
import vn.flast.utils.NumberUtils;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class OrderDetail {

    private String key;
    private String note;
    private Long detailId;
    private Long productId;
    private String productName;
    private String orderName;
    private String skuDetailCode;
    private String unit;
    private String warrantyPeriod;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
    private Integer discountRate;
    private Double discountAmount;

    @JsonProperty("mSkuDetails")
    private List<SkuDetails> skuDetails;

    public Double getDiscountAmount() {
        return NumberUtils.gteZero(discountAmount) ? discountAmount : 0.0;
    }
}
