package vn.flast.controller.excetion;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import vn.flast.components.JwtGenericException;
import vn.flast.components.RecordNotFoundException;
import vn.flast.components.WebException;
import vn.flast.entities.RList;
import vn.flast.entities.RObject;

import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@ControllerAdvice
public class ExceptionController {

    Logger logger = LoggerFactory.getLogger(ExceptionController.class);
    @Value("${dev.mode}")
    private String mode;

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        RList<Object> _rList = new RList<>(403, e.getMessage());
        logger.error("handleAccessDeniedException: {}", e.getMessage());
        return new ResponseEntity<>(_rList, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = { JwtGenericException.class })
    public ResponseEntity<Object> handleJwtGenericException(JwtGenericException e) {
        RObject ret = RObject.response(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), e.getMessage());
        return new ResponseEntity<>(ret, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    @ExceptionHandler(value = { WebException.class })
    public ResponseEntity<?> handleWebException(Exception e) {
        logger.error("======= Web Exception : {} ====== ", e.getMessage());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
        RObject ret = RObject.response(HttpStatus.BAD_GATEWAY.value(), e.getMessage());
        return new ResponseEntity<>(ret, responseHeaders, HttpStatus.BAD_GATEWAY);
    }

    // error handle for @Valid
    // Bắt lỗi kiểm tra dữ liệu đầu vào của các model.
    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<Object> handleMethodArgumentNotValid(ConstraintViolationException ex) {
        RObject<Object> _rObj = new RObject<>(1, "Lỗi dữ liệu đầu vào");
        Set<? extends ConstraintViolation<?>> _constraintViolations =  ex.getConstraintViolations();
        HashMap<String, String> errors = new HashMap<>();
        for (Iterator<ConstraintViolation<?>> iterator = (Iterator<ConstraintViolation<?>>) _constraintViolations.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> next =  iterator.next();
            Path pIm = next.getPropertyPath();
            errors.put(pIm.getClass().getName(), next.getMessage());
        }
        _rObj.setData(errors);
        return new ResponseEntity<>(_rObj, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class, NoSuchFileException.class })
    public ResponseEntity<Object> unhandledPath(final NoHandlerFoundException e) {
        RList<Object> _rList = new RList<>(404, e.getMessage());
        return new ResponseEntity<>(_rList, HttpStatus.OK);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handledNotFoundRecord(RecordNotFoundException e) {
        RList<Object> _rList = new RList<>(503, e.getMessage());
        return new ResponseEntity<>(_rList, HttpStatus.OK);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleErrorHandler(Exception e) {
        RList<Object> _rList = new RList<>(1, e.getMessage());
        logger.error("---- handleErrorHandler: {}-----", mode.equals("prod") ? e.getMessage() : e);
        return new ResponseEntity<>(_rList, HttpStatus.OK);
    }
}
