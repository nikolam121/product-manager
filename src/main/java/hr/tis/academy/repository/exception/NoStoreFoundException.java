package hr.tis.academy.repository.exception;

public class NoStoreFoundException extends RuntimeException{
    public NoStoreFoundException(String message) {
        super(message);
    }
    public NoStoreFoundException(Exception e) {
        super(e);
    }
}
