package nl.surepay.transactionValidator.util;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsvFileReaderTest {

    @Autowired
    private CsvFileReader csvFileReader;

    @Test
    void convertFileToDto() throws IOException {
        byte[] bytes = Objects.requireNonNull(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("recordsIncludingDuplicateReferenceAndInvalidBalance.csv")).openStream().readAllBytes());
        List<TransactionDto> transactions = csvFileReader.convertFileToDto(bytes);
        assertFalse(transactions.isEmpty());
        assertEquals(transactions.size(),10);
        assertEquals(transactions.get(0).getReference(),194261);
        assertEquals(transactions.get(0).getAccountNumber(),"NL91RABO0315273637");
        assertEquals(transactions.get(1).getReference(),112806);
        assertEquals(transactions.get(1).getAccountNumber(),"NL27SNSB0917829871");
    }
    @Test
    void convertFileToDtoInvalidFormatFile() throws IOException {
        byte[] bytes = Objects.requireNonNull(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("invalidFormatrecords.csv")).openStream().readAllBytes());
        assertThrows(InvalidFormatException.class,()->csvFileReader.convertFileToDto(bytes));
    }
    @Test
    void convertFileToDtoNullInput(){
        byte[] bytes =null;
        assertThrows(IllegalArgumentException.class,()->csvFileReader.convertFileToDto(bytes));
    }
}