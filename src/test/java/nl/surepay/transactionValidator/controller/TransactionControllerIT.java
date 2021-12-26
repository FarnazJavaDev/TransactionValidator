package nl.surepay.transactionValidator.controller;

import nl.surepay.transactionValidator.domain.dto.ValidationResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    public static Resource getFile(String fileName) {
        String path = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(fileName)).getPath();
        return new FileSystemResource(path);
    }

    @Test
    void validateCsvFileTransactionsIncludingTwoDuplicateReference() throws URISyntaxException {
        ResponseEntity<ValidationResponseDto> response = callService("records.csv");
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getValidationResultSetDto().size(), 2);
    }

    @Test
    void validateCsvFileTransactionsIncludingDuplicateReferenceAndInvalidBalance() throws URISyntaxException {
        ResponseEntity<ValidationResponseDto> response = callService("recordsIncludingDuplicateReferenceAndInvalidBalance.csv");
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getValidationResultSetDto().size(), 3);
        assertEquals(response.getBody().getValidationResultSetDto().iterator().next().getReference(), 112806);
        assertEquals(response.getBody().getValidationResultSetDto().iterator().next().getDescription(), "Book Richard Tyson");
    }

    @Test
    void validateJsonFileTransactionsIncludingInvalidAccountBalance() throws URISyntaxException {
        ResponseEntity<ValidationResponseDto> response = callService("records.json");
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getValidationResultSetDto().size(), 2);
        assertEquals(response.getBody().getValidationResultSetDto().iterator().next().getReference(), 167875);
        assertEquals(response.getBody().getValidationResultSetDto().iterator().next().getDescription(), "Toy Greg Alysha");
    }

    @Test
    void validateJsonFileTransactionsValidFile() throws URISyntaxException {
        ResponseEntity<ValidationResponseDto> response = callService("validRecords.json");
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNull(response.getBody());
    }

    @Test
    void validateCsvFileTransactionsValidFile() throws URISyntaxException {
        ResponseEntity<ValidationResponseDto> response = callService("validRecords.csv");
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNull(response.getBody());
    }

    @Test
    void validateTextFileTransactions() throws URISyntaxException {
        ResponseEntity<ValidationResponseDto> response = callService("records.txt");
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getExceptionMessage());
    }

    @Test
    void validateJsonFileTransactionsMissingReferenceField() throws URISyntaxException {
        ResponseEntity<ValidationResponseDto> response = callService("recordWithOutReferenceField.json");
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
    }

    private ResponseEntity<ValidationResponseDto> callService(String fileName) throws URISyntaxException {
        String address = "http://localhost:" + port + "/resources/transactions";
        URI uri = new URI(address);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getFile(fileName));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(uri, requestEntity, ValidationResponseDto.class);
    }
}