package api.itau.test.exceptionHandler.InvalidTransaction;

import api.itau.test.exceptionHandler.ErrorTypes;

import java.time.Instant;

public record InvalidTransactionDto(
        Instant timestamp,
        int httpStatus,
        ErrorTypes errorType,
        String message
) {
}
