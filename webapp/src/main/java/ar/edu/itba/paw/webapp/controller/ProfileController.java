package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/profile/{id}")
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    JobCardService jobCardService;

    @Autowired
    JobContractService jobContractService;

    @Autowired
    JobPostService jobPostService;

    @Autowired
    ReviewService reviewService;

    @ModelAttribute("user")
    private User getUser(@PathVariable("id") final long id) {
        return userService.getUserByRoleAndId(1, id);
    }

    @ModelAttribute("avgRate")
    private Double getAvgRate(@PathVariable("id") final long id) {
        return reviewService.findProfessionalAvgRate(id);
    }

    @ModelAttribute("reviews")
    private List<Review> getProfessionalReviews(@PathVariable("id") final long id) {
        return reviewService.findProfessionalReviews(id);
    }

    @ModelAttribute("services")
    private List<JobPost> getServices(@PathVariable("id") final long id) {
        return jobPostService.findByUserId(id);
    }

    @ModelAttribute("totalContractsCompleted")
    private int getTotalContractsCompleted(@PathVariable("id") final long id) {
        return jobContractService.findContractsQuantityByProId(id);
    }

    @RequestMapping(value = "/services")
    public ModelAndView profileWithServices(@PathVariable("id") final long id, @ModelAttribute("user") User user) {
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("withServices", true);
        UserAuth auth = userService.getAuthInfo(user.getEmail()).orElseThrow(UserNotFoundException::new);
        mav.addObject("isPro", auth.getRoles().contains(UserAuth.Role.PROFESSIONAL));
        mav.addObject("jobCards", jobCardService.findByUserIdWithReview(id));
        return mav;
    }

    @RequestMapping(value = "/reviews")
    public ModelAndView profileWithReviews(@PathVariable("id") final long id) {
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("withServices", false);
        mav.addObject("reviewsByPoints", reviewService.findProfessionalReviewsByPoints(id));
        return mav;
    }
}
