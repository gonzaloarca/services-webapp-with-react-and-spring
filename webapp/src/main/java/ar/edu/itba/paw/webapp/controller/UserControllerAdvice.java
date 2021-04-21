package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class UserControllerAdvice {

    @Autowired
    UserService userService;

    @ModelAttribute("currentUser")
    public User currentUser(Principal principal) {
        User currentUser = null;
        if (principal != null) {
            currentUser = userService.findByEmail(principal.getName()).orElse(null);
        }
        return currentUser;
    }

    @ModelAttribute("searchForm")
    public SearchForm searchForm() {
        return new SearchForm();
    }

    @ModelAttribute("zoneValues")
    public JobPost.Zone[] zoneValues(){
        return JobPost.Zone.values();
    }

}
