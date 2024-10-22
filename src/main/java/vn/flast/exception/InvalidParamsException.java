package vn.flast.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidParamsException extends RuntimeException {
	public InvalidParamsException(String exception) {
        super(exception);
    }
}