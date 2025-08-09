package vn.flast.domains.stock;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StockUtils {
    public static String hashSku(String sku) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = md.digest(sku.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
