package nl.surepay.transactionValidator.util;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JsonFileReaderTest {

    @Autowired
    private JsonFileReader jsonFileReader;

    @Test
    void convertFileToDto() throws IOException {
        byte[] bytes = Objects.requireNonNull(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("records.json")).openStream().readAllBytes());
        List<TransactionDto> transactions = jsonFileReader.convertFileToDto(bytes);
        assertFalse(transactions.isEmpty());
        assertEquals(transactions.size(),10);
        assertEquals(transactions.get(0).getReference(),130498);
        assertEquals(transactions.get(0).getAccountNumber(),"NL69ABNA0433647324");
        assertEquals(transactions.get(1).getReference(),167875);
        assertEquals(transactions.get(1).getAccountNumber(),"NL93ABNA0585619023");
    }
    @Test
    void convertFileToDtoInvalidFormatFile() throws IOException {
        byte[] bytes = Objects.requireNonNull(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("recordWithOutReferenceField.json")).openStream().readAllBytes());
        List<TransactionDto> transactionDtos = jsonFileReader.convertFileToDto(bytes);
        assertNotNull(transactionDtos);
        assertFalse(transactionDtos.isEmpty());
        assertEquals(transactionDtos.size(),1);
        assertNull(transactionDtos.get(0).getReference());
    }
    @Test
    void convertFileToDtoNullInput(){
        byte[] bytes =null;
        assertThrows(IllegalArgumentException.class,()->jsonFileReader.convertFileToDto(bytes));
    }
}