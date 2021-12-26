package nl.surepay.transactionValidator.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import nl.surepay.transactionValidator.domain.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CsvFileReader implements FileReader {

    @Override
    public List<TransactionDto> convertFileToDto(byte[] fileBytes) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(TransactionDto.class).sortedBy("reference","accountNumber","description","startBalance","mutation","endBalance").withHeader().withColumnSeparator(',');
        MappingIterator<TransactionDto> parsedData = csvMapper.readerWithSchemaFor(TransactionDto.class)
                .with(schema)
                .readValues(fileBytes);

        return parsedData.readAll();
    }
}
