package vn.flast.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class MyResponse<T> {

    private Integer errorCode = 200;
    private String message;
    private Boolean success;
    private T data;

    MyResponse(T obj, String message) {
        this.data = obj;
        this.message = message;
    }

    MyResponse(Integer errorCode, String message, T obj) {
        this.data = obj;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static MyResponse<Object> response(Object dRes) {
        return new MyResponse<>(dRes, "success");
    }

    public static MyResponse<Object> response(Integer errorCode, String message, Object dRes) {
        var response = new MyResponse<>(dRes, message);
        response.setErrorCode(errorCode);
        return response;
    }

    public static MyResponse<Object> response(Object dRes, String message) {
        return new MyResponse<>(dRes, message);
    }

    public static MyResponse<Object> message(String message) {
        return new MyResponse<>(200, message);
    }

    public static MyResponse<Object> response(Integer errorCode, String message) {
        return new MyResponse<>(errorCode, message, null);
    }

    public boolean getSuccess() {
        return errorCode != null && errorCode == 200;
    }
}
