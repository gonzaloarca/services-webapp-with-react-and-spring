package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.form.LoginForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import ar.edu.itba.paw.webapp.form.SearchForm;
import ar.edu.itba.paw.webapp.utils.JobContractCard;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.Principal;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private JobCardService jobCardService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("searchForm") SearchForm form, @RequestParam(value="page", required = false,defaultValue = "1") final int page) {
        if(page < 1)
            throw new IllegalArgumentException();
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("jobCards", jobCardService.findAll(page-1));
        return mav;
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView search(@Valid @ModelAttribute("searchForm") SearchForm form, final BindingResult errors,
                               final ModelAndView mav, @RequestParam(value="page",required = false,defaultValue = "1") final int page) {

        if(page < 1)
            throw new IllegalArgumentException();

        if (errors.hasErrors()) {
            ModelAndView errorMav = new ModelAndView(mav.getViewName());
            errorMav.addObject(form);
            errorMav.addObject("categories", JobPost.JobType.values());
            return errorMav;
        }

        final ModelAndView searchMav = new ModelAndView("search");
        searchMav.addObject("categories", JobPost.JobType.values());
        searchMav.addObject("pickedZone", JobPost.Zone.values()[Integer.parseInt(form.getZone())]);
        searchMav.addObject("jobCards", jobCardService.search(form.getQuery(),
                JobPost.Zone.values()[Integer.parseInt(form.getZone())],
                (form.getCategory() == -1) ? null : JobPost.JobType.values()[form.getCategory()],page-1)
        );
        return searchMav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(@ModelAttribute("registerForm") RegisterForm registerForm) {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerForm(@Valid @ModelAttribute("registerForm") RegisterForm registerForm, BindingResult errors) {
        if (errors.hasErrors())
            return register(registerForm);

        ByteImage byteImage = null;

        if (registerForm.getAvatar().getSize() != 0) {
            try {
                byteImage = imageService.create(registerForm.getAvatar().getBytes(), registerForm.getAvatar().getContentType());
            } catch (IOException ignored) {
                //Se ignora porque la imagen queda en valor null
            }
        }

        try {
            userService.register(registerForm.getEmail(), passwordEncoder.encode(registerForm.getPassword()),
                    registerForm.getName(), registerForm.getPhone(), Arrays.asList(0, 1), byteImage);
        } catch (UserAlreadyExistsException e) {
            errors.rejectValue("email", "register.existingemail");
            return register(registerForm);
        }

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(@ModelAttribute("loginForm") LoginForm loginForm) {
        return new ModelAndView("login");
    }

    @RequestMapping("/categories")
    public ModelAndView categories() {
        return new ModelAndView("categories");
    }

    @RequestMapping("/password_changed")
    public ModelAndView passwordChanged() {
        SecurityContextHolder.clearContext();
        return new ModelAndView("passwordChanged");
    }
}
