package api.itau.test.exceptionHandler;

import api.itau.test.exceptionHandler.InvalidTransaction.InvalidTransactionDto;
import api.itau.test.exceptionHandler.InvalidTransaction.InvalidTransactionException;
import api.itau.test.exceptionHandler.resourceNotFoundException.ResourceNotFoundDto;
import api.itau.test.exceptionHandler.resourceNotFoundException.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<ResourceNotFoundDto> handleResourceNotFound(ResourceNotFoundException exception){
        ResourceNotFoundDto errorDto = new ResourceNotFoundDto(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                ErrorTypes.RESOURCE_NOT_FOUND,
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<InvalidTransactionDto> handleInvalidTransaction(InvalidTransactionException exception){
        InvalidTransactionDto errorDto = new InvalidTransactionDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorTypes.INVALID_TRANSACTION,
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
}
