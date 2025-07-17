package api.itau.test.features.transaction.validations;

import api.itau.test.exceptionHandler.InvalidTransaction.InvalidTransactionException;
import api.itau.test.features.transaction.dto.CreateTransactionDto;

import java.math.BigDecimal;

public class isTransactionUnitPricePositive implements TransactionValidator{
    @Override
    public void validate(CreateTransactionDto data) {
        BigDecimal positiveValue = BigDecimal.ZERO;
        if (data.transactionUnitPrice().compareTo(positiveValue) <=0 ){
            throw new InvalidTransactionException("It's not allowed operations with negative unit price");
        }
    }
}

