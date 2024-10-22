package vn.flast.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {
	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		return !contactField.contains(" ") && contactField.length() <= 20;
	}
}
