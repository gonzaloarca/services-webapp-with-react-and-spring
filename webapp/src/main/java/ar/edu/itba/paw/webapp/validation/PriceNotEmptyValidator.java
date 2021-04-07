package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.models.JobPackage;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PriceNotEmptyValidator implements ConstraintValidator<PriceNotEmpty, Object> {

    @Override
    public void initialize(PriceNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {
        //TODO: Paramterizar mensajes respecto de messages.properties correctamente
        JobPackage.RateType rateType = JobPackage.RateType
                .values()[(int) new BeanWrapperImpl(value).getPropertyValue("rateType")];
        String price = (String) new BeanWrapperImpl(value).getPropertyValue("price");

        if (rateType != JobPackage.RateType.TBD) {
            if (price == null || price.isEmpty()) {
                return false;
            } else if (!Pattern.matches("^(?=.+)(?:[1-9]\\d*|0)?(?:\\.\\d+)?$", price)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("El precio debe ser un número decimal válido")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
