package ar.edu.itba.paw.webapp.dto;

public class ErrorDto {
    private String message;

    public ErrorDto(Exception e) {
        this.message = e.getMessage();
    }

    public ErrorDto() {
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"message\":\"" + message + "\"" +
                '}';
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
