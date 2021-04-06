package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

//TODO: ver de separar en Controllers más especificos
@Controller
public class MainController {

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @RequestMapping("/")
    public ModelAndView home() {
        final ModelAndView mav = new ModelAndView("index");
        Map<JobPost, String> jobCards = jobPostService.findAllWithCheapierPackage();
        mav.addObject("jobCards", jobCards);
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
        mav.addObject("jobPost", jobPostService.findById(id).orElseThrow(JobPostNotFoundException::new));
        mav.addObject("packages", jobPackageService.findByPostIdWithPrice(id));
        return mav;
    }

}
