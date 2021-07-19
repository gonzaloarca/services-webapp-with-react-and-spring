package ar.edu.itba.paw.webapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DistinctFieldsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DistinctFields {
    public String message() default "Fields values can not match!";

    public String field();

    public String distinctField();

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
