package vn.flast.entities.warehouse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import vn.flast.models.ShippingHistory;
import vn.flast.utils.CopyProperty;

public record Delivery(
    Integer id,
    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    String address,
    String customerMobilePhone,
    String customerReceiverName,

    @NotBlank(message = "Mã đơn không được để trống")
    String orderCode,

    @NotBlank(message = "Mã con không được để trống")
    String detailCode,

    Integer shippingCost,
    Integer cod,
    Integer provinceId,
    Integer wardId,

    @Positive(message = "Số lượng phải lớn hơn 0")
    Integer quantity,
    Integer status,

    @NotNull(message = "Chưa chọn kho giao")
    Integer warehouseId,

    String transporterCode,
    @NotNull(message = "Chưa chọn đơn vị vận chuyển")
    Integer transporterId,
    String note
){
    public ShippingHistory transformShip() {
        ShippingHistory ship = new ShippingHistory();
        CopyProperty.CopyIgnoreNull(this, ship);
        ship.setReceiverName(customerReceiverName());
        ship.setReceiverMobile(customerMobilePhone());
        return ship;
    }
}
