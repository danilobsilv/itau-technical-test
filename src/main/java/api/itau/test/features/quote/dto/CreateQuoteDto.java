package api.itau.test.features.quote.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateQuoteDto(
    @NotNull
    UUID assetId,

    @NotNull
    @Positive
    BigDecimal quoteUnityPrice
) {
}

