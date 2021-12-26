package nl.surepay.transactionValidator.controller;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import nl.surepay.transactionValidator.domain.dto.ValidationResponseDto;
import nl.surepay.transactionValidator.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "resources/transactions", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ValidationResponseDto> validateTransactions(@RequestPart("file") MultipartFile file) {
        List<TransactionDto> transactionDtos = transactionService.extractDtoFromFile(file);
        if (!transactionDtos.isEmpty())
            transactionService.validateTransactions(transactionDtos);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
