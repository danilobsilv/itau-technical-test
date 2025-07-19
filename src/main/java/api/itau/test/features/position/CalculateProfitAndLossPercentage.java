package api.itau.test.features.position;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateProfitAndLossPercentage {
    public static BigDecimal calculate(BigDecimal averagePrice, BigDecimal currentPrice) {
        if (currentPrice == null || averagePrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return (currentPrice.divide(averagePrice, 4, RoundingMode.HALF_UP))
                .subtract(BigDecimal.ONE)
                .multiply(new BigDecimal("100"));
    }
}