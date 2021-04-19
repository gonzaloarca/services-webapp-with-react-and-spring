package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.form.LoginForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import ar.edu.itba.paw.webapp.form.SearchForm;
import ar.edu.itba.paw.webapp.utils.JobContractCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
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
    @RequestMapping(value = "/my-contracts")
    public ModelAndView myContracts() {
        final ModelAndView mav = new ModelAndView("myContracts");
        List<JobContractCard> jobContractCards = new ArrayList<>();
//        TODO: OBTENER ID DEL USUARIO ACTUAL
        jobContractService.findByClientId(1).forEach((jobContract ->
                jobContractCards.add(
                        new JobContractCard(jobContract, jobCardService.findByPostId(jobContract.getJobPackage().getPostId()),
                                reviewService.findContractReview(jobContract.getId())))));

        mav.addObject("contractCards", jobContractCards);
        return mav;
    }

    @RequestMapping(value = "/qualify-contract/{contractId}")
    public ModelAndView qualifyContract(@PathVariable("contractId") final long id,
                                        @ModelAttribute("reviewForm") ReviewForm reviewForm) {
        //TODO: VERIFICAR QUE SEA EL CLIENTE Y QUE LA REVIEW NO ESTE COMPLETADA
        final ModelAndView mav = new ModelAndView("qualifyContract");
        mav.addObject("jobCard", jobCardService.findByPostId(
                jobContractService.findById(id)
                        .getJobPackage().getPostId()));
        mav.addObject("contractId", id);
        return mav;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("searchForm") SearchForm form) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("jobCards", jobCardService.findAll());
        mav.addObject("zones", JobPost.Zone.values());
        return mav;
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView search(@Valid @ModelAttribute("searchForm") SearchForm form, final BindingResult errors) {
        final ModelAndView mav = new ModelAndView("search");
        mav.addObject("zones", JobPost.Zone.values());
        mav.addObject("categories", JobPost.JobType.values());
        if (errors.hasErrors())
            return home(form);
        mav.addObject("query", form.getQuery());
        mav.addObject("pickedZone", JobPost.Zone.values()[Integer.parseInt(form.getZone())]);
        mav.addObject("pickedCategory", form.getCategory());
        mav.addObject("jobCards", jobCardService.search(form.getQuery(),
                JobPost.Zone.values()[Integer.parseInt(form.getZone())],
                (form.getCategory() == -1) ? null : JobPost.JobType.values()[form.getCategory()])
        );
        return mav;
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

        if(registerForm.getAvatar().getSize() != 0) {
            try {
                byteImage = imageService.create(registerForm.getAvatar().getBytes(), registerForm.getAvatar().getContentType());
            } catch (IOException ignored) {
                //Se ignora porque la imagen queda en valor null
            }
        }

        userService.register(registerForm.getEmail(), passwordEncoder.encode(registerForm.getPassword()),
                registerForm.getName(), registerForm.getPhone(), Arrays.asList(0, 1), byteImage);

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
