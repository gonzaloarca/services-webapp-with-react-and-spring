package exceptions;

import java.util.NoSuchElementException;

public class JobPackageNotFoundException extends NoSuchElementException {

    private static final String MESSAGE = "Job Package Not Found";

    public JobPackageNotFoundException() {
        super(MESSAGE);
    }
}
