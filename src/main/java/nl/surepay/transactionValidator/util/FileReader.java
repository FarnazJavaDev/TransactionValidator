package nl.surepay.transactionValidator.util;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


public interface FileReader {
    List<TransactionDto> convertFileToDto(byte[] fileBytes) throws IOException;
}
