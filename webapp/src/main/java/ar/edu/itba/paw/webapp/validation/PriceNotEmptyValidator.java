package ar.edu.itba.paw.webapp.validation;

import ar.edu.itba.paw.models.JobPackage;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PriceNotEmptyValidator implements ConstraintValidator<PriceNotEmpty, Object> {

    private String price;

    private String rateType;

    private String message;

    @Override
    public void initialize(PriceNotEmpty constraintAnnotation) {
        this.price = constraintAnnotation.price();
        this.rateType = constraintAnnotation.rateType();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {
        Object ordinal = new BeanWrapperImpl(value)
                .getPropertyValue(rateType);
        Object priceValue = new BeanWrapperImpl(value)
                .getPropertyValue(price);

        if (ordinal == null) {
            return true; //el null de RateType se checkea desde el form
        }

        if (!ordinal.equals(JobPackage.RateType.TBD.ordinal()) &&
                (priceValue == null || priceValue.equals("") ||
                        !Pattern.matches("^(?=.+)(?:[1-9]\\d*|0)?(?:\\.\\d+)?$", priceValue.toString()))) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addNode(price)
                        .addConstraintViolation();
                return false;
        }

        return true;
    }
}
