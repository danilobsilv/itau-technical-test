package api.itau.test.exceptionHandler.resourceNotFoundException;

import api.itau.test.exceptionHandler.ErrorTypes;
import java.time.Instant;

public record ResourceNotFoundDto(
        Instant timestamp,
        int httpStatus,
        ErrorTypes errorType,
        String message
) {
}
