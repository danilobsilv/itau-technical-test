package api.itau.test.features.position.dto;

import api.itau.test.features.position.Position;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * DTO para exibir os detalhes da posição de um ativo para o cliente.
 */
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
    public PositionDetailsDto(Position position, BigDecimal currentPrice, BigDecimal profitAndLoss, BigDecimal profitAndLossPercentage) {
        this(
                position.getPositionId(),
                position.getAsset().getAssetCode(),
                position.getAsset().getAssetName(),
                position.getQuantity(),
                position.getAveragePrice(),
                currentPrice,
                profitAndLoss,
                profitAndLossPercentage
        );
    }

}
