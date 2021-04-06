package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    @RequestMapping(value="/",method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("searchForm") final SearchForm form) {
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

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ModelAndView submitSearch(@Valid @ModelAttribute("searchForm") final SearchForm form,
                                     final BindingResult errors) {
        if(errors.hasErrors()){
            return home(form);
        }

        return new ModelAndView("redirect:/search?query="+form.getQuery()+"&zone="+form.getZone());
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ModelAndView searchJobPosts(@RequestParam("zone") final String zone, @RequestParam("query") final String query,@ModelAttribute("searchForm") final SearchForm form) {
        int zoneId = Integer.parseInt(zone);
        ModelAndView mav = new ModelAndView("index");
        Map<JobPost, List<String>> jobCards = jobPostService.searchWithData(query,zoneId);
        mav.addObject("jobCards", jobCards);
        mav.addObject("zones", JobPost.Zone.values());
        return mav;
    }

}
