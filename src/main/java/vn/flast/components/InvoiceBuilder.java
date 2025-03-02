package vn.flast.components;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import vn.flast.entities.SaleProduct;
import vn.flast.exception.NullArgumentException;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.models.Product;
import vn.flast.models.User;
import vn.flast.repositories.ProductRepository;
import vn.flast.service.ProductService;
import vn.flast.service.user.UserService;
import vn.flast.utils.BeanUtil;
import vn.flast.utils.InfoCommon;
import vn.flast.utils.UtilConfigs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class InvoiceBuilder {


    private static final Logger log = LoggerFactory.getLogger(InvoiceBuilder.class);
    private static final String TEMPLATE_URL = UtilConfigs.TEMPLATE_HOA_DON_ORDER;
    private static final String NOT_AVAILABLE = "N/A";

    private final UserService userService;

    private User sale;

    private CustomerOrder order;

    public InvoiceBuilder create(CustomerOrder order) {
        NullArgumentException.check("order", order);
        this.sale = Optional.ofNullable(userService.findById(order.getUserCreateId()))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Sale!"));
        this.order = order;
        return this;
    }

    public String render() {
        // template var
        final String $varHeader = "$varHeader";
        final String $varSaleInfo = "$varSaleInfo";
        final String $varCustomerInfo = "$varCustomerInfo";
        final String $varOrderInfo = "$varOrderInfo";
        final String $varTotalOrder = "$varTotalOrder";
        final String $textMoney = "$textMoney";

        String template = "";
        try (InputStream inputStream = new ClassPathResource(TEMPLATE_URL).getInputStream()){
            template = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        String customerInfo = renderCustomerInfoHtml();
        String saleInfo = renderSaleInfoHtml();
        String orderDetails = renderOrderDetailsHtml();
        Double total = order.getTotal();
        String totalOrder = renderOrderTotal();

        return template
                .replace($varHeader, InfoCommon.headerEmail())
                .replace($varSaleInfo, saleInfo)
                .replace($varCustomerInfo, customerInfo)
                .replace($varOrderInfo, orderDetails)
                .replace($varTotalOrder, totalOrder)
                .replace($textMoney, ReadNumber.numberToString(Double.valueOf(total)));
    }

    private String renderCustomerInfoHtml() {
        return InfoCommon.renderCustomerInfoHtml(order);
    }

    private String renderSaleInfoHtml() {
        return InfoCommon.renderSaleInfoHtml(sale);
    }

    private String renderOrderTotal() {
        return InfoCommon.renderOrderTotal(order);
    }

    private String renderOrderDetailsHtml() {
        var details = order.getDetails();
        MutableInt stt = new MutableInt();
        Function<String, String> buildNote = InfoCommon::buildNote;
        Function<CustomerOrderDetail, String> buildIntentTime = InfoCommon::buildIntentTime;
        List<String> items = new ArrayList<>();
        for (CustomerOrderDetail dt : details) {
            String content = generateTableItem(dt, stt);
            if(StringUtils.isEmpty(content)){
                continue;
            }
            String newContent = content.concat(buildIntentTime.apply(dt)).concat(buildNote.apply(dt.getCustomerNote()));
            items.add(newContent);
        }
        return String.join("\n", items);
    }

    private String generateTableItem(CustomerOrderDetail orderDetail, MutableInt stt) {
        stt.increment();
        ProductService productRepo = BeanUtil.getBean(ProductService.class);
        SaleProduct product = productRepo.findById(orderDetail.getProductId());
        String data = InfoCommon.generateProductItem(stt, orderDetail, product);
        return  data;
    }



}
