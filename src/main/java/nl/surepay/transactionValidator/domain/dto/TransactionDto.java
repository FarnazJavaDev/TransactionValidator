package nl.surepay.transactionValidator.domain.dto;

import lombok.*;
import nl.surepay.transactionValidator.domain.validation.TransactionAccountBalanceConstraint;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TransactionAccountBalanceConstraint
@ToString
public class TransactionDto {
    @NotNull(message = "Transaction reference could not be null!")
    private Integer reference;
    @NotNull
    @Pattern(regexp = "^NL[0-9]{2}[A-z0-9]{4}[0-9]{10}$")
    private String accountNumber;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal startBalance;
    @NotNull
    private BigDecimal mutation;
    @NotNull
    private BigDecimal endBalance;
}
