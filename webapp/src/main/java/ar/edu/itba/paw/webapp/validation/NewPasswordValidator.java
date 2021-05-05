package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.webapp.form.PasswordChangeForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NewPasswordValidator implements ConstraintValidator<ValidNewPassword, PasswordChangeForm> {
	@Override
	public void initialize(ValidNewPassword validNewPassword) {

	}

	@Override
	public boolean isValid(PasswordChangeForm form, ConstraintValidatorContext context) {
		if (!form.getNewPass().isEmpty() && form.getNewPass().equals(form.getCurrentPass())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Passwords can not match")
					.addNode("currentPass")
					.addConstraintViolation();
			return false;
		}

		if (!form.getNewPass().equals(form.getRepeatNewPass())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Passwords must match")
					.addNode("repeatNewPass")
					.addConstraintViolation();
			return false;
		}

		return true;
	}
}
