package exceptions;

public class DeleteFailException extends RuntimeException{

    private static final String MESSAGE = "Delete action failed";

    public DeleteFailException() {
        super(MESSAGE);
    }
}
