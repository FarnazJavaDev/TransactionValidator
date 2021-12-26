package nl.surepay.transactionValidator.domain.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.surepay.transactionValidator.domain.dto.TransactionDto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TransactionsUniqueConstraint
public class TransactionsValidationObject {
    private List<TransactionDto> transactions;
    private List<TransactionDto> invalidTransactions =new ArrayList<>();
}
