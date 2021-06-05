package ar.edu.itba.paw.models.exceptions;

public class UpdateFailException extends RuntimeException{

    private static final String MESSAGE = "Update action failed";

    public UpdateFailException() {
        super(MESSAGE);
    }
}
