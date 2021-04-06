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

        JobPackage.RateType rateType = JobPackage.RateType
                .values()[(int) new BeanWrapperImpl(value).getPropertyValue("rateType")];
        String price = (String) new BeanWrapperImpl(value).getPropertyValue("price");

        return (rateType == JobPackage.RateType.TBD || price != null
                && Pattern.matches("^(?=.+)(?:[1-9]\\d*|0)?(?:\\.\\d+)?$", price));
    }
}
