package demo.usermanager.auth;

public class NoLoggedUserException extends RuntimeException {
    public NoLoggedUserException(String message) {
        super(message);
    }
}
