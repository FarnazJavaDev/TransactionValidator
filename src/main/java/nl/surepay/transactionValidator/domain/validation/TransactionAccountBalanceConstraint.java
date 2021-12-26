package nl.surepay.transactionValidator.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = nl.surepay.transactionValidator.domain.validation.TransactionAccountBalanceValidator.class)
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionAccountBalanceConstraint {
    String message() default "endBalance is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
