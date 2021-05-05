package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.webapp.form.PackageForm;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PriceNotEmptyValidator implements ConstraintValidator<PriceNotEmpty, PackageForm> {

    @Override
    public void initialize(PriceNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(PackageForm form,
                           ConstraintValidatorContext context) {
        Integer ordinal = form.getRateType();
        String price = form.getPrice();

        if (ordinal == null) {
            return false;
        }

        if (ordinal != JobPackage.RateType.TBD.ordinal() &&
                (price == null || price.isEmpty() ||
                        !Pattern.matches("^(?=.+)(?:[1-9]\\d*|0)?(?:\\.\\d+)?$", price))) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Introduzca un precio válido").addNode("price")
                        .addConstraintViolation();
                return false;
        }

        return true;
    }
}
