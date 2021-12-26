package nl.surepay.transactionValidator.service;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import nl.surepay.transactionValidator.exception.CustomValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceImplSystemTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void extractDtoFromFile() {
    }

    @Test
    void validateTransactionsIncludingDuplicateReference() {
        List<TransactionDto> transactions = new ArrayList<>();
        TransactionDto transactionDto1 = new TransactionDto(194261, "NL91RABO0315273637", "Book John Smith", BigDecimal.valueOf(21.6), BigDecimal.valueOf(-41.83), BigDecimal.valueOf(-20.23));
        TransactionDto transactionDto2 = new TransactionDto(194261, "NL27SNSB0917829871", "Clothes Irma Steven", BigDecimal.valueOf(91.23), BigDecimal.valueOf(+15.57), BigDecimal.valueOf(106.8));
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);
        assertThrows(CustomValidationException.class ,()-> transactionService.validateTransactions(transactions));
    }
    @Test
    void validateTransactionsIncludingInvalidAccountBalance() {
        List<TransactionDto> transactions = new ArrayList<>();
        TransactionDto transactionDto1 = new TransactionDto(194261, "NL91RABO0315273637", "Book John Smith", BigDecimal.valueOf(21.6), BigDecimal.valueOf(-41.83), BigDecimal.valueOf(-20.23));
        TransactionDto transactionDto2 = new TransactionDto(194262, "NL27SNSB0917829871", "Clothes Irma Steven", BigDecimal.valueOf(91.23), BigDecimal.valueOf(+15.57), BigDecimal.valueOf(106));
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);
        assertThrows(CustomValidationException.class ,()-> transactionService.validateTransactions(transactions));
    }
    @Test
    void validateTransactionsValidData() {
        List<TransactionDto> transactions = new ArrayList<>();
        TransactionDto transactionDto1 = new TransactionDto(194261, "NL91RABO0315273637", "Book John Smith", BigDecimal.valueOf(21.6), BigDecimal.valueOf(-41.83), BigDecimal.valueOf(-20.23));
        TransactionDto transactionDto2 = new TransactionDto(194262, "NL27SNSB0917829871", "Clothes Irma Steven", BigDecimal.valueOf(91.23), BigDecimal.valueOf(+15.57), BigDecimal.valueOf(106.8));
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);
        assertDoesNotThrow(()-> transactionService.validateTransactions(transactions));
    }
}