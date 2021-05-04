package exceptions;

public class JobContractNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Job Contract Not Found";

    public JobContractNotFoundException() {
        super(MESSAGE);
    }

}
