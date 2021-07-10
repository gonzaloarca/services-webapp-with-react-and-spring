package ar.edu.itba.paw.webapp.oldcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class CurrentUserController {

    private final Logger currentUserControllerLogger = LoggerFactory.getLogger(CurrentUserController.class);

    @Autowired
    private JobCardService jobCardService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PaginationService paginationService;

    @RequestMapping("/analytics")
    public ModelAndView analytics(Principal principal,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
        if (page < 1) {
            currentUserControllerLogger.debug("Invalid page {}", page);
            throw new IllegalArgumentException();
        }
        // Principal es distinto de null porque en Auth config este mapping tiene hasRole("PROFESSIONAL")
        currentUserControllerLogger.debug("Finding user with email {}", principal.getName());
        long id = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new).getId();
        int maxPage = paginationService.findMaxPageRelatedJobCards(id);
        ModelAndView mav = new ModelAndView("analytics");
        mav
                .addObject("user", userService.getUserByRoleAndId(1, id))
                .addObject("avgRate", Math.floor(reviewService.findProfessionalAvgRate(id) * 100) / 100)
                .addObject("totalContractsCompleted", jobContractService.findCompletedContractsQuantityByProId(id))
                .addObject("totalReviewsSize", reviewService.findReviewsSizeByProId(id));
        currentUserControllerLogger.debug("Finding analytics rankings for user {}", id);
        mav
                .addObject("analyticRankings", userService.findUserAnalyticRankings(id));
        currentUserControllerLogger.debug("Finding related job cards for user {}", id);
        mav
                .addObject("jobCards", jobCardService.findRelatedJobCards(id, page - 1))
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage);
        return mav;
    }
/*
    @RequestMapping(value = "/rate-contract/{contractId}")
    public ModelAndView rateContract(@PathVariable("contractId") final long id,
                                     @ModelAttribute("reviewForm") ReviewForm reviewForm) {
        currentUserControllerLogger.debug("Finding contract review for contract {}", id);
        if (!reviewService.findContractReview(id).isPresent()) {
            currentUserControllerLogger.debug("Review not found, proceeding to display view");
            return new ModelAndView("rateContract")
            .addObject("jobCard", jobCardService.findByPostId(
                    jobContractService.findById(id)
                            .getJobPackage().getPostId()))
            .addObject("contractId", id);
        } else {
            currentUserControllerLogger.debug("Review found, returning to my contracts");
            return new ModelAndView("redirect:/my-contracts/client/finalized");
        }
    }

    @RequestMapping(value = "/rate-contract/{contractId}", method = RequestMethod.POST)
    public ModelAndView rateContractSubmit(@PathVariable("contractId") final long id,
                                           @Valid @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                           final BindingResult errors) {
        currentUserControllerLogger.debug("Finding contract with id {}", id);
        if (!reviewService.findContractReview(id).isPresent()) {
            if (errors.hasErrors()) {
                currentUserControllerLogger.debug("Errors in review form: {}", errors.getAllErrors().toString());
                return rateContract(id, reviewForm);
            }
            int rateValue = reviewForm.getRateValue();
            String title = reviewForm.getTitle();
            String description = reviewForm.getDescription();
            currentUserControllerLogger.debug("Creating review for contract {} with data: rate value: {}, title: {}, description: {}", id, rateValue, title, description);
            reviewService.create(id, rateValue, title, description);
        }
        return new ModelAndView("redirect:/my-contracts/client/finalized");
    }
    */

}
