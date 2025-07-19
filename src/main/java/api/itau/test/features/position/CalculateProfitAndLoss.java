package api.itau.test.features.position;

import java.math.BigDecimal;

public class CalculateProfitAndLoss {
    public static BigDecimal calculate(BigDecimal quantity, BigDecimal averagePrice, BigDecimal currentPrice) {
        if (currentPrice == null || currentPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return (currentPrice.subtract(averagePrice)).multiply(quantity);
    }
}