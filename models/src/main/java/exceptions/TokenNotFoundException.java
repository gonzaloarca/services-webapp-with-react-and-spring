package exceptions;

import java.util.NoSuchElementException;

public class TokenNotFoundException extends NoSuchElementException {

    private static final String MESSAGE = "Token not found";

    public TokenNotFoundException() {
        super(MESSAGE);
    }
}
