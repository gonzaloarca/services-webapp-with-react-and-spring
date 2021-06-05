package ar.edu.itba.paw.models.exceptions;

import java.util.NoSuchElementException;

public class JobPostNotFoundException extends NoSuchElementException {

    private static final String MESSAGE = "Job Post Not Found";

    public JobPostNotFoundException() {
        super(MESSAGE);
    }
}
