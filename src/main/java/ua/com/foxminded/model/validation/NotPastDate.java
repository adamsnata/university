package ua.com.foxminded.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckNotPastDateValidator.class)
@Documented
public @interface NotPastDate {

    String message() default "{ua.com.foxminded.constraints.NotPastDate}";

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
