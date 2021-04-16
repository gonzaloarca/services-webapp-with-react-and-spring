package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.exceptions.JobPackageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(
            {UserNotFoundException.class, JobPostNotFoundException.class, JobPackageNotFoundException.class})
    public ModelAndView notFoundError() {
        return new ModelAndView("error/404");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler() {
        return new ModelAndView("error/default");
    }

}
