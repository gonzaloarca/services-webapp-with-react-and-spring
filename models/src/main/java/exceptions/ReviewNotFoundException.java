package exceptions;

public class ReviewNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Review not found";

    public ReviewNotFoundException() {
        super(MESSAGE);
    }
}

