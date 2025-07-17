package api.itau.test.features.transaction.dto;

import api.itau.test.features.transaction.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreateTransactionDto(
        @NotNull
        UUID userId,

        @NotNull
        UUID assetId,

        @NotNull
        @Positive
        BigDecimal transactionAmount,

        @NotNull
        @Positive
        BigDecimal transactionUnitPrice,

        @NotNull
        OperationType operationType,

        @NotNull
        @Positive
        BigDecimal transactionBankBrokerage,


        @NotNull
        @PastOrPresent(message = "This transaction can't be made in the future")
        Instant transactionDateTime
) {
}
