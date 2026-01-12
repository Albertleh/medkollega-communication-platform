package at.ac.tuwien.isg.backend.exception;

public class HandledException extends RuntimeException {

    public HandledException(String message) {
        super(message);
    }

    public HandledException() {
    }

    public HandledException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandledException(Exception e) {
        super(e);
    }
}
