package vn.flast.controller.order;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.components.QuoteBuilder;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.MyResponse;
import vn.flast.entities.order.OrderContent;
import vn.flast.entities.QuoteOrder;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.repositories.ConfigRepository;
import vn.flast.repositories.DetailItemRepository;
import vn.flast.service.MediaService;
import vn.flast.service.user.UserService;
import vn.flast.utils.CopyProperty;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/qoute")
@RequiredArgsConstructor
public class QuoteController {

    private final OrderService orderService;

    private final QuoteBuilder quoteBuilder;

    private final DetailItemRepository detailItemRepository;

    private final MediaService mediaService;

    private final UserService userService;

    private final ConfigRepository configRepository;

    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    public ResponseEntity<?> invoice(@RequestParam("id") Long id, @RequestParam(required = false) String code) {
        var findOrder = StringUtils.isNotEmpty(code)
                ? orderService.findByCode(code)
                : orderService.findById(id);
        CustomerOrder order = Optional.ofNullable(findOrder).orElseThrow(
                () -> new RuntimeException("Không tìm thấy Cơ Hội!")
        );
//        CustomerPersonal customer = findOrder.getCustomer();
        String strOrder = quoteBuilder.create(order, false).render();
        Document doc = Jsoup.parse(strOrder);
        Element mainContent = doc.selectFirst("tr#main__content");
        String extractedContent = mainContent != null ? mainContent.outerHtml() : "";
        Element infoCustomer = doc.selectFirst("td#info__customer");
        String extractedCustomer = infoCustomer != null ? mainContent.outerHtml() : "";
        Element infoSale = doc.selectFirst("td#info__sale");
        String extractedSale = infoSale != null ? mainContent.outerHtml() : "";
        Element infoPay = doc.selectFirst("tr#note__content");
        String extractedPay = infoPay != null ? mainContent.outerHtml() : "";
        OrderContent data = new OrderContent();
        CopyProperty.CopyIgnoreNull(findOrder, data);
        data.setContent(extractedContent);
        data.setInfoCustomer(extractedCustomer);
        data.setInfoSale(extractedSale);
        data.setInfoPrice(extractedPay);
        return ResponseEntity.ok(strOrder);
    }

    @GetMapping("/info-order")
    public MyResponse<?> getInfoOrder(@RequestParam("id") Long id, @RequestParam(required = false) String code){
        var findOrder = StringUtils.isNotEmpty(code)
                ? orderService.findByCode(code)
                : orderService.findById(id);
        CustomerOrder order = Optional.ofNullable(findOrder).orElseThrow(
                () -> new RuntimeException("Không tìm thấy Cơ Hội!")
        );
        for(CustomerOrderDetail detail : order.getDetails()){
            var items = detailItemRepository.findByDetailId(detail.getId());
            for (var item : items) {
                var imageList = mediaService.list(Math.toIntExact(item.getProductId()), "Product").stream()
                        .map(prt -> prt.getFileName()).collect(Collectors.toList());
                item.setImageLists(imageList);
            }
            detail.setItems(items);
        }
        var sale = userService.findById(order.getUserCreateId());
        QuoteOrder quoteOrder = new QuoteOrder();
        quoteOrder.setOrder(order);
        quoteOrder.setSale(sale);
        quoteOrder.setInfoBank(configRepository.findByKey("info_bank"));
        return MyResponse.response(quoteOrder);
    }
}
