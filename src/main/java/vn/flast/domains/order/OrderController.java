package vn.flast.domains.order;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.entities.order.OrderDetail;
import vn.flast.entities.order.OrderResponse;
import vn.flast.entities.warehouse.SkuDetails;
import vn.flast.models.CustomerOrder;
import vn.flast.repositories.ProductRepository;
import vn.flast.searchs.OrderFilter;
import vn.flast.utils.BuilderParams;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.JsonUtils;
import vn.flast.utils.NumberUtils;
import vn.flast.validator.ValidationErrorBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/save")
    public MyResponse<?> create(@Valid @RequestBody OrderInput entity, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Input invalid .!");
        }
        CustomerOrder order = orderService.saveOpportunity(entity);
        return MyResponse.response(order, "Cập nhật đơn hàng thành công !");
    }

    @GetMapping("/view-on-edit")
    public MyResponse<?> getEdit(@RequestParam Long orderId) {
        OrderResponse order = orderService.view(orderId);
        BuilderParams dataParams = BuilderParams.create().addParam("customer", order.getCustomer());

        List<OrderDetail> details = new ArrayList<>();
        for(var orderDetail : order.getDetails()) {
            OrderDetail detail = new OrderDetail();
            detail.setDetailId(orderDetail.getId());
            detail.setKey(orderDetail.getCode());
            detail.setOrderName(orderDetail.getName());
            detail.setPrice(orderDetail.getPrice());

            if(!NumberUtils.isNull(orderDetail.getPriceOff())) {
                detail.setDiscountAmount(orderDetail.getPriceOff());
                int discountRate = (int) Math.round((orderDetail.getPriceOff() / orderDetail.getPrice()) * 100);
                detail.setDiscountRate(discountRate);
            } else {
                detail.setDiscountAmount(0.0);
                detail.setDiscountRate(0);
            }
            detail.setTotalPrice(orderDetail.getTotal());

            detail.setWarrantyPeriod(orderDetail.getWarrantyPeriod());
            detail.setQuantity(orderDetail.getQuantity());
            detail.setProductId(orderDetail.getProductId());
            detail.setProductName(orderDetail.getProductName());

            productRepository.findById(orderDetail.getProductId()).ifPresentOrElse(product
                -> detail.setUnit(product.getUnit()), () -> {
                    throw new RuntimeException("Product not found: " + orderDetail.getProductId());
                }
            );

            detail.setSkuDetailCode(String.valueOf(orderDetail.getSkuId()));
            detail.setSkuDetails(JsonUtils.Json2ListObject(orderDetail.getSkuInfo(), SkuDetails.class));
            details.add(detail);
        }

        CustomerOrder customerOrder = new CustomerOrder();
        CopyProperty.CopyIgnoreNull(order, customerOrder, "details");

        dataParams.addParam("order", customerOrder);
        dataParams.addParam("data", details);
        return MyResponse.response(dataParams.getParams());
    }

    @GetMapping("/view")
    public MyResponse<?> view(@RequestParam Long id) {
        CustomerOrder order = orderService.view(id);
        return MyResponse.response(order);
    }

    @GetMapping("/find-by-code")
    public MyResponse<?> findByCode(@RequestParam String code) {
        CustomerOrder order = orderService.findByCode(code);
        return MyResponse.response(order);
    }

    @GetMapping("/fetch")
    public MyResponse<?> list(OrderFilter filter) {
        OrderFilter updatedFilter = filter.withPage(filter.page() + 1).withType(CustomerOrder.TYPE_ORDER);
        var orders = orderService.fetchList(updatedFilter);
        return MyResponse.response(orders);
    }

    @PostMapping("/complete")
    public MyResponse<?> complete(@RequestParam Long id) {
        CustomerOrder order = orderService.completeOrder(id);
        return MyResponse.response(order);
    }

    @GetMapping("/fetch-by-process")
    public MyResponse<?> fetchByProcess(OrderFilter filter) {
        var orders = orderService.fetchListOrderStatus(filter);
        return MyResponse.response(orders);
    }

    @PostMapping("/update-status-order")
    public MyResponse<?> updateStatusOrder(@RequestParam Long orderId, @RequestParam Integer statusId){
        CustomerOrder order = orderService.updateStatusOrder(orderId, statusId);
        return MyResponse.response(order);
    }

    @PostMapping("/cancel-co-hoi")
    public MyResponse<?> cancelCoHoi(@RequestParam Long orderId, @RequestParam Boolean detail){
        orderService.cancelCoHoi(orderId, detail);
        return MyResponse.response("oke");
    }
}
