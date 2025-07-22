package api.itau.test.features.transaction.dto;

import api.itau.test.features.transaction.OperationType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreateTransactionDto(
    @NotNull
    UUID userId,
    @NotNull
    UUID assetId,
    @Positive
    @NotNull
    BigDecimal transactionAmount,
    @Positive
    @NotNull
    BigDecimal transactionUnitPrice,
    @NotNull
    OperationType operationType,
    @PositiveOrZero
    @NotNull
    BigDecimal transactionBankBrokerage,
    @NotNull
    Instant transactionDateTime
) {}