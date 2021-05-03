package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.LoginForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.SearchForm;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotVerifiedException;
import exceptions.VerificationTokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private JobCardService jobCardService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private PaginationService paginationService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("searchForm") SearchForm form, @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
        if (page < 1)
            throw new IllegalArgumentException();
        int maxPage = paginationService.findMaxPageJobCards();
        return new ModelAndView("index")
                .addObject("jobCards", jobCardService.findAll(page - 1))
                .addObject("maxPage", maxPage)
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("categories", Arrays.copyOfRange(JobPost.JobType.values(), 0, 3));
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView search(@Valid @ModelAttribute("searchForm") SearchForm form, final BindingResult errors,
                               final ModelAndView mav,
                               @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
        if (page < 1)
            throw new IllegalArgumentException();

        if (errors.hasErrors()) {
            return new ModelAndView(mav.getViewName()).addObject(form)
                    .addObject("categories", JobPost.JobType.values());
        }
        int maxPage = paginationService.findMaxPageJobPostsSearch(form.getQuery(),
                JobPost.Zone.values()[Integer.parseInt(form.getZone())],
                (form.getCategory() == -1) ? null : JobPost.JobType.values()[form.getCategory()]);
        return new ModelAndView("search")
                .addObject("categories", JobPost.JobType.values())
                .addObject("pickedZone", JobPost.Zone.values()[Integer.parseInt(form.getZone())])
                .addObject("maxPage", maxPage)
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("jobCards", jobCardService.search(form.getQuery(),
                        JobPost.Zone.values()[Integer.parseInt(form.getZone())],
                        (form.getCategory() == -1) ? null : JobPost.JobType.values()[form.getCategory()], page - 1)
                );
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
            userService.register(registerForm.getEmail(), registerForm.getPassword(),
                    registerForm.getName(), registerForm.getPhone(), byteImage);
        } catch (UserAlreadyExistsException e) {
            errors.rejectValue("email", "register.existingemail");
            return register(registerForm);
        } catch (UserNotVerifiedException e) {
            return new ModelAndView("tokenViews").addObject("resend", true);
        }

        return new ModelAndView("tokenViews").addObject("send", true);
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(@ModelAttribute("loginForm") LoginForm loginForm) {
        return new ModelAndView("login");
    }

    @RequestMapping("/categories")
    public ModelAndView categories() {
        return new ModelAndView("categories")
                .addObject("categories", JobPost.JobType.values());
    }

    @RequestMapping("/password_changed")
    public ModelAndView passwordChanged() {
        SecurityContextHolder.clearContext();
        return new ModelAndView("passwordChanged");
    }

    @RequestMapping("/token")
    public ModelAndView verifyToken(@RequestParam("user_id") final long user_id, @RequestParam("token") final String token) {

        ModelAndView mav = new ModelAndView("tokenViews");
        User user = userService.findById(user_id);

        if (user.isVerified())
            return new ModelAndView("error/404");

        try {
            verificationTokenService.verifyToken(user, token);
        } catch (VerificationTokenExpiredException e) {
            return mav.addObject("expired", true);
        }

        return mav.addObject("success", true);
    }
}
