package vn.flast.components;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.SaleProduct;
import vn.flast.exception.NullArgumentException;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.models.User;
import vn.flast.service.ProductService;
import vn.flast.service.user.UserService;
import vn.flast.utils.BeanUtil;
import vn.flast.utils.DateUtils;
import vn.flast.utils.InfoCommon;
import vn.flast.utils.UtilConfigs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class QuoteBuilder {

    private static final Logger log = LoggerFactory.getLogger(InvoiceBuilder.class);
    private static final String TEMPLATE_URL = UtilConfigs.MAIL_TEMPLATE_INVOICE;
    private static final String NOT_AVAILABLE = "N/A";

    private final UserService userService;

    private User sale;

    private CustomerOrder order;

    private Boolean mail;

    public QuoteBuilder create(CustomerOrder order, Boolean mail) {
        NullArgumentException.check("order", order);
        this.sale = Optional.ofNullable(userService.findById(order.getUserCreateId())).orElseThrow(
                () -> new RuntimeException("Không tìm thấy Sale!")
        );
        this.order = order;
        this.mail = mail;
        return this;
    }

    public String calDeposit() {
        if(order.isOrder()) {
            return String.valueOf(order.getTotal() - order.getPaid());
        }
        long total = OrderService.calTotal(order);

        long pay;
        if(total <= 10000000) {
            pay = total;
        } else {
            pay = calFeeDeposit();
        }
        return String.valueOf(pay);
    }

    private long calFeeDeposit () {
        long deposit = 0;
        for(CustomerOrderDetail orderDetail : order.getDetails()) {
                deposit += orderDetail.getTotal();
            }
        return deposit;
    }

    public static long percentOfFee(long fee, int percent) {
        return (fee/100)*percent;
    }

    private String takeNotePaid() {
        if(order.isOrder()) {
            return "Số tiền còn lại: " + formatNumber((int) (order.getTotal() - order.getPaid())) + " đ";
        }
        long total = OrderService.calTotal(order);
        String notePay = "Quý khách cần chuyển khoản số tiền: " + formatNumber(total) + " VND để xác nhận đơn hàng";
        return notePay;
    }

    private static String formatNumber(Integer num) {
        return InfoCommon.formatNumber(num);
    }

    private static String formatNumber(Long num) {
        return InfoCommon.formatNumber(num);
    }

    public String render() {
        // template var
        final String $varSaleInfo = "$varSaleInfo";
        final String $varCustomerInfo = "$varCustomerInfo";
        final String $varOrderInfo = "$varOrderInfo";
        final String $varTotalOrder = "$varTotalOrder";
        final String $varOrderCode = "$varOrderCode";
        final String $createdAt = "$createdAt";
        final String $expireAt = "$expireAt";
        final String $emailTitle = "$emailTitle";
        final String $infoBankOfVat = "$infoBankOfVat";
        final String $noteOfVat = "$noteOfVat";

        String template = "";
        try (InputStream inputStream = new ClassPathResource(TEMPLATE_URL).getInputStream()){
            template = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date createdDate = order.getCreatedAt();
        Date expireDate = DateUtils.addDays(createdDate, 10);

        String customerInfo = renderCustomerInfoHtml();
        String saleInfo = renderSaleInfoHtml();
        String orderDetails = renderOrderDetailsHtml();
        String totalOrder = renderOrderTotal();
        String emailTitle =  getEmailTitle();
        String notePay = takeNotePaid();
        String headerEmail =  InfoCommon.headerEmail();
        String footerEmail = InfoCommon.footerEmail();
        if(mail){
            return template
                    .replace("${header}", headerEmail)
                    .replace($emailTitle, emailTitle)
                    .replace($varOrderCode, getValue(order.getOrderName()))
                    .replace($createdAt, dateFormat.format(createdDate))
                    .replace($expireAt, dateFormat.format(expireDate))
                    .replace($varSaleInfo, saleInfo)
                    .replace($varCustomerInfo, customerInfo)
                    .replace($varOrderInfo, orderDetails)
                    .replace($varTotalOrder, totalOrder)
                    .replace("$notePay", notePay)
                    .replace("$deposit", calDeposit())
                    .replace($infoBankOfVat, InfoCommon.infoBankOfVat(order))
                    .replace($noteOfVat, InfoCommon.noteOfVatAndOrder(order))
                    .replace("${footer}", footerEmail);
        }
        return template
                .replace("${footer}", "")
                .replace("${header}", "")
                .replace($emailTitle, emailTitle)
                .replace($varOrderCode, getValue(order.getOrderName()))
                .replace($createdAt, dateFormat.format(createdDate))
                .replace($expireAt, dateFormat.format(expireDate))
                .replace($varSaleInfo, saleInfo)
                .replace($varCustomerInfo, customerInfo)
                .replace($varOrderInfo, orderDetails)
                .replace($varTotalOrder, totalOrder)
                .replace("$notePay", notePay)
                .replace("$deposit", calDeposit())
                .replace($infoBankOfVat, InfoCommon.infoBankOfVat(order))
                .replace($noteOfVat, InfoCommon.noteOfVatAndOrder(order));

    }
    String getValue(final String val){
        return StringUtils.isBlank(val) ? NOT_AVAILABLE : val;
    }

    private String renderCustomerInfoHtml() {
        return InfoCommon.renderCustomerInfoHtml(order);
    }

    private String renderSaleInfoHtml() {
        return InfoCommon.renderSaleInfoHtml(sale);
    }

    public String renderOrderTotal() {
        return InfoCommon.renderOrderTotal(order);
    }

    private String getEmailTitle() {
        return checkIsOrder() ? "XÁC NHẬN ĐƠN HÀNG" : "BÁO GIÁ ĐƠN HÀNG";
    }

    private boolean checkIsOrder() {
        return this.order.isOrder();
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
