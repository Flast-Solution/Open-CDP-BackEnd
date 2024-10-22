package vn.flast.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Email(message="Email không hợp !!")
@Pattern(regexp=".+@.+\\..+", message="Email không hợp !")
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface EmailValidator {
    String message() default "Email không hợp";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
