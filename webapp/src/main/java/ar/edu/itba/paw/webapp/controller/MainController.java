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

    @Autowired
    private JobPostService jobPostService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("searchForm") SearchForm form, @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
        if (page < 1)
            throw new IllegalArgumentException();
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("jobCards", jobCardService.findAll(page - 1));
        int maxPage = jobPostService.findMaxPage();
        List<Integer> currentPages = paginationService.findCurrentPages(page, maxPage);
        mav.addObject("maxPage", jobPostService.findMaxPage());
        mav.addObject("currentPages", currentPages);
        mav.addObject("categories", Arrays.copyOfRange(JobPost.JobType.values(), 0, 3));
        return mav;
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView search(@Valid @ModelAttribute("searchForm") SearchForm form, final BindingResult errors,
                               final ModelAndView mav, @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {

        if (page < 1)
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
        int maxPage = paginationService.findMaxPageJobPostsSearch(form.getQuery(),
                JobPost.Zone.values()[Integer.parseInt(form.getZone())],
                (form.getCategory() == -1) ? null : JobPost.JobType.values()[form.getCategory()]);
        searchMav.addObject("maxPage", maxPage);
        searchMav.addObject("currentPages", paginationService.findCurrentPages(page, maxPage));
        searchMav.addObject("jobCards", jobCardService.search(form.getQuery(),
                JobPost.Zone.values()[Integer.parseInt(form.getZone())],
                (form.getCategory() == -1) ? null : JobPost.JobType.values()[form.getCategory()], page - 1)
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
        ModelAndView mav = new ModelAndView("categories");
        mav.addObject("categories", JobPost.JobType.values());
        return mav;
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
