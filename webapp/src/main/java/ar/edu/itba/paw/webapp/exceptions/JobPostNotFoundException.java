package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "No such post")
public class JobPostNotFoundException extends RuntimeException {
    //TODO: IMPLEMENTAR
}
