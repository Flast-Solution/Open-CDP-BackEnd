package vn.flast.domains.order;

import org.apache.commons.lang3.StringUtils;
import vn.flast.entities.order.OrderDetail;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.utils.Common;
import vn.flast.utils.JsonUtils;
import vn.flast.utils.NumberUtils;

import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

public class OrderUtils {

    public static Integer PAYMENT_IS_CONFIRM = 1;
    public static Integer PAYMENT_IS_NOT_CONFIRM = 0;
    public static Integer PAYMENT_STATUS_DONE = 1;

    public static String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public static String createOrderCode() {
        String lastThereDigits = getAlphaNumericString(3);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String subYear = year.substring(year.length() - 2);
        String end = day + month + subYear + lastThereDigits;
        return "O" + getAlphaNumericString(1) + getAlphaNumericString(2) + end;
    }

    public static void calDetailPrice(CustomerOrderDetail detail, OrderDetail input) {
        double subTotal;
        subTotal = detail.getPrice() * detail.getQuantity();
        double priceOff = input.getDiscountAmount();
        double feeAfterPromotion = subTotal - priceOff;
        detail.setPriceOff(priceOff);
        detail.setTotal(feeAfterPromotion);
    }

    public static void calculatorPrice(CustomerOrder order) {
        if (Objects.isNull(order) || Common.CollectionIsEmpty(order.getDetails())) {
            return;
        }
        double subTotal = order.getDetails()
            .stream()
            .mapToDouble(CustomerOrderDetail::getTotal).sum();
        double priceOff = 0;
        if (StringUtils.isNotEmpty(order.getDiscountInfo())) {
            OrderDiscount orderDiscount = JsonUtils.Json2Object(order.getDiscountInfo(), OrderDiscount.class);
            priceOff = orderDiscount != null ? orderDiscount.getPriceOff(subTotal) : 0;
        }
        double feeAfterPromotion = subTotal - priceOff;
        double vat = NumberUtils.calculatorPercent(feeAfterPromotion, order.getVat());
        order.setSubtotal(subTotal);
        order.setPriceOff(priceOff);
        order.setTotal(subTotal - priceOff + vat);
    }
}
