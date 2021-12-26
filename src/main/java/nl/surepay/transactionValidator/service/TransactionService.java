package nl.surepay.transactionValidator.service;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import nl.surepay.transactionValidator.domain.dto.ValidationResultDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface TransactionService {
    List<TransactionDto> extractDtoFromFile(MultipartFile file);
    void validateTransactions(List<TransactionDto> transactionDtoList);
}
