package ar.edu.itba.paw.models.exceptions;

import java.util.NoSuchElementException;

public class ProfessionalNotFoundException extends NoSuchElementException {
    private static final String MESSAGE = "Professional not found";

    public ProfessionalNotFoundException() {
        super(MESSAGE);
    }
}
