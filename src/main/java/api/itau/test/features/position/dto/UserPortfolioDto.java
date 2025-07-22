package api.itau.test.features.position.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record UserPortfolioDto(
        UUID userId,
        String userName,
        BigDecimal totalValue,
        List<PositionDetailsDto> positions
) {
}
