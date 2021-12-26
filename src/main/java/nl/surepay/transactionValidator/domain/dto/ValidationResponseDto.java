package nl.surepay.transactionValidator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResponseDto {
    private Set<ValidationResultDto> validationResultSetDto = new HashSet<>();
    private String exceptionMessage;
}
