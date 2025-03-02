package vn.flast.controller.order;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.components.InvoiceBuilder;
import vn.flast.components.QuoteBuilder;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.OrderContent;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerPersonal;
import vn.flast.utils.CopyProperty;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/qoute")
@RequiredArgsConstructor
public class QuoteController {

    private final OrderService orderService;

    private final InvoiceBuilder invoiceBuilder;

    private final QuoteBuilder quoteBuilder;

    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    public ResponseEntity<?> invoice(@RequestParam("id") Long id, @RequestParam(required = false) String code) {
        var findOrder = StringUtils.isNotEmpty(code)
                ? orderService.findByCode(code)
                : orderService.findById(id);
        CustomerOrder order = Optional.ofNullable(findOrder).orElseThrow(
                () -> new RuntimeException("Không tìm thấy Cơ Hội!")
        );
        CustomerPersonal customer = findOrder.getCustomer();
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
        return ResponseEntity.ok(data);
    }
}
