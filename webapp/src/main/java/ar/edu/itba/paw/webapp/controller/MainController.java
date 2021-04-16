package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private JobCardService jobCardService;

    @RequestMapping(value = "/profile/services")
    public ModelAndView profileWithServices(){
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("withServices", true);
        return mav;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/profile/reviews")
    public ModelAndView profileWithReviews(){
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("withServices", false);
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

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView searchSubmitSearch(@Valid @ModelAttribute("searchForm") SearchForm form,
                                           final BindingResult errors) {
        if (errors.hasErrors())
            return search("", form.getQuery(), form.getCategory(), form);

        return new ModelAndView("redirect:search?query=" + form.getQuery() + "&zone=" + form.getZone());
    }

    private List<JobCard> createCards(List<JobPost> jobPosts) {
        List<JobCard> jobCards = new ArrayList<>();

        jobPosts.forEach(jobPost -> {
            JobPackage min = jobPostService.findCheapestPackage(jobPost.getId()).orElseThrow(JobPostNotFoundException::new);
            jobCards.add(new JobCard(
                    jobPost, min.getRateType(), min.getPrice(),
                    jobContractService.findContractsQuantityByProId(jobPost.getUser().getId())
            ));
        });
        return jobCards;
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ModelAndView register(@ModelAttribute("registerForm")RegisterForm registerForm){
        return new ModelAndView("register");
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView registerForm(@Valid @ModelAttribute("registerForm")RegisterForm registerForm,BindingResult errors){
        if(errors.hasErrors())
            return register(registerForm);
        userService.register(registerForm.getEmail(),passwordEncoder.encode(registerForm.getPassword()),registerForm.getUsername(),registerForm.getPhone(), Arrays.asList(1));
        return new ModelAndView("redirect:");
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

}
