package ar.edu.itba.paw.webapp.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DistinctFieldsValidator implements ConstraintValidator<DistinctFields, Object> {
    private String field;
    private String distinctField;
    private String message;

    public void initialize(DistinctFields constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.distinctField = constraintAnnotation.distinctField();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        Object distinctFieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(distinctField);

        if (fieldValue != null && !fieldValue.equals(distinctFieldValue)) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addNode(distinctField)
                .addConstraintViolation();

        return false;
    }
}
