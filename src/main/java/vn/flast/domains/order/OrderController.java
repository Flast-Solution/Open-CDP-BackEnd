package vn.flast.domains.order;
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
import vn.flast.entities.order.DeleteContract;
import vn.flast.entities.order.OrderDetail;
import vn.flast.entities.order.OrderResponse;
import vn.flast.entities.warehouse.SkuDetails;
import vn.flast.models.CustomerContract;
import vn.flast.models.CustomerOrder;
import vn.flast.repositories.CustomerContractRepository;
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

    @Autowired
    private CustomerContractRepository contractRepository;

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

            Double totalPrice = orderDetail.getQuantity() * orderDetail.getPrice();
            detail.setTotalPrice(totalPrice);

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
        var orders = orderService.fetchList(filter);
        return MyResponse.response(orders);
    }

    @GetMapping("/fetch-kanban")
    public MyResponse<?> fetchKanban() {
        var orders = orderService.fetchKanban();
        return MyResponse.response(orders);
    }

    @GetMapping("/fetch-kanban-detail")
    public MyResponse<?> fetchKanbanDetail(OrderFilter filter) {
        var orders = orderService.fetchKanbanDetail(filter);
        return MyResponse.response(orders);
    }

    @PostMapping("/complete")
    public MyResponse<?> complete(@RequestParam Long id) {
        CustomerOrder order = orderService.completeOrder(id);
        return MyResponse.response(order);
    }

    @PostMapping("/update-status-order")
    public MyResponse<?> updateStatusOrder(
        @RequestParam Long orderId,
        @RequestParam Long detailId,
        @RequestParam Integer statusId
    ){
        orderService.updateStatusOrder(orderId, detailId, statusId);
        return MyResponse.response("Oki", "Cập nhật đơn hàng thành công !");
    }

    @PostMapping("/cancel-co-hoi")
    public MyResponse<?> cancelCoHoi(@RequestParam Long orderId, @RequestParam Boolean detail){
        orderService.cancelCoHoi(orderId, detail);
        return MyResponse.response("oke");
    }

    @GetMapping("/get-contract")
    public MyResponse<?> findContract(@RequestParam String code) {
        var contracts = contractRepository.findByCode(code).stream().map(CustomerContract::createUrlFile);
        return MyResponse.response(contracts);
    }

    @PostMapping("/delete-contract-file")
    public MyResponse<?> findContract(@RequestParam String code, @RequestBody DeleteContract deleteContract) {
        if(deleteContract.file().isEmpty()) {
            throw new RuntimeException("File không được để trống");
        }
        orderService.deleteContract(code, deleteContract.file());
        return MyResponse.response("Xóa file thành công !");
    }
}
