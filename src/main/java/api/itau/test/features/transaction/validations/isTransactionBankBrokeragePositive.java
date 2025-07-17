package api.itau.test.features.transaction.validations;

import api.itau.test.exceptionHandler.InvalidTransaction.InvalidTransactionException;
import api.itau.test.features.transaction.dto.CreateTransactionDto;

import java.math.BigDecimal;

public class isTransactionBankBrokeragePositive implements TransactionValidator{

    @Override
    public void validate(CreateTransactionDto data) {
        BigDecimal positiveValue = BigDecimal.ZERO;

        if (data.transactionBankBrokerage().compareTo(positiveValue) <= 0){
            throw new InvalidTransactionException("It's not allowed operations with negative bank brokerage");
        }
    }
}
