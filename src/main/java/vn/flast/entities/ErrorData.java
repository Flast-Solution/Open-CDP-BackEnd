
package vn.flast.entities;

import java.util.HashMap;
import java.util.Map;

public class ErrorData {
    
    public static int INVALID_TOKEN = 1;
    public static int INVALID_TIME_EXPRICE = 2;
    public static int PERMISSION_DENY = 20;
    public static int UPLOAD_FILE_FAIL = 21;
    public static int SUCCESS = 200;
    public static int NOT_FOUND = 404;
    
    public static String getMessage(int errorCode) {
        Map<Integer, String> getErrors = getError();
        return getErrors.get(errorCode);
    }
    
    public static Map<Integer, String> getError() {
        
        Map<Integer, String> eCode = new HashMap<>();
        eCode.put(INVALID_TOKEN, "Token invalid in request");
        eCode.put(INVALID_TIME_EXPRICE, "Time for token is exprice");
        eCode.put(SUCCESS, "Success");
        eCode.put(UPLOAD_FILE_FAIL, "Lỗi tải file lên");
        
        return eCode;
    }
}
