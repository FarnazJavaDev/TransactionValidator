package nl.surepay.transactionValidator.domain.validation;


import nl.surepay.transactionValidator.domain.dto.TransactionDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TransactionAccountBalanceValidator implements
        ConstraintValidator<TransactionAccountBalanceConstraint, TransactionDto> {

    @Override
    public boolean isValid(TransactionDto transactionDto, ConstraintValidatorContext constraintValidatorContext) {
        return transactionDto.getStartBalance().add(transactionDto.getMutation()).compareTo(transactionDto.getEndBalance())==0;
    }
}
