package at.ac.tuwien.isg.backend.exception;


public class UserNotFoundException extends HandledException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Exception e) {
        super(e);
    }
}
