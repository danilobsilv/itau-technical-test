package api.itau.test.features.transaction.validations;

import api.itau.test.exceptionHandler.InvalidTransaction.InvalidTransactionException;
import api.itau.test.features.transaction.dto.CreateTransactionDto;

public class operationTypeExists implements TransactionValidator{
    @Override
    public void validate(CreateTransactionDto data) {
        if (data.operationType() == null){
            throw new InvalidTransactionException("The operation type is mandatory");
        }
    }
}
