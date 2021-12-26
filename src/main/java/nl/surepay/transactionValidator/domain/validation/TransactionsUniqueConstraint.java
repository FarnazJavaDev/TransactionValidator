package nl.surepay.transactionValidator.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TransactionsUniqueValidator.class)
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionsUniqueConstraint {
    String message() default "Duplicate reference number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
