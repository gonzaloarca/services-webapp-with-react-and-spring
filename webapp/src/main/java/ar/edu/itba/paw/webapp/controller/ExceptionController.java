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

@ControllerAdvice
public class ExceptionController {

    @Autowired
    UserService userService;

    @ModelAttribute("currentUser")
    public User currentUser(Principal principal){
        User currentUser = null;
        if(principal != null){
            currentUser = userService.findByEmail(principal.getName()).orElse(null);
        }
        return currentUser;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(
            {UserNotFoundException.class, JobPostNotFoundException.class, JobPackageNotFoundException.class, NoSuchElementException.class, ReviewNotFoundException.class})
    public ModelAndView notFoundError() {
        return new ModelAndView("error/404").addObject("currentUser",new User(1,"a","a","a",true,true));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler() {
        return new ModelAndView("error/default");
    }

}
