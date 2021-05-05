package exceptions;

import java.util.NoSuchElementException;

public class JobContractNotFoundException extends NoSuchElementException {

    private static final String MESSAGE = "Job Contract Not Found";

    public JobContractNotFoundException() {
        super(MESSAGE);
    }

}
