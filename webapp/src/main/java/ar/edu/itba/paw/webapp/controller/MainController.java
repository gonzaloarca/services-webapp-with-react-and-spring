package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.exceptions.JobPackageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.webapp.utils.JobCard;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

//TODO: ver de separar en Controllers más especificos
@Controller
public class MainController {

    @Autowired
    private JobPostService jobPostService;


    @RequestMapping("/")
    public ModelAndView home(@ModelAttribute("searchForm") SearchForm form) {
        final ModelAndView mav = new ModelAndView("index");
        List<JobCard> jobCards = new ArrayList<>();
        jobPostService.findAll().ifPresent(jobPosts -> jobPosts.forEach(jobPost -> {
            JobPackage min = jobPostService.findCheapestPackage(jobPost.getId());
            jobCards.add(new JobCard(
                    jobPost, min.getRateType(), min.getPrice(),
                    jobContractService.findContractsQuantityByProId(jobPost.getUser().getId())
            ));
        }));

        mav.addObject("jobCards", jobCards);
        mav.addObject("zones", JobPost.Zone.values());
        return mav;
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView searchJobPosts(@RequestParam() final int zone, @RequestParam() final String query,
                                       @ModelAttribute("searchForm") SearchForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("redirect:/");
        }

        return null;
    }

}
