// Em: api.itau.test.features.position.dto.PositionDetailsDto.java
package api.itau.test.features.position.dto;

import api.itau.test.features.position.Position;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public record PositionDetailsDto(
        UUID positionId,
        String assetCode,
        String assetName,
        BigDecimal quantity,
        BigDecimal averagePrice,
        BigDecimal currentPrice,
        BigDecimal profitAndLoss,
        BigDecimal profitAndLossPercentage
) {
    public PositionDetailsDto(Position position, BigDecimal currentPrice) {
        this(
                position.getPositionId(),
                position.getAsset().getAssetCode(),
                position.getAsset().getAssetName(),
                position.getQuantity(),
                position.getAveragePrice(),
                currentPrice,
                calculateProfitAndLoss(position.getQuantity(), position.getAveragePrice(), currentPrice),
                calculateProfitAndLossPercentage(position.getAveragePrice(), currentPrice)
        );
    }

    private static BigDecimal calculateProfitAndLoss(BigDecimal quantity, BigDecimal averagePrice, BigDecimal currentPrice) {
        if (currentPrice == null || currentPrice.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return (currentPrice.subtract(averagePrice)).multiply(quantity);
    }

    private static BigDecimal calculateProfitAndLossPercentage(BigDecimal averagePrice, BigDecimal currentPrice) {
        if (currentPrice == null || averagePrice.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return (currentPrice.divide(averagePrice, 4, RoundingMode.HALF_UP))
                .subtract(BigDecimal.ONE)
                .multiply(new BigDecimal("100"));
    }
}

