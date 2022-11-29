package icesi.VirtualStore.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

public interface CustomAnnotations {

    @Documented
    @Constraint(validatedBy = NameValidator.class)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface NameValidation {


        String message() default "Name is invalid";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

    @Documented
    @Constraint(validatedBy = NameValidator.class)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface EmailValidation {


        String message() default "Name is invalid";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }
}




