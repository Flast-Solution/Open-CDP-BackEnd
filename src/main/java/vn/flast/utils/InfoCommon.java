package vn.flast.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.core.io.ClassPathResource;
import vn.flast.config.ConfigUtil;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.SaleProduct;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.models.ProductSkusDetails;
import vn.flast.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class InfoCommon {

    private static final String NOT_AVAILABLE = "N/A";
    private static final String BR_TAG = "<br/>";

    static String getValue(final String val) {
        return StringUtils.isBlank(val) ? NOT_AVAILABLE : val;
    }

    public static String headerEmail() {
        String template = "";
        try (InputStream inputStream = new ClassPathResource("templates/header_email.html").getInputStream()) {
            template = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Lỗi lấy header mail");
        }
        return template;
    }

    public static String footerEmail() {
        String template = "";
        try (InputStream inputStream = new ClassPathResource("templates/footer_email.html").getInputStream()) {
            template = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Lỗi lấy footer mail");
        }
        return template;
    }

    public static String renderCustomerInfoHtml(CustomerOrder order) {
        final String congTy = "$varCongTy";
        final String diaChi = "$varDiaChi";
        final String soDienThoai = "$varSoDienThoai";
        final String nguoiLienHe = "$varNguoiLienHe";
        final String email = "$varEmail";

        String html = """
                	<tr style="line-height:30px; padding: 10px; background: #cccccc52; font-size: 12px">
                		<td style="padding: 0 7px; font-size: 12px">Công ty:</td>
                		<td style="padding: 0 7px; font-size: 12px">$varCongTy</td>
                	</tr>
                	<tr style="line-height:30px;">
                		<td style="padding: 0 7px; font-size: 12px; line-height:60px">Địa chỉ:</td>
                		<td style="padding: 0 7px; font-size: 12px">$varDiaChi</td>
                	</tr>
                	<tr style="line-height:30px; padding: 10px; background: #cccccc52">
                		<td style="padding: 0 7px; font-size: 12px">Số điện	thoại:</td>
                		<td style="padding: 0 7px; font-size: 12px">$varSoDienThoai</td>
                	</tr>
                	<tr style="line-height:30px;">
                		<td style="padding: 0 7px; font-size: 12px">Người liên hệ:</td>
                		<td style="padding: 0 7px; font-size: 12px">$varNguoiLienHe</td>
                	</tr>
                	<tr style="line-height:30px; padding: 10px; background: #cccccc52">
                		<td style="padding: 0 7px; font-size: 12px">Email:</td>
                		<td style="padding: 0 7px; font-size: 12px">$varEmail</td>
                	</tr>
                """;

        return html.replace(congTy, getValue(order.getEnterpriseName()))
                .replace(diaChi, getValue(order.getCustomerAddress()))
                .replace(soDienThoai, getValue(formatPhone(order.getCustomerMobilePhone())))
                .replace(nguoiLienHe, getValue(order.getCustomerReceiverName()))
                .replace(email, getValue(order.getCustomerEmail()));
    }

    public static String renderSaleInfoHtml(User sale) {
        final String saleName = "$varSale";
        final String soDienThoai = "$varSoDienThoai";
        final String email = "$varEmail";

        String html = """
                	<tr style="line-height:30px;">
                		<td style="padding: 0 7px; font-size: 12px; line-height:60px">Đại diện:</td>
                		<td style="padding: 0 7px; font-size: 12px">$varSale</td>
                	</tr>
                	<tr style="line-height:30px; padding: 10px; background: #cccccc52">
                		<td style="padding: 0 7px; font-size: 12px">Số điện	thoại:</td>
                		<td style="padding: 0 7px; font-size: 12px">$varSoDienThoai</td>
                	</tr>
                	<tr style="line-height:30px;">
                		<td style="padding: 0 7px; font-size: 12px">Email:</td>
                		<td style="padding: 0 7px; font-size: 12px">$varEmail</td>
                	</tr>
                """;

        return html.replace(saleName, getValue(sale.getSsoId()))
                .replace(soDienThoai, getValue(formatPhone(sale.getPhone())))
                .replace(email, sale.getEmail());
    }

    public static String generateProductItem(MutableInt stt, CustomerOrderDetail orderDetail, SaleProduct product) {
        String note = orderDetail.getCustomerNote();
        String dayDuote = orderDetail.getDayDuote();
        int tt = Math.toIntExact(orderDetail.getPrice());

        String productName = product.getName();
        try (InputStream inputStream = new ClassPathResource("templates/item_product.html").getInputStream()) {
            String format = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            String noiDung = generateHtmlContent(groupProperties(JsonUtils.Json2ListObject(orderDetail.getSkuInfo(), ProductSkusDetails.class)));
            StringBuffer contentWithFile = new StringBuffer(noiDung);
            joinFileBanLe(contentWithFile, product);
            Map<String, String> varibleToMap = new HashMap<>();
            varibleToMap.put("${rowspan}", String.valueOf(getRowSpan(note, dayDuote)));
            varibleToMap.put("${soTT}", formatNumber(stt.intValue()));
            varibleToMap.put("${nameProduct}", productName);
            varibleToMap.put("${noiDung}", contentWithFile.toString());
            varibleToMap.put("${soLuong}", formatNumber(orderDetail.getQuantity()));
            varibleToMap.put("${donGia}", formatNumber(orderDetail.getPrice()));
            varibleToMap.put("${thanhTien}", formatNumber(tt));
            varibleToMap.put("${giamGia}", getPriceOffOfDetail(orderDetail));
            return variableMap(format, varibleToMap);
        } catch (IOException e) {
            return "";
        }
    }

    public static String renderOrderTotal(CustomerOrder order) {

        final String $feeSaleOther = "$feeSaleOther";
        int feeSaleOther = order.getFeeSaleOther();
        long totalPrice = OrderService.calTotal(order);
        String feeOther = """
                	<tr>
                		<td align="right" width="70%" style="padding: 4px 12px">Chi phí khác:</td>
                		<td width="30%" style="padding: 4px 12px">$feeSaleOther VND</td>
                	</tr>
                """;
        try (InputStream inputStream = new ClassPathResource("templates/item_total.html").getInputStream()) {
            String format = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            Map<String, String> varibleToMap = new HashMap<>();
            varibleToMap.put("$tienHang", formatNumber(order.getSubtotal() - order.getPriceOff()));
            varibleToMap.put("$phiVanChuyen", renderShipping(order));
            varibleToMap.put("$chietKhauDon", formatNumber(order.calCastBack()));
            varibleToMap.put("$vat", formatNumber(order.calVat()));
            varibleToMap.put("$feeOther", feeSaleOther == 0 ? "" : feeOther.replace($feeSaleOther, formatNumber(feeSaleOther)));
            varibleToMap.put("$thanhTien", formatNumber(totalPrice));
            return variableMap(format, varibleToMap);
        } catch (IOException e) {
            return "";
        }
    }

    private static String renderShipping(CustomerOrder order) {
        int shippingCost = Optional.ofNullable(order.getShippingCost()).orElse(0);
        return shippingCost == 0 ? "N/A" : formatNumber(shippingCost) + " VND";
    }


    private static String formatPhone(String input) {
        if (StringUtils.isBlank(input) || !NumberUtils.isDigits(input)) {
            return NOT_AVAILABLE;
        }
        return input.replaceFirst("(\\d{4})(\\d{3})(\\d+)", "($1) $2-$3");
    }

    public static String buildNote(String note) {
        if (StringUtils.isEmpty(note)) {
            return "";
        }
        return """
                <tr class="dt__note" style="border-bottom: 1px solid #cccccc8c;">
                	<td colspan="4" style="padding: 10px; text-align:left; white-space: pre-line;">
                		<strong>Ghi chú:</strong>
                	 	<span stype='padding-left: 10px'>${note}</span>
                	</td>
                </tr>""".replace("${note}", note);
    }

    public static String buildIntentTime(CustomerOrderDetail dt) {
        var dayDuote = dt.getDayDuote();
        if (StringUtils.isEmpty(dayDuote) || dt.donHang()) {
            return "";
        }
        String textDayDuote =  dt.getDayDuote().concat(" ngày") ;
        String text =  "Thời gian bàn giao dự kiến (tính từ khi chốt maket)";

        String temp = """
                	<tr class="dt__confirm__time" style="border-bottom: 1px solid #cccccc8c;">
                		<td colspan="4" style="padding: 10px; text-align:left;"><b>{{text}}: {{dayDuote}}</b></td>
                	</tr>
                """;

        var mapToVrib = new HashMap<String, String>();
        mapToVrib.put("{{dayDuote}}", textDayDuote);
        mapToVrib.put("{{text}}", text);
        return variableMap(temp, mapToVrib);
    }

    public static String variableMap(String str, Map<String, String> maps) {
        for (Map.Entry<String, String> e : maps.entrySet()) {
            str = str.replace(e.getKey(), e.getValue());
        }
        return str;
    }

    public static void joinFileBanLe(StringBuffer strBuilder, SaleProduct product) {
        List<String> fileView = product.getImageLists();
        if (fileView.isEmpty()) {
            return;
        }
        strBuilder.append(BR_TAG + "<strong>Hình ảnh sản phẩm: </strong> " + BR_TAG);
        strBuilder.append("<div id='img-preview'>");
        fileView.forEach(img -> strBuilder
                .append("<a target='_blank' href='").append(ConfigUtil.HOST_URL).append(img).append("'>")
                .append("<img style='max-width:100px; max-height:100px; margin: 20px 0px 0px 10px' src='").append(ConfigUtil.HOST_URL).append(img).append("' />")
                .append("</a>")
        );
        strBuilder.append("</div>");
    }

    public static String formatNumber(Integer num) {
        return num == null ? NOT_AVAILABLE : getGroupingNumberFormat().format(num);
    }

    public static String formatNumber(Long num) {
        return num == null ? NOT_AVAILABLE : getGroupingNumberFormat().format(num);
    }

    public static String formatNumber(Double num) {
        return num == null ? NOT_AVAILABLE : getGroupingNumberFormat().format(num);
    }

    private static DecimalFormat getGroupingNumberFormat() {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter;
    }

    public static int getRowSpan(String note, String dayDuote) {
        int $rowspan = 2;
        if (StringUtils.isNotEmpty(note) && StringUtils.isNotEmpty(dayDuote)) {
            $rowspan = 4;
        } else if (StringUtils.isNotEmpty(note) || StringUtils.isNotEmpty(dayDuote)) {
            $rowspan = 3;
        }
        return $rowspan;
    }

    public static String getPriceOffOfDetail(CustomerOrderDetail orderDetail) {
        if (orderDetail.getPriceOff() == 0) {
            return "";
        }
        String temp = """
                   	<tr>
                        <td style="text-align: left;"></td>
                        <td style="text-align:center">Giảm giá:</td>
                        <td style="text-align:center">${priceOff}</td>
                    </tr>
                    <tr>
                        <td style="text-align: left;"></td>
                        <td style="text-align:center"><strong>Tổng tiền:</strong></td>
                        <td style="text-align:center"><strong>${total}</strong></td>
                    </tr>
                """;
        Map<String, String> variableToMap = new HashMap<>();
        variableToMap.put("${priceOff}", formatNumber(orderDetail.getPriceOff()));
        variableToMap.put("${total}", formatNumber(orderDetail.getTotal()));
        return variableMap(temp, variableToMap);
    }

    public static Map<String, List<ProductSkusDetails>> groupProperties(List<ProductSkusDetails> properties) {
        return properties.stream()
                .filter(prop -> prop.getDel() == 0) // Lọc các bản ghi không bị xóa (del = 0)
                .collect(Collectors.groupingBy(
                        ProductSkusDetails::getName, // Nhóm theo name
                        Collectors.toList()
                ));
    }

    // Tạo chuỗi HTML từ Map đã nhóm
    public static String generateHtmlContent(Map<String, List<ProductSkusDetails>> groupedProperties) {
        StringBuilder html = new StringBuilder();

        for (Map.Entry<String, List<ProductSkusDetails>> entry : groupedProperties.entrySet()) {
            // Thêm thẻ div cho name (in đậm)
            html.append("<div style='padding-top: 10px; padding-bottom: 10px;'>")
                    .append("<b>").append(entry.getKey()).append(":</b>")
                    .append("</div>");

            // Thêm các value với dấu - phía trước
            html.append("<div style='padding-left: 20px;'>");
            for (ProductSkusDetails prop : entry.getValue()) {
                html.append("<span>- ")
                        .append(prop.getValue())
                        .append("</span><br>");
            }
            html.append("</div>");
        }

        return html.toString();
    }

    public static String infoBankOfVat(CustomerOrder order) {
        String template = """
                	<p>- Ngân hàng TMCP Tiên Phong (TPbank) chi nhánh Phạm Hùng - Hà Nội</p>
                	<p>- Số tài khoản: <strong class="stk">0379 491 3333</strong> - Chủ khoản: <strong class="owner_banking">Chu Thị Mỹ Hạnh</strong></p>
                """;
        String contentCk = "<p>- Nội dung CK: <strong class='nd_ck'>" + order.getCode() + "</strong></p>";
        if (order.getTotal() <= 5000000) {
            return template.concat(contentCk);
        }
        String content = order.coLayVat() ? """
                	<p>- Ngân hàng Lộc Phát Việt Nam LPBank- Chi nhánh Hoàng Mai - Hà Nội</p>
                	<p>- Số tài khoản: <strong class="stk">071968003333</strong> - <span class="owner_banking">CONG TY CO PHAN PRINTGO VIET NAM </span></p>
                """ : template;
        return content.concat(contentCk);
    }

    public static String noteOfVatAndOrder(CustomerOrder order) {
        if (!order.coLayVat() && StringUtils.isEmpty(order.getCustomerNote())) {
            return "";
        }
        StringBuilder noteBuilder = new StringBuilder("<p><b>Ghi chú đơn hàng:</b></p>");
        if (StringUtils.isNotEmpty(order.getCustomerNote())) {
            noteBuilder.append("<p>- ").append(order.getCustomerNote()).append("</p>");
        }
        noteBuilder.append(order.coLayVat() ? "<p>- Báo giá đã bao gồm thuế giá trị gia tăng ( VAT)</p>" : "<p>- Báo giá chưa bao gồm thuế giá trị gia tăng ( VAT)</p>");
        return noteBuilder.toString();
    }
}
