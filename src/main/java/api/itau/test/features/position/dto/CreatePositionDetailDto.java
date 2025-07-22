package api.itau.test.features.position.dto;

import api.itau.test.features.position.Position;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePositionDetailDto(
        UUID positionId,
        UUID userId,
        UUID assetId,
        BigDecimal quantity,
        BigDecimal averagePrice
) {
    public CreatePositionDetailDto(Position position){
        this(
            position.getPositionId(),
            position.getUser().getUserId(),
            position.getAsset().getAssetId(),
            position.getQuantity(),
            position.getAveragePrice()
        );
    }
}
