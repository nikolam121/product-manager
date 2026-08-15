package hr.tis.academy.configuration;

import hr.tis.academy.repository.exception.NoProductFoundException;
import hr.tis.academy.repository.exception.NoStoreFoundException;
import hr.tis.academy.service.exception.InvalidImageParametersException;
import hr.tis.academy.service.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAnyException(Exception exception) {
        String uuid = UUID.randomUUID().toString();
        LOGGER.error("Unhandled exception, reference id '{}'", uuid, exception);

        return ResponseEntity.internalServerError()
                .body(String.format("An unexpected error occurred. Reference ID: %s", uuid));
    }

    @ExceptionHandler(NoProductFoundException.class)
    public ResponseEntity<String> handleNoProductFoundException(NoProductFoundException exception) {
        LOGGER.warn("Product not found: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(NoStoreFoundException.class)
    public ResponseEntity<String> handleNoStoreFoundException(NoStoreFoundException exception) {
        LOGGER.warn("Store not found: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception) {
        LOGGER.warn("Resource not found: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidImageParametersException.class)
    public ResponseEntity<String> handleInvalidImageParametersException(InvalidImageParametersException exception) {
        LOGGER.warn("Invalid image parameters: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
