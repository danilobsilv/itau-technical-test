package api.itau.test.features.quote.dto;

import api.itau.test.features.quote.Quote;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record QuoteDetailsDto(
        UUID quoteId,
        String assetCode,
        BigDecimal quoteUnityPrice,
        Instant quoteDateTime
) {
    public QuoteDetailsDto(Quote quote) {
        this(
            quote.getQuoteId(),
            quote.getAsset().getAssetCode(),
            quote.getQuoteUnityPrice(),
            quote.getQuoteDateTime()
        );
    }
}
