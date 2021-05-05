package exceptions;

public class MailCreationException extends RuntimeException {

    private static final String MESSAGE = "Email Creation Error";

    public MailCreationException() {
        super(MESSAGE);
    }
}
