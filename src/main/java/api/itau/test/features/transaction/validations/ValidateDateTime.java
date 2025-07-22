package api.itau.test.features.transaction.validations;

import api.itau.test.exceptionHandler.InvalidTransaction.InvalidTransactionException;
import api.itau.test.features.transaction.dto.CreateTransactionDto;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ValidateDateTime implements TransactionValidator{

    @Override
    public void validate(CreateTransactionDto data) {
        Instant now = Instant.now();

        if (data.transactionDateTime().isAfter(now)){
            throw new InvalidTransactionException("This transaction can't be made in the future");
        }
    }
}
