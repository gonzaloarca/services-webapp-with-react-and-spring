package exceptions;

import java.util.NoSuchElementException;

public class JobPostImageNotFoundException extends NoSuchElementException {

    private static final String MESSAGE = "Job Post Image Not Found";

    public JobPostImageNotFoundException() {
        super(MESSAGE);
    }
}
