package vn.flast.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Locale;

import jakarta.validation.Configuration;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Payload;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


public class CustomConstraint {
	
	private static final Validator validator;

	static {
		Configuration<?> config = Validation.byDefaultProvider().configure();
		ValidatorFactory factory = config.buildValidatorFactory();
		validator = factory.getValidator();
		factory.close();
	}

	private static class TestBean {
		@Language
		private String language;

		@SuppressWarnings("unused")
		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}
	}

	public static void main(String[] args) {
		TestBean testBean = new TestBean();
		testBean.setLanguage("english");
		validator.validate(testBean).forEach(CustomConstraint::printError);
	}

	private static void printError(ConstraintViolation<TestBean> violation) {
		System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
	}

	@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = LanguageValidator.class)
	@Documented
	public static @interface Language {
		String message() default "must be a valid language display name." + " found: ${validatedValue}";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};
	}

	public static class LanguageValidator implements ConstraintValidator<Language, String> {

		@Override
		public void initialize(Language constraintAnnotation) {
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			if (value == null) {
				return false;
			}
			for (Locale locale : Locale.getAvailableLocales()) {
				if (locale.getDisplayLanguage().equalsIgnoreCase(value)) {
					return true;
				}
			}

			return false;
		}
	}
}
