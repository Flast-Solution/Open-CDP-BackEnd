package vn.flast.utils;
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

import java.math.BigDecimal;

/**************************************************************************/

public class NumberUtils {

    public static boolean isNull(Integer num) {
        return null == num;
    }

    public static boolean isNull(Double num) {
        return null == num;
    }

    public static boolean isNull(BigDecimal num) {
        return null == num;
    }

    public static boolean isNull(Long num) {
        return null == num;
    }

    public static boolean isNotNull(Integer num) {
        return !isNull(num);
    }

    public static boolean isNotNull(Long num) {
        return !isNull(num);
    }

    public static boolean gteZero(Double num) {
        return !isNull(num) && num > 0;
    }

    public static double calculatorPercent(double fee, double percent) {
        return (fee/100)*percent;
    }
}
