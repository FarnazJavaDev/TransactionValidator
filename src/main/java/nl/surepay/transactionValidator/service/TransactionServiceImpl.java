package nl.surepay.transactionValidator.service;

import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import nl.surepay.transactionValidator.util.CsvFileReader;
import nl.surepay.transactionValidator.util.FileReader;
import nl.surepay.transactionValidator.util.JsonFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private Map<String, FileReader> fileReaderMap = new HashMap<>();


    public TransactionServiceImpl(CsvFileReader csv, JsonFileReader jsonFileReader) {
        fileReaderMap.put("text/csv", csv);
        fileReaderMap.put("application/json", jsonFileReader);
    }

    @Override
    public List<TransactionDto> extractDtoFromFile(MultipartFile file) {
        List<TransactionDto> transactionList = new ArrayList<>();
        Optional<FileReader> fileReader = Optional.ofNullable(fileReaderMap.get(file.getContentType()));
        fileReader.ifPresent(reader -> {
            try {
                transactionList.addAll(reader.convertFileToDto(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException("Could not load file!", e);
            }
        });
        fileReader.orElseThrow(() -> new RuntimeException("File Format is not accepted!"));
        return transactionList;
    }

    @Override
    public void validateTransactions(List<TransactionDto> transactionDtoList) {
        //todo log
        logger.info("Valid transaction List : " + transactionDtoList.toString());
    }
}
