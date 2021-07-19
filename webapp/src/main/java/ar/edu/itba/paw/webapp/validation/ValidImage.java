package ar.edu.itba.paw.webapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* Esta annotation valida que el archivo es de un tipo de imagen o que no hay archivo (size = 0 es valido) */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ImageFileValidator.class})
public @interface ValidImage {
    public String message() default "Invalid image file";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
