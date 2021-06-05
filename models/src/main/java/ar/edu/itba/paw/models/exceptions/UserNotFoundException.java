package ar.edu.itba.paw.models.exceptions;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {

    private static final String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
