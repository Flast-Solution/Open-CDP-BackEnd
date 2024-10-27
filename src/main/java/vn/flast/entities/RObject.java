package vn.flast.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@Data
public class RObject<T> {
    
    public int errorCode = 200;
    public String message;
    private Object data;
    
    public RObject(int code, String meg) {
    	errorCode = code;
    	message = meg;
    }

    public RObject(Object obj, String meg) {
        data = obj;
        message = meg;
    }
    
    public RObject(T dRes) {
    	data = dRes;
    }
    
    public static RObject<Object> response(Object dRes) {
    	return new RObject<>(dRes);
    }

    public static RObject<Object> response(int code, String meg) {
        return new RObject<>(code, meg);
    }

    public static RObject<Object> response(Object data, String meg) {
        return new RObject<>(data, meg);
    }
    
    public static RObject<Object> response(String str) {
    	return new RObject<>(str);
    }
    
    public void setData(Object dRes) {
    	data = dRes;
    }
    
    public String getMessage() {
    	return Optional.ofNullable(message).orElse(ErrorData.getMessage(errorCode));
    }
    
    public int getErrorCode() {
    	return Optional.of(errorCode).orElse(200);
    }

	public RObject(String string) {
		data = string;
	}

    public boolean isSuccess() {
        return this.errorCode == 200;
    }
}
