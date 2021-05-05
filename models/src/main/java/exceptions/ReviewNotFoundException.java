package exceptions;

import java.util.NoSuchElementException;

public class ReviewNotFoundException extends NoSuchElementException {

    private static final String MESSAGE = "Review not found";

    public ReviewNotFoundException() {
        super(MESSAGE);
    }
}

