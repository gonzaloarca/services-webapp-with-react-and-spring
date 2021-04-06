package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

//TODO: ver de separar en Controllers más especificos
@Controller
public class MainController {

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobContractService jobContractService;

    @RequestMapping("/")
    public ModelAndView home(@ModelAttribute("searchForm") SearchForm form) {
        final ModelAndView mav = new ModelAndView("index");
        Map<JobPost, List<String>> jobCards = jobPostService.findAllJobCardsWithData();
        mav.addObject("jobCards", jobCards);
        mav.addObject("zones", JobPost.Zone.values());
        return mav;
    }

    @RequestMapping("/create-job-post")
    public ModelAndView createJobPost() {
        final ModelAndView mav = new ModelAndView("createJobPost");
        return mav;
    }

    @RequestMapping("/job/{postId}")
    public ModelAndView jobPostDetails(@PathVariable("postId") final long id) {
        final ModelAndView mav = new ModelAndView("jobPostDetails");
        JobPost jobPost = jobPostService.findById(id).orElseThrow(JobPostNotFoundException::new);
        mav.addObject("jobPost", jobPost);
        mav.addObject("packages", jobPackageService.findByPostIdWithPrice(id));
        mav.addObject("contractsCompleted",
                jobContractService.findContractsQuantityByProId(jobPost.getUser().getId()));
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
