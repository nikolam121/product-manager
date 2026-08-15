package hr.tis.academy.service.exception;

public class InvalidImageParametersException extends RuntimeException {
    public InvalidImageParametersException(String message) {
        super(message);
    }
}