package vn.flast.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberConstraint {
    String message() default "Số điện thoại nhỏ hơn 20 ký tự và không có khoảng trắng";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
