package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.SearchForm;
import ar.edu.itba.paw.webapp.utils.JobCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.util.*;

//TODO: ver de separar en Controllers más especificos
@Controller
public class MainController {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("searchForm") SearchForm form) {
        final ModelAndView mav = new ModelAndView("index");
        List<JobCard> jobCards = new ArrayList<>();
        Optional<List<JobPost>> homePosts = jobPostService.findAll();
        if (homePosts.isPresent()) {
            jobCards = createCards(homePosts.get());
        }

        mav.addObject("jobCards", jobCards);
        mav.addObject("zones", JobPost.Zone.values());
        return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView homeSubmitSearch(@Valid @ModelAttribute("searchForm") final SearchForm form,
                                         final BindingResult errors) {
        if (errors.hasErrors())
            return search("", form.getQuery(), form.getCategory(), form);

        return new ModelAndView("redirect:search?query=" + form.getQuery() + "&zone=" + form.getZone());
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam() final String zone, @RequestParam() final String query,
                               @RequestParam(value = "category", required = false) final String category,
                               @ModelAttribute("searchForm") SearchForm form) {
        form.setQuery(query);
        final ModelAndView mav = new ModelAndView("search");
        mav.addObject("zones", JobPost.Zone.values());
        mav.addObject("categories", JobPost.JobType.values());
        List<JobCard> jobCards = new ArrayList<>();
        if (zone.equals("")) {
            mav.addObject("pickedZone", null);
            return mav;
        }
        Optional<List<JobPost>> searchPost = jobPostService.search(form.getQuery(), JobPost.Zone.values()[Integer.parseInt(zone)]);
        if (searchPost.isPresent()) {
            jobCards = createCards(searchPost.get());
        }
        mav.addObject("jobCards", jobCards);
        mav.addObject("pickedZone", JobPost.Zone.values()[Integer.parseInt(form.getZone())]);
        mav.addObject("query", form.getQuery());
        if (form.getCategory() != null)
            mav.addObject("pickedCategory", JobPost.JobType.values()[Integer.parseInt(form.getCategory())]);
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
