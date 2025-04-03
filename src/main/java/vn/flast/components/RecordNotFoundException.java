package vn.flast.components;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public RecordNotFoundException(String exception) {
        super(exception);
    }
}