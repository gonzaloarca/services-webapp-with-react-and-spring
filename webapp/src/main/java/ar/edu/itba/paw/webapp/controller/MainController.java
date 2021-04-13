package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.webapp.form.SearchForm;
import ar.edu.itba.paw.webapp.utils.JobCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO: ver de separar en Controllers más especificos
@Controller
public class MainController {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobContractService jobContractService;

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

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView search(@Valid @ModelAttribute("searchForm") SearchForm form, final BindingResult errors) {
        final ModelAndView mav = new ModelAndView("search");
        mav.addObject("zones", JobPost.Zone.values());
        mav.addObject("categories", JobPost.JobType.values());
        List<JobCard> jobCards = new ArrayList<>();
        if (errors.hasErrors())
            return home(form);
        mav.addObject("query", form.getQuery());
        mav.addObject("pickedZone", JobPost.Zone.values()[Integer.parseInt(form.getZone())]);
        Optional<List<JobPost>> searchPost;
        if (form.getCategory() != null) {
            mav.addObject("pickedCategory", JobPost.JobType.values()[Integer.parseInt(form.getCategory())]);
            searchPost = jobPostService.search(form.getQuery(), JobPost.Zone.values()[Integer.parseInt(form.getZone())],
                    JobPost.JobType.values()[Integer.parseInt(form.getCategory())]);
        } else
            searchPost = jobPostService.search(form.getQuery(), JobPost.Zone.values()[Integer.parseInt(form.getZone())],
                    null);

        if (searchPost.isPresent())
            jobCards = createCards(searchPost.get());
        mav.addObject("jobCards", jobCards);
        return mav;
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
}
