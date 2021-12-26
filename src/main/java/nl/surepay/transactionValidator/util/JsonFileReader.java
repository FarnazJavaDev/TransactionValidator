package nl.surepay.transactionValidator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JsonFileReader implements FileReader{
    @Override
    public List<TransactionDto> convertFileToDto(byte[] fileBytes) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(fileBytes, TransactionDto[].class));
    }
}
