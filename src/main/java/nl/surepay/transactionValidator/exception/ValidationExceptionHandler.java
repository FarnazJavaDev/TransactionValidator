package nl.surepay.transactionValidator.exception;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import nl.surepay.transactionValidator.domain.dto.ValidationResultDto;
import nl.surepay.transactionValidator.domain.dto.ValidationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {CustomValidationException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex) {
        List<TransactionDto> invalidTransactions = new ArrayList<>(((CustomValidationException) ex).getInvalidTransactions());
        var results = invalidTransactions.stream().map(tr -> new ValidationResultDto(tr.getReference(), tr.getDescription())).collect(Collectors.toSet());
        ValidationResponseDto validationResultSetDto = new ValidationResponseDto();
        validationResultSetDto.setValidationResultSetDto(results);
        return new ResponseEntity(validationResultSetDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleFileErrors(RuntimeException ex) {
        ValidationResponseDto validationResultSetDto = new ValidationResponseDto();
        validationResultSetDto.setExceptionMessage(ex.getMessage());
        return new ResponseEntity(validationResultSetDto, HttpStatus.BAD_REQUEST);
    }
}
