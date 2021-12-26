package nl.surepay.transactionValidator.domain.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class TransactionsUniqueValidator implements
        ConstraintValidator<TransactionsUniqueConstraint,TransactionsValidationObject> {

    @Override
    public boolean isValid(TransactionsValidationObject transactions, ConstraintValidatorContext constraintValidatorContext) {

        Set<Integer> total = new HashSet<>();
        transactions.getTransactions().stream().forEach(a -> {
            if(total.contains(a.getReference())){
                transactions.getInvalidTransactions().add(a);
            }
            else
                total.add(a.getReference());
        });
        return transactions.getInvalidTransactions().isEmpty();
    }
}
