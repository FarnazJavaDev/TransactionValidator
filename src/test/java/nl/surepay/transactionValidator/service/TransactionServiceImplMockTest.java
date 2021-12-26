package nl.surepay.transactionValidator.service;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import nl.surepay.transactionValidator.util.CsvFileReader;
import nl.surepay.transactionValidator.util.JsonFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplMockTest {

    @Mock
    private MultipartFile file;
    @Mock
    private CsvFileReader csvFileReader;
    @Mock
    private JsonFileReader jsonFileReader;
    private TransactionServiceImpl transactionService;
    private TransactionServiceImpl transactionServiceSpy;

    @BeforeEach
    public void setup() {
        transactionService = new TransactionServiceImpl(csvFileReader, jsonFileReader);
        transactionServiceSpy = spy(transactionService);
    }

    @Test
    public void extractDtoFromFile_csv() throws IOException {
        List<TransactionDto> transactions = getTransactionDtoList();
        doReturn("text/csv").when(file).getContentType();
        doReturn(transactions).when(csvFileReader).convertFileToDto(any());
        var transactionDtos = transactionServiceSpy.extractDtoFromFile(file);
        assertEquals(transactionDtos.size(),2);
        assertEquals(transactionDtos.get(0),transactions.get(0));
        assertEquals(transactionDtos.get(1),transactions.get(1));
        verify(file,times(1)).getContentType();
        verify(file,times(1)).getBytes();
        verify(csvFileReader,times(1)).convertFileToDto(any());
    }
    @Test
    public void extractDtoFromFile_json() throws IOException {
        List<TransactionDto> transactions = getTransactionDtoList();
        doReturn("application/json").when(file).getContentType();
        doReturn(transactions).when(jsonFileReader).convertFileToDto(any());
        var transactionDtos = transactionServiceSpy.extractDtoFromFile(file);
        assertEquals(transactionDtos.size(),2);
        assertEquals(transactionDtos.get(0),transactions.get(0));
        assertEquals(transactionDtos.get(1),transactions.get(1));
        verify(file,times(1)).getContentType();
        verify(file,times(1)).getBytes();
        verify(jsonFileReader,times(1)).convertFileToDto(any());
    }

    private List<TransactionDto> getTransactionDtoList() {
        List<TransactionDto> transactions = new ArrayList<>();
        TransactionDto transactionDto1 = new TransactionDto(194261, "NL91RABO0315273637", "Book John Smith", BigDecimal.valueOf(21.6), BigDecimal.valueOf(-41.83), BigDecimal.valueOf(-20.23));
        TransactionDto transactionDto2 = new TransactionDto(194262, "NL27SNSB0917829871", "Clothes Irma Steven", BigDecimal.valueOf(91.23), BigDecimal.valueOf(+15.57), BigDecimal.valueOf(106));
        transactions.add(transactionDto1);
        transactions.add(transactionDto2);
        return transactions;
    }

    @Test
    public void extractDtoFromFile_txt() throws IOException {
        doReturn("text").when(file).getContentType();
        assertThrows(RuntimeException.class,()->transactionServiceSpy.extractDtoFromFile(file));
        verify(file,times(1)).getContentType();
        verify(file,never()).getBytes();
        verify(jsonFileReader,never()).convertFileToDto(any());
    }

    @Test
    public void extractDtoFromFile_csv_throwsException() throws IOException {
        doReturn("text/csv").when(file).getContentType();
        when(csvFileReader.convertFileToDto(any())).thenThrow(new IOException());
        assertThrows(RuntimeException.class,()->transactionServiceSpy.extractDtoFromFile(file));
        verify(file,times(1)).getContentType();
        verify(file,times(1)).getBytes();
        verify(csvFileReader,times(1)).convertFileToDto(any());
    }
}