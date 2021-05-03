package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import ar.edu.itba.paw.models.AnalyticRanking;
import ar.edu.itba.paw.models.JobContractCard;
import exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/my-contracts/professional")
    public ModelAndView myProContracts(Principal principal, @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
        if (page < 1) {
            currentUserControllerLogger.debug("Invalid page {}", page);
            throw new IllegalArgumentException();
        }

        currentUserControllerLogger.debug("Finding user with email: {}",principal.getName());
        long id = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new).getId();
        int maxPage = paginationService.findMaxPageContractsByProId(id);
        currentUserControllerLogger.debug("Findign contract cards for professional {}",id);
        List<JobContractCard> jobContractCards = jobContractService.findJobContractCardsByProId(id, page - 1);
        return new ModelAndView("myContracts")
                .addObject("contractType", 1)
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage)
                .addObject("contractCards",jobContractCards );
    }

    @RequestMapping(value = "/my-contracts/client")
    public ModelAndView myClientContracts(Principal principal, @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
        if (page < 1)
            throw new IllegalArgumentException();

        long id = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new).getId();
        int maxPage = paginationService.findMaxPageContractsByClientId(id);
        currentUserControllerLogger.debug("Findign contract cards for client {}",id);
        List<JobContractCard> jobContractCards =  jobContractService.findJobContractCardsByClientId(id, page - 1);
        return new ModelAndView("myContracts")
                .addObject("contractType", 0)
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage)
                .addObject("contractCards",jobContractCards);
    }

    @RequestMapping("/analytics")
    public ModelAndView analytics(Principal principal,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
        if (page < 1) {
            currentUserControllerLogger.debug("Invalid page {}",page);
            throw new IllegalArgumentException();
        }
        // Principal es distinto de null porque en Auth config este mapping tiene hasRole("PROFESSIONAL")
        currentUserControllerLogger.debug("Finding user with email {}",principal.getName());
        long id = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new).getId();
        int maxPage = paginationService.findMaxPageRelatedJobCards(id);
        ModelAndView mav = new ModelAndView("analytics");
        mav
            .addObject("user", userService.getUserByRoleAndId(1, id))
            .addObject("avgRate", reviewService.findProfessionalAvgRate(id))
            .addObject("totalContractsCompleted", jobContractService.findContractsQuantityByProId(id))
            .addObject("totalReviewsSize", reviewService.findProfessionalReviewsSize(id));
        currentUserControllerLogger.debug("Finding analytics rankings for user {}",id);
        mav
            .addObject("analyticRankings", userService.findUserAnalyticRankings(id));
        currentUserControllerLogger.debug("Finding related job cards for user {}",id);
        mav
            .addObject("jobCards", jobCardService.findRelatedJobCards(id, page - 1))
            .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
            .addObject("maxPage", maxPage);
        return mav;
    }

    @RequestMapping(value = "/rate-contract/{contractId}")
    public ModelAndView rateContract(@PathVariable("contractId") final long id,
                                     @ModelAttribute("reviewForm") ReviewForm reviewForm) {
        currentUserControllerLogger.debug("Finding contract review for contract {}",id);
        if (!reviewService.findContractReview(id).isPresent()) {
            currentUserControllerLogger.debug("Review not found, proceeding to display view");
            final ModelAndView mav = new ModelAndView("rateContract");
            mav.addObject("jobCard", jobCardService.findByPostId(
                    jobContractService.findById(id)
                            .getJobPackage().getPostId()));
            mav.addObject("contractId", id);
            return mav;
        } else {
            currentUserControllerLogger.debug("Review found, returning to my contracts");
            return new ModelAndView("redirect:/my-contracts/client");
        }
    }

    @RequestMapping(value = "/rate-contract/{contractId}", method = RequestMethod.POST)
    public ModelAndView rateContractSubmit(@PathVariable("contractId") final long id,
                                           @Valid @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                           final BindingResult errors) {
        currentUserControllerLogger.debug("Finding contract with id {}",id);
        if (!reviewService.findContractReview(id).isPresent()) {
            if (errors.hasErrors()) {
                currentUserControllerLogger.debug("Errors in review form: {}",errors.getAllErrors().toString());
                return rateContract(id, reviewForm);
            }
            int rateValue = reviewForm.getRateValue();
            String title = reviewForm.getTitle();
            String description = reviewForm.getDescription();
            currentUserControllerLogger.debug("Creating review for contract {} with data: rate value: {}, title: {}, description: {}",id,rateValue,title,description);
            reviewService.create(id, rateValue,title, description);
        }
        return new ModelAndView("redirect:/my-contracts/client");
    }
}
