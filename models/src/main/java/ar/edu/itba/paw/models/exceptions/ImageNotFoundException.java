package ar.edu.itba.paw.models.exceptions;

import java.util.NoSuchElementException;

public class ImageNotFoundException extends NoSuchElementException {

    private static final String MESSAGE = "Image not found";

    public ImageNotFoundException() {
        super(MESSAGE);
    }
}