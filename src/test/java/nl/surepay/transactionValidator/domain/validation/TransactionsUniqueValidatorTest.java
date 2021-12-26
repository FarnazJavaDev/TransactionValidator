package nl.surepay.transactionValidator.domain.validation;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsUniqueValidatorTest {

    @Test
    void isValidIncludingDuplicateTransaction() {
        TransactionsValidationObject transactionsValidationObject=new TransactionsValidationObject();
        List<TransactionDto> transactions=new ArrayList<>();
        TransactionDto transactionDto1 = new TransactionDto(194261, "NL91RABO0315273637", "Book John Smith", BigDecimal.valueOf(21.6), BigDecimal.valueOf(-41.83), BigDecimal.valueOf(-20.23));
        TransactionDto transactionDto2 = new TransactionDto(194261, "NL27SNSB0917829871", "Clothes Irma Steven", BigDecimal.valueOf(91.23), BigDecimal.valueOf(+15.57), BigDecimal.valueOf(106.8));
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);
        transactionsValidationObject.setTransactions(transactions);
        TransactionsUniqueValidator validator=new TransactionsUniqueValidator();
        assertFalse(validator.isValid(transactionsValidationObject,null));
    }
    @Test
    void isValidIncludingValidTransaction() {
        TransactionsValidationObject transactionsValidationObject=new TransactionsValidationObject();
        List<TransactionDto> transactions=new ArrayList<>();
        TransactionDto transactionDto1 = new TransactionDto(194261, "NL91RABO0315273637", "Book John Smith", BigDecimal.valueOf(21.6), BigDecimal.valueOf(-41.83), BigDecimal.valueOf(-20.23));
        TransactionDto transactionDto2 = new TransactionDto(194262, "NL27SNSB0917829871", "Clothes Irma Steven", BigDecimal.valueOf(91.23), BigDecimal.valueOf(+15.57), BigDecimal.valueOf(106.8));
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);
        transactionsValidationObject.setTransactions(transactions);
        TransactionsUniqueValidator validator=new TransactionsUniqueValidator();
        assertTrue(validator.isValid(transactionsValidationObject,null));
    }
}