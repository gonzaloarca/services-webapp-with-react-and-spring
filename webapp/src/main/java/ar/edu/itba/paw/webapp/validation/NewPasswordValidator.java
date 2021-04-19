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
		//TODO: Aplicar i18n a mensajes
		if (!form.getNewPass().isEmpty() && form.getNewPass().equals(form.getCurrentPass())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("La nueva contraseña y la actual no pueden coincidir")
					.addNode("currentPass")
					.addConstraintViolation();
			return false;
		}

		if (!form.getNewPass().equals(form.getRepeatNewPass())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Las contraseñas no coinciden")
					.addNode("repeatNewPass")
					.addConstraintViolation();
			return false;
		}

		return true;
	}
}
