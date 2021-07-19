package ar.edu.itba.paw.webapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ImageListValidator.class})
public @interface ValidImageList {
    public String message() default "Invalid image file";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
