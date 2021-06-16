package ar.edu.itba.paw.models.exceptions;

public class ExpiredTokenException extends RuntimeException{

    private static final String MESSAGE = "Token has expired";

    public ExpiredTokenException() {
        super(MESSAGE);
    }
}
