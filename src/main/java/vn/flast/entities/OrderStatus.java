package vn.flast.entities;

public class OrderStatus {

    private OrderStatus() {}
    public static final int REQUEST_UPDATE_FROM_SAN_XUAT = 1;
    public static final int REQUEST_UPDATE_EMPTY = 0;

    public static final int DETAIL_SAN_XUAT_DELAY_MASK = 1;

    public static final int BAO_GIA = 0;
    public static final int CAN_PHE_DUYET = 16;


    public static final int PERCHARGING_DUYET_SAN_XUAT = 13;
    public static final int SUPPLIER_SAN_XUAT_VA_GIA_CONG = 4;
    public static final int SUPPLIER_HOAN_THANH = 18;
    public static final int SUPPLIER_SAN_XUAT_LAI = 19;

    public static final int WARE_HOUSE_DANG_CHUYEN_VE_KHO = 5;
    public static final int WARE_HOUSE_TRONG_KHO = 6;
    public static final int WARE_HOUSE_DA_GIAO = 7;
    public static final int WARE_HOUSE_DANG_GIAO = 27;
    public static final int WARE_DON_HANG_LOI= 14;
    public static final int WARE_DON_HANG_THIEU= 20;

    public static final int SHIPPING_DANG_GIAO = 21;
    public static final int SHIPPING_DA_GIAO = 22;
    public static final int SHIPPING_GIAO_LOI = 23;
    public static final int SHIPPING_GIAO_TRE = 24;
    public static final int SHIPPING_GIAO_NHIEU_DOT = 25;

    public static final int ACOUNTANT_HOAN_THANH = 8;
    public static final int ACOUNTANT_HOAN_TIEN = 15;
    public static final int ACOUNTANT_THIEU_TIEN = 26;
    public static final int ACOUNTANT_DA_THANH_TOAN = 9;
    public static final int ACOUNTANT_DON_HANG_LOI = 10;
    public static final int ACOUNTANT_HUY_DON = 11;
    public static final int ACOUNTANT_KHONG_THANH_TOAN = 12;
}
