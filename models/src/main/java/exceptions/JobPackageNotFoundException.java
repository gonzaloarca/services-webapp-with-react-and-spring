package exceptions;

public class JobPackageNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Job Package Not Found";

    public JobPackageNotFoundException() {
        super(MESSAGE);
    }
}
