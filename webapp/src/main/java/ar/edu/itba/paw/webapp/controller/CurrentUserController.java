package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import ar.edu.itba.paw.models.AnalyticRanking;
import ar.edu.itba.paw.models.JobContractCard;
import exceptions.UserNotFoundException;
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
        if (page < 1)
            throw new IllegalArgumentException();

        long id = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new).getId();
        int maxPage = paginationService.findMaxPageContractsByProId(id);

        return new ModelAndView("myContracts")
                .addObject("contractType", 1)
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage)
                .addObject("contractCards", jobContractService.findJobContractCardsByProId(id, page - 1));
    }

    @RequestMapping(value = "/my-contracts/client")
    public ModelAndView myClientContracts(Principal principal, @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
        if (page < 1)
            throw new IllegalArgumentException();

        long id = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new).getId();
        int maxPage = paginationService.findMaxPageContractsByClientId(id);

        return new ModelAndView("myContracts")
                .addObject("contractType", 0)
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage)
                .addObject("contractCards", jobContractService.findJobContractCardsByClientId(id, page - 1));
    }

    @RequestMapping("/analytics")
    public ModelAndView analytics(Principal principal,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") final int page) {
        if (page < 1)
            throw new IllegalArgumentException();
        long id = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new).getId();
        //TODO: VERIFICAR QUE SEA PROFESSIONAL
        int maxPage = paginationService.findMaxPageRelatedJobCards(id);
        return new ModelAndView("analytics")
                .addObject("user", userService.getUserByRoleAndId(1, id))
                .addObject("avgRate", reviewService.findProfessionalAvgRate(id))
                .addObject("totalContractsCompleted", jobContractService.findContractsQuantityByProId(id))
                .addObject("totalReviewsSize", reviewService.findProfessionalReviewsSize(id))
                .addObject("analyticRankings", userService.findUserAnalyticRankings(id))
                .addObject("jobCards", jobCardService.findRelatedJobCards(id, page - 1))
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage);
    }

    @RequestMapping(value = "/rate-contract/{contractId}")
    public ModelAndView rateContract(@PathVariable("contractId") final long id,
                                     @ModelAttribute("reviewForm") ReviewForm reviewForm) {
        //TODO: VERIFICAR QUE SEA EL CLIENTE DESDE SPRING SECURITY
        if (!reviewService.findContractReview(id).isPresent()) {
            final ModelAndView mav = new ModelAndView("rateContract");
            mav.addObject("jobCard", jobCardService.findByPostId(
                    jobContractService.findById(id)
                            .getJobPackage().getPostId()));
            mav.addObject("contractId", id);
            return mav;
        } else return new ModelAndView("redirect:/my-contracts");
    }

    @RequestMapping(value = "/rate-contract/{contractId}", method = RequestMethod.POST)
    public ModelAndView rateContractSubmit(@PathVariable("contractId") final long id,
                                           @Valid @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                           final BindingResult errors) {
        if (!reviewService.findContractReview(id).isPresent()) {
            //TODO: VERIFICAR QUE SEA EL CLIENTE DESDE SPRING SECURITY
            if (errors.hasErrors())
                return rateContract(id, reviewForm);

            reviewService.create(id, reviewForm.getRateValue(), reviewForm.getTitle(), reviewForm.getDescription());
        }
        return new ModelAndView("redirect:/my-contracts");
    }
}
