package api.itau.test.features.position.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePositionDto(
        @NotNull
        UUID userId,
        @NotNull
        UUID assetId,
        @Positive
        BigDecimal quantity,
        @Positive
        BigDecimal averagePrice
) {
}
