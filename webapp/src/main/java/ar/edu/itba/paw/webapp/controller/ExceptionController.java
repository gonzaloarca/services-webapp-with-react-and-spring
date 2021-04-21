package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import exceptions.JobPackageNotFoundException;
import exceptions.JobPostNotFoundException;
import exceptions.ReviewNotFoundException;
import exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Optional;

@ControllerAdvice
public class ExceptionController {

    @Autowired
    private UserService userService;

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(
            {UserNotFoundException.class, JobPostNotFoundException.class, JobPackageNotFoundException.class, NoSuchElementException.class, ReviewNotFoundException.class})
    public ModelAndView notFoundError() {
        ModelAndView mav = new ModelAndView("error/404");
        userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).ifPresent(value -> mav.addObject("currentUser", value));
        return mav;
    }

//    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
//    public ModelAndView defaultErrorHandler() {
//        ModelAndView mav = new ModelAndView("error/default");
//        userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).ifPresent(value -> mav.addObject("currentUser", value));
//        return mav;
//    }

}
