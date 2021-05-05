package exceptions;

public class UserNotVerifiedException extends RuntimeException{

    private static final String MESSAGE = "User not verified";

    public UserNotVerifiedException() {
        super(MESSAGE);
    }
}
