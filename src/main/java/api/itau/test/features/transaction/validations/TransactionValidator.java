package api.itau.test.features.transaction.validations;

import api.itau.test.features.transaction.dto.CreateTransactionDto;

public interface TransactionValidator {

    void validate(CreateTransactionDto data);
}
