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
/**************************************************************************/

import vn.flast.security.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.text.Normalizer;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Pattern;

public class Common {

    public static String deAccent(String str) {
        String nStr = str
            .replaceAll("Đ", "D")
            .replaceAll("đ", "")
            .replaceAll(" ", "-");
        String nfdNormalizedString = Normalizer.normalize(nStr, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).toString();
    }

    public static String escape(String s) {
        if (s == null) return null;
        return s.replace("\\", "\\\\")
            .replace("\t", "\\t")
            .replace("\b", "\\b")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\f", "\\f")
            .replace("\"", "\\\"")
            .replace("_", "\\_")
            .replace(";", "\\;")
            .replace("--", "\\--")
            .replace("#", "\\#")
            .replace("/*", "/\\*")
            .replace("*/", "*\\/");
    }

    private static UserPrinciple getInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrinciple userPrinciple) {
            return userPrinciple;
        }
        return null;
    }

    public static String getSsoId () {
        return Optional.ofNullable(getInfo()).map(UserPrinciple::getSsoId).orElse("");
    }

    public static Integer getUserId () {
        return Optional.ofNullable(getInfo()).map(UserPrinciple::getId).orElse(0);
    }

    public static boolean CollectionIsEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static String getAlphaNumericString(int n, boolean isNumberOnly) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (isNumberOnly) {
            AlphaNumericString = "0123456789";
        }
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public static String makeFolder(String fd) {
        String path = fd;
        if(fd.startsWith("/uploads/")) {
            path = System.getProperty("user.dir") + fd;
        }
        File f = new File(path);
        if(f.exists()) {
            return fd;
        }
        if(!f.mkdirs()){
            System.out.println("[AopCommon.makeFolder] Create folder fail .!");
        }
        return fd;
    }
}
