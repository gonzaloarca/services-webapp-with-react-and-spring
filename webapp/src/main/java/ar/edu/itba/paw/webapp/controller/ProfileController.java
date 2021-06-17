package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.webapp.form.DeleteItemForm;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile/{id}")
public class ProfileController {

    private final Logger profileControllerLogger = LoggerFactory.getLogger(ProfileController.class);

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

    @Autowired
    PaginationService paginationService;

    @ModelAttribute("user")
    private User getUser(@PathVariable("id") final long id) {
        return userService.getUserByRoleAndId(1, id);
    }

    @ModelAttribute("avgRate")
    private Double getAvgRate(@PathVariable("id") final long id) {
        return Math.floor(reviewService.findProfessionalAvgRate(id) * 100) / 100;
    }

    @ModelAttribute("servicesSize")
    private int getServicesSize(@PathVariable("id") final long id) {
        return jobCardService.findSizeByUserId(id);
    }

    @ModelAttribute("totalReviewsSize")
    private int getReviews(@PathVariable("id") final long id) {
        return reviewService.findProfessionalReviewsSize(id);
    }

    @ModelAttribute("totalContractsCompleted")
    private int getTotalContractsCompleted(@PathVariable("id") final long id) {
        return jobContractService.findCompletedContractsQuantityByProId(id);
    }

    @RequestMapping(value = "/services")
    public ModelAndView profileWithServices(@PathVariable("id") final long id,
                                            @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
                                            @ModelAttribute("user") User user,
                                            @ModelAttribute("deleteJobPostForm") DeleteItemForm form) {
        if (page < 1) {
            profileControllerLogger.debug("Invalid page: {}",page);
            throw new IllegalArgumentException();
        }

        profileControllerLogger.debug("Getting auth info for user: {}",user.getEmail());
        UserAuth auth = userService.getAuthInfo(user.getEmail()).orElseThrow(UserNotFoundException::new);
        int maxPage = paginationService.findMaxPageJobPostsByUserId(id);
        return new ModelAndView("profile")
                .addObject("isPro", auth.getRoles().contains(UserAuth.Role.PROFESSIONAL))
                .addObject("withServices", true)
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage)
                .addObject("jobCards", jobCardService.findByUserId(id, page - 1));
    }

    @RequestMapping(value = "/reviews")
    public ModelAndView profileWithReviews(@PathVariable("id") final long id, @RequestParam(value = "page", required = false, defaultValue = "1") final int page, @ModelAttribute("user") User user) {
        if (page < 1) {
            profileControllerLogger.debug("Invalid page: {}",page);
            throw new IllegalArgumentException();
        }

        profileControllerLogger.debug("Getting auth info for user: {}",user.getEmail());
        UserAuth auth = userService.getAuthInfo(user.getEmail()).orElseThrow(UserNotFoundException::new);
        int maxPage = paginationService.findMaxPageReviewsByUserId(id);
        return new ModelAndView("profile")
                .addObject("isPro", auth.getRoles().contains(UserAuth.Role.PROFESSIONAL))
                .addObject("withServices", false)
                .addObject("reviews", reviewService.findProfessionalReviews(id, page - 1))
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage)
                .addObject("reviewsByPoints", reviewService.findProfessionalReviewsByPoints(id));
    }

}
