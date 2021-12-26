package nl.surepay.transactionValidator.domain.validation;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import nl.surepay.transactionValidator.exception.CustomValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aspect
@Component
public class TransactionValidationAspect {
    private final Validator validator;
    Logger logger = LoggerFactory.getLogger(TransactionValidationAspect.class);

    public TransactionValidationAspect(Validator validator) {
        this.validator = validator;
    }

    @Before("execution(* nl.surepay.transactionValidator.service.TransactionService.validateTransactions(*))")
    public void validateTransactions(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (!(args[0] instanceof List)) {
            throw new RuntimeException("Invalid method arg");
        }
        List<TransactionDto> transactions = (List<TransactionDto>) args[0];
        logger.info("validateTransactions is called for validating : " + transactions);
        Set<TransactionDto> errors = new HashSet<>();
        TransactionsValidationObject validationObject = new TransactionsValidationObject(transactions, new ArrayList<>());
        Set<ConstraintViolation<TransactionsValidationObject>> violations = validator.validate(validationObject);
        if (!violations.isEmpty()) {
            errors.addAll(validationObject.getInvalidTransactions());
        }
        transactions.forEach(dto -> {
            Set<ConstraintViolation<TransactionDto>> violations2 = validator.validate(dto);
            if (!violations2.isEmpty()) {
                errors.add(dto);
            }
        });
        if (!errors.isEmpty()) {
            logger.error("Invalid transaction List : " + errors);
            throw new CustomValidationException(errors);
        }
    }


}
