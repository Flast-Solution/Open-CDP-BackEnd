package vn.flast.components;
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

import java.text.NumberFormat;

public class ReadNumber {

    public static String formatNumberForRead(double number) {
        NumberFormat nf = NumberFormat.getInstance();
        String temp = nf.format(number);
        StringBuilder strReturn = new StringBuilder();
        int slen = temp.length();
        for (int i = 0; i < slen; i++) {
            if (String.valueOf(temp.charAt(i)).equals("."))
                break;
            else if (Character.isDigit(temp.charAt(i))) {
                strReturn.append(temp.charAt(i));
            }
        }
        return strReturn.toString();
    }

    public static String numberToString(double number) {
        String sNumber = formatNumberForRead(number);
        // Tao mot bien tra ve
        String sReturn = "";
        // Tim chieu dai cua chuoi
        int iLen = sNumber.length();
        // Lat nguoc chuoi nay lai
        StringBuilder sNumber1 = new StringBuilder();
        for (int i = iLen - 1; i >= 0; i--) {
            sNumber1.append(sNumber.charAt(i));
        }
        // Tao mot vong lap de doc so
        // Tao mot bien nho vi tri cua sNumber
        int iRe = 0;
        do {
            // Tao mot bien cat tam
            String sCut;
            if (iLen > 3) {
                sCut = sNumber1.substring((iRe * 3), (iRe * 3) + 3);
                sReturn = Read(sCut, iRe) + sReturn;
                iRe++;
                iLen -= 3;
            } else {
                sCut = sNumber1.substring((iRe * 3), (iRe * 3) + iLen);
                sReturn = Read(sCut, iRe) + sReturn;
                break;
            }
        } while (true);
        if (sReturn.length() > 1) {
            sReturn = sReturn.substring(0, 1).toUpperCase() + sReturn.substring(1);
        }
        sReturn = sReturn + "đồng";
        return sReturn;
    }

    // Khoi tao ham Read
    public static String Read(String sNumber, int iPo) {
        // Tao mot bien tra ve
        StringBuilder sReturn = new StringBuilder();
        // Tao mot bien so
        String[] sPo = {"", "ngàn" + " ", "triệu" + " ", "tỷ" + " "};
        String[] sSo = {"không" + " ", "một" + " ", "hai" + " ", "ba" + " ", "bốn" + " ", "năm" + " ", "sáu" + " ",
                "bảy" + " ", "tám" + " ", "chín" + " "};
        String[] sDonvi = {"", "mươi" + " ", "trăm" + " "};
        // Tim chieu dai cua chuoi
        int iLen = sNumber.length();
        // Tao mot bien nho vi tri doc
        int iRe = 0;
        // Tao mot vong lap de doc so
        for (int i = 0; i < iLen; i++) {
            String sTemp = "" + sNumber.charAt(i);
            int iTemp = Integer.parseInt(sTemp);
            // Tao mot bien ket qua
            String sRead = "";
            // Kiem tra xem so nhan vao co = 0 hay ko
            if (iTemp == 0) {
                switch (iRe) {
                    case 0:
                        break;// Khong lam gi ca
                    case 1: {
                        if (Integer.parseInt("" + sNumber.charAt(0)) != 0) {
                            sRead = "lẻ" + " ";
                        }
                        break;
                    }
                    case 2: {
                        if (Integer.parseInt("" + sNumber.charAt(0)) != 0
                                && Integer.parseInt("" + sNumber.charAt(1)) != 0) {
                            sRead = "không trăm" + " ";
                        }
                        break;
                    }
                }
            } else if (iTemp == 1) {
                sRead = iRe == 1 ? "mười" + " " : "một" + " " + sDonvi[iRe];
            } else if (iTemp == 5) {
                if (iRe == 0) {
                    if (sNumber.length() <= 1) {
                        sRead = "năm" + " ";
                    } else if (Integer.parseInt("" + sNumber.charAt(1)) != 0) {
                        sRead = "lăm" + " ";
                    } else
                        sRead = "năm" + " ";
                } else {
                    sRead = sSo[iTemp] + sDonvi[iRe];
                }
            } else {
                sRead = sSo[iTemp] + sDonvi[iRe];
            }
            sReturn.insert(0, sRead);
            iRe++;
        }
        if (!sReturn.isEmpty()) {
            sReturn.append(sPo[iPo]);
        }
        return sReturn.toString();
    }

    public static String repeat(String s, int n) {
        if (s == null) {
            return null;
        }
        return s.repeat(Math.max(0, n));
    }
}
