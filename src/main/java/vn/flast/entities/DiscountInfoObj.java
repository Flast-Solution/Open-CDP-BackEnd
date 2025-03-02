package vn.flast.entities;

public class DiscountInfoObj {
    public String code;
    public DiscountType unit;
    public int value;
    public enum DiscountType {
        percent, money
    }
    public double getMonney(Double total) {
        if(total == null || total == 0) {
            return 0L;
        }
        return unit.equals(DiscountType.money) ? value : value * total / 100;
    }
}
