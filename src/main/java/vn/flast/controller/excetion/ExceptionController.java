package vn.flast.controller.excetion;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import vn.flast.components.JwtGenericException;
import vn.flast.components.RecordNotFoundException;
import vn.flast.entities.MyResponse;

import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Set;

@ControllerAdvice
public class ExceptionController {

    Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @Value("${dev.mode}")
    private String mode;

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        MyResponse<?> mResponse = MyResponse.response(403, e.getMessage());
        logger.error("handleAccessDeniedException: {}", e.getMessage());
        return new ResponseEntity<>(mResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = { JwtGenericException.class })
    public ResponseEntity<Object> handleJwtGenericException(JwtGenericException e) {
        MyResponse<?> mResponse = MyResponse.response(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), e.getMessage());
        return new ResponseEntity<>(mResponse, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<Object> handleMethodArgumentNotValid(ConstraintViolationException ex) {
        MyResponse<Object> mResponse = MyResponse.response(1, "Lỗi dữ liệu đầu vào");
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        HashMap<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : constraintViolations) {
            String fieldName = violation.getPropertyPath().toString();
            errors.put(fieldName, violation.getMessage());
        }
        mResponse.setData(errors);
        return new ResponseEntity<>(mResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        MyResponse<Object> mResponse = MyResponse.response(1, "Lỗi dữ liệu đầu vào");
        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        mResponse.setData(errors);
        return new ResponseEntity<>(mResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class, NoSuchFileException.class })
    public ResponseEntity<Object> unhandledPath(final NoHandlerFoundException e) {
        MyResponse<Object> mResponse = MyResponse.response(404, e.getMessage());
        return new ResponseEntity<>(mResponse, HttpStatus.OK);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handledNotFoundRecord(RecordNotFoundException e) {
        MyResponse<Object> mResponse = MyResponse.response(503, e.getMessage());
        return new ResponseEntity<>(mResponse, HttpStatus.OK);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleErrorHandler(Exception e) {
        MyResponse<Object> mResponse = MyResponse.response(1, e.getMessage());
        logger.error("---- handleErrorHandler: {}-----", mode.equals("prod") ? e.getMessage() : e);
        return new ResponseEntity<>(mResponse, HttpStatus.OK);
    }
}
