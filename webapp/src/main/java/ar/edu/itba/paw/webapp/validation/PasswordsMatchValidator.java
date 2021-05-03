package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.webapp.form.RegisterForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, RegisterForm> {

    @Override
    public void initialize(PasswordsMatch passwordsMatch) {

    }

    @Override
    public boolean isValid(RegisterForm form, ConstraintValidatorContext context) {
        //TODO: Aplicar i18n a mensaje
        if (form == null || form.getPassword() ==null || !form.getPassword().equals(form.getRepeatPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Las contraseñas no coinciden")
                    .addNode("repeatPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
