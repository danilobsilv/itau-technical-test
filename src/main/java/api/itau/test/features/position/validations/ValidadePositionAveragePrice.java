package api.itau.test.features.position.validations;

import api.itau.test.exceptionHandler.InvalidPosition.InvalidPositionException;
import api.itau.test.features.position.dto.CreatePositionDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidadePositionAveragePrice implements PositionValidator{
    @Override
    public void validate(CreatePositionDto data) {
        BigDecimal positiveValue = BigDecimal.ZERO;

        if (data.averagePrice().compareTo(positiveValue) < 0){
            throw new InvalidPositionException("It's not allowed operations with negative average price.");
        }
    }
}
