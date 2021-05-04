package exceptions;

public class MismatchedTokensException extends RuntimeException {

    private static final String MESSAGE = "Tokens don't match";

    public MismatchedTokensException() {
        super(MESSAGE);
    }
}
