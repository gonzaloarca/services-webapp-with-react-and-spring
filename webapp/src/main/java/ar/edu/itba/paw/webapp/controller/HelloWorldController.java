package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.exceptions.JobPackageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class HelloWorldController {

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @RequestMapping("/")
    public ModelAndView home() {
        final ModelAndView mav = new ModelAndView("index");
        Map<JobPost, JobPackage> jobCards = jobPostService.findAllWithCheapierPackage();
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
        mav.addObject("packages", jobPackageService.findByPostId(id).orElseThrow(JobPackageNotFoundException::new));
        return mav;
    }

//    @RequestMapping("/user/{userId}")
//    public ModelAndView getUser(@PathVariable("userId") final long id) {
//        final ModelAndView mav = new ModelAndView("index");
//        UserOriginal aux = userService.findById(id).orElseThrow(UserNotFoundException::new);
//        mav.addObject("greeting", aux.getName());
//        mav.addObject("password", aux.getPassword());
//        return mav;
//    }
//
//    @RequestMapping(path = {"/create"})//, method = RequestMethod.POST)
//    public ModelAndView registerUser(@RequestParam("username") String username, @RequestParam("password") String password) {
//        final UserOriginal user = userService.register(username, password);
//        return new ModelAndView("redirect:/user/" + user.getId());
//    }

}
