package vn.flast.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class ValidationErrorBuilder {
	
	public static ValidationError fromBindingErrors(Errors errors) {
        ValidationError error = new ValidationError("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
	
	public static ValidationError fromBindingBusiness(Exception ex) {
        ValidationError error = new ValidationError(ex.getMessage());
        error.addValidationError(ex.getMessage());
        return error;
    }
}
