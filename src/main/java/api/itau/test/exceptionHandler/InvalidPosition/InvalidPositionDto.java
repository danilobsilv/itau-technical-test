package api.itau.test.exceptionHandler.InvalidPosition;

import api.itau.test.exceptionHandler.ErrorTypes;

public record InvalidPositionDto(
        int httpStatus,
        ErrorTypes errorType,
        String message
) {
}
