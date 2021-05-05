package exceptions;

public class VerificationTokenExpiredException extends RuntimeException{

    private static final String MESSAGE = "Token has expired";

    public VerificationTokenExpiredException() {
        super(MESSAGE);
    }
}
