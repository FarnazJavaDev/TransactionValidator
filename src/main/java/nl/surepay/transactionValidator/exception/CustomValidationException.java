package nl.surepay.transactionValidator.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.surepay.transactionValidator.domain.dto.TransactionDto;

import java.util.Set;

@Data
@AllArgsConstructor
public class CustomValidationException extends RuntimeException {
    private Set<TransactionDto> invalidTransactions;
}
