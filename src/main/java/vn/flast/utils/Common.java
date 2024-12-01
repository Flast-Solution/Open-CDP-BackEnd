package vn.flast.utils;

import vn.flast.security.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static String escape(String s){
        return s.replace("\\", "\\\\")
            .replace("\t", "\\t")
            .replace("\b", "\\b")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\f", "\\f")
            .replace("\"", "\\\"");
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
}
