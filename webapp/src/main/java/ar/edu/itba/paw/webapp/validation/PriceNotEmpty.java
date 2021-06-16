package ar.edu.itba.paw.webapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PriceNotEmptyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceNotEmpty {

    String price();

    String rateType();

    String message() default "Price is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
