package ar.edu.itba.paw.webapp.oldcontrollers;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class NavBarControllerAdvice {

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
    public JobPost.Zone[] zoneValues() {
        return JobPost.Zone.values();
    }

}
