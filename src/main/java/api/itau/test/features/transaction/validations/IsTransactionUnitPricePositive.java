package api.itau.test.features.transaction.validations;

import api.itau.test.exceptionHandler.InvalidTransaction.InvalidTransactionException;
import api.itau.test.features.transaction.dto.CreateTransactionDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class IsTransactionUnitPricePositive implements TransactionValidator{
    @Override
    public void validate(CreateTransactionDto data) {
        BigDecimal positiveValue = BigDecimal.ZERO;
        if (data.transactionUnitPrice().compareTo(positiveValue) <=0 ){
            throw new InvalidTransactionException("It's not allowed operations with negative unit price");
        }
    }
}

