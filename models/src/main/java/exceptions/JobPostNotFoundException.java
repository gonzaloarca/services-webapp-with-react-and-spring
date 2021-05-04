package exceptions;

public class JobPostNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Job Post Not Found";

    public JobPostNotFoundException() {
        super(MESSAGE);
    }
}
