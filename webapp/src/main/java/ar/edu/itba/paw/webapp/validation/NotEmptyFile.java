package ar.edu.itba.paw.webapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* Esta annotation valida que el archivo no tenga size = 0 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotEmptyFileValidator.class})
public @interface NotEmptyFile {
    String message() default "A file must be selected";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
