package nl.surepay.transactionValidator.domain.validation;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import nl.surepay.transactionValidator.exception.CustomValidationException;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionValidationAspectMockTest {

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Validator validator;

    @Mock
    private Set<ConstraintViolation<TransactionsValidationObject>> violations;
    @Mock
    private Set<ConstraintViolation<TransactionsValidationObject>> violations2;

    private TransactionValidationAspect transactionValidationAspect;
    private TransactionValidationAspect transactionValidationAspectSpy;

    @BeforeEach
    public void setup() {
        transactionValidationAspect = new TransactionValidationAspect(validator);
        transactionValidationAspectSpy = spy(transactionValidationAspect);
    }

    @Test
    public void extractFileAspectValidData() {
        List<TransactionDto> transactions = getTransactionDtoList();
        Object[] args = new Object[1];
        args[0]=transactions;
        doReturn(args).when(joinPoint).getArgs();
        doReturn(violations).when(validator).validate(any());
        doReturn(true).when(violations).isEmpty();
        assertDoesNotThrow(()->transactionValidationAspectSpy.validateTransactions(joinPoint));
        verify(joinPoint,times(1)).getArgs();
        verify(violations,times(3)).isEmpty();
    }
    @Test
    public void extractFileAspectInvalidData() {
        List<TransactionDto> transactions = getTransactionDtoList();
        Object[] args = new Object[1];
        args[0]=transactions;
        doReturn(args).when(joinPoint).getArgs();
        doReturn(violations).when(validator).validate(any(TransactionsValidationObject.class));
        doReturn(violations2).when(validator).validate(any(TransactionDto.class));
        doReturn(true).when(violations).isEmpty();
        doReturn(false).when(violations2).isEmpty();
        assertThrows(CustomValidationException.class,()->transactionValidationAspectSpy.validateTransactions(joinPoint));
        verify(joinPoint,times(1)).getArgs();
        verify(violations,times(1)).isEmpty();
        verify(violations2,times(2)).isEmpty();
    }

    private List<TransactionDto> getTransactionDtoList() {
        List<TransactionDto> transactions = new ArrayList<>();
        TransactionDto transactionDto1 = new TransactionDto(194261, "NL91RABO0315273637", "Book John Smith", BigDecimal.valueOf(21.6), BigDecimal.valueOf(-41.83), BigDecimal.valueOf(-20.23));
        TransactionDto transactionDto2 = new TransactionDto(194262, "NL27SNSB0917829871", "Clothes Irma Steven", BigDecimal.valueOf(91.23), BigDecimal.valueOf(+15.57), BigDecimal.valueOf(106.8));
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);
        return transactions;
    }
}