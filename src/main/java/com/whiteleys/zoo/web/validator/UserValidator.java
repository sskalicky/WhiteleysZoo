package com.whiteleys.zoo.web.validator;

import com.whiteleys.zoo.domain.Sex;
import com.whiteleys.zoo.domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator {
	private static final String UK_POSTCODE_PATTERN = "^([A-PR-UWYZ](([0-9](([0-9]|[A-HJKSTUW])?)?)|([A-HK-Y][0-9]([0-9]|[ABEHMNPRVWXY])?)) [0-9][ABD-HJLNP-UW-Z]{2})";

	private User user;
	private Errors errors;

	public boolean supports(Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		this.user = (User)target;
		this.errors = errors;

		validateMandatoryFields();
		validatePasswordConfirmation();
		validateUKPostcodeFormat();
		validateSexValuesAllowed();
		validateBirthDateRange();
	}

	private void validateMandatoryFields(){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.username.mandatory", "Required field!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password.mandatory", "Required field!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sex", "user.sex.mandatory", "Required field!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dobDay", "user.dobDay.mandatory", "Required field!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dobMonth", "user.dobMonth.mandatory", "Required field!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dobYear", "user.dobYear.mandatory", "Required field!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postcode", "user.postcode.mandatory", "Required field!");
	}

	private void validatePasswordConfirmation(){
		if(!user.getPassword().equals(user.getPassword2())){
			errors.rejectValue("password2","user.password.confirmation.distinct", "Password retyped incorrectly!");
		}
	}

	private void validateUKPostcodeFormat() {
		Pattern pattern = Pattern.compile(UK_POSTCODE_PATTERN);
		Matcher matcher = pattern.matcher(user.getPostcode());
		if(!matcher.matches()){
			errors.rejectValue("postcode","user.postcode.ukformat.wrong", "Postcode format doesn't correspond with UK Post code format!");
		}
	}

	private void validateSexValuesAllowed() {
		if(user.getSex() != null){
			switch (user.getSex()){
				case F:
				case M:
					break;
				default:
					errors.rejectValue("sex","user.sex.value.unknown", "Unknown value for field Sex!");
			}
		}
	}

	private void validateBirthDateRange() {
		Calendar cal = Calendar.getInstance();

		if(user.getDobDay() != null
				&& (user.getDobDay() < 1 || user.getDobDay() > 31)){
			errors.rejectValue("dobDay","user.dobDay.outofrange", "Date of birth Day field must be in range [1,31].");
		}

		if(user.getDobMonth() != null
				&& (user.getDobMonth() < Calendar.JANUARY || user.getDobMonth() > Calendar.DECEMBER)) {
			errors.rejectValue("dobMonth","user.dobMonth.outofrange", "Date of birth Month field must be in range [January(0),December(11)].");
		}

		if(user.getDobYear() != null
				&& (user.getDobYear() > cal.get(Calendar.YEAR))) {
			errors.rejectValue("dobYear","user.dobYear.outofrange", "Date of birth Year field can't be set in the future.");
		}
	}
}
