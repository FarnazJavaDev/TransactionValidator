package nl.surepay.transactionValidator.domain.validation;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionAccountBalanceValidatorTest {

    @Test
    void isValid() {
        TransactionDto transactionDto1 = new TransactionDto(194261, "NL91RABO0315273637", "Book John Smith", BigDecimal.valueOf(21.6), BigDecimal.valueOf(-41.83), BigDecimal.valueOf(-20.23));
        TransactionAccountBalanceValidator validator=new TransactionAccountBalanceValidator();
        assertTrue(validator.isValid(transactionDto1,null));
    }
    @Test
    void isInvalid() {
        TransactionDto transactionDto1 = new TransactionDto(194261, "NL91RABO0315273637", "Book John Smith", BigDecimal.valueOf(21.6), BigDecimal.valueOf(-41.83), BigDecimal.valueOf(20.23));
        TransactionAccountBalanceValidator validator=new TransactionAccountBalanceValidator();
        assertFalse(validator.isValid(transactionDto1,null));
    }
}