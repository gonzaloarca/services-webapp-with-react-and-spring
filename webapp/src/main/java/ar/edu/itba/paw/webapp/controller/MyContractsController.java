package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.form.UpdateContractForm;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@RequestMapping("/my-contracts")
@Controller
public class MyContractsController {

    private final Logger myContractsControllerLogger = LoggerFactory.getLogger(MyContractsController.class);

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaginationService paginationService;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping(path = "/{contractType}/{contractState}", method = RequestMethod.GET)
    public ModelAndView getMyContracts(Principal principal,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
                                       HttpServletRequest servletRequest, @PathVariable final String contractType,
                                       @PathVariable final String contractState,
                                       @ModelAttribute("updateContractForm") UpdateContractForm form) {
        if (page < 1)
            throw new IllegalArgumentException();

        long id = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new).getId();
        int maxPage;
        List<JobContractCard> jobContractCards;

        List<JobContract.ContractState> states = jobContractService.getContractStates(contractState);
        Locale locale = localeResolver.resolveLocale(servletRequest);

        if (contractType.equals("professional")) {
            maxPage = paginationService.findMaxPageContractsByProId(id, states);
            jobContractCards = jobContractService
                    .findJobContractCardsByProIdAndSorted(id, states, page - 1, locale);
        } else if (contractType.equals("client")) {
            maxPage = paginationService.findMaxPageContractsByClientId(id, states);
            jobContractCards = jobContractService
                    .findJobContractCardsByClientIdAndSorted(id, states, page - 1, locale);
        } else
            throw new IllegalArgumentException();

        if (!contractState.equals("active") && !contractState.equals("pending") && !contractState.equals("finalized"))
            throw new IllegalArgumentException();

        UserAuth userAuth = userService.getAuthInfo(principal.getName()).orElseThrow(UserNotFoundException::new);

        myContractsControllerLogger.debug("Finding contract cards for professional {}", id);

        return new ModelAndView("myContracts")
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage)
                .addObject("contractCards", jobContractCards)
                .addObject("isPro", userAuth.getRoles().contains(UserAuth.Role.PROFESSIONAL))
                .addObject("contractType", contractType)
                .addObject("contractStateEndpoint", contractState);
    }

    @RequestMapping(path = "/{contractType}/{contractState}/{id}/update", method = RequestMethod.POST)
    public ModelAndView updateContract(@ModelAttribute("updateContractForm") UpdateContractForm form,
                                       HttpServletRequest servletRequest, @PathVariable("id") long id,
                                       @PathVariable String contractType, @PathVariable String contractState,
                                       BindingResult errors, Principal principal) {
        myContractsControllerLogger.debug("Updating state in contract {} to {}", id, form.getNewState());

        if (errors.hasErrors()) {
            return getMyContracts(principal, 1, servletRequest, contractType, contractState, form);
        }

        jobContractService.changeContractScheduledDate(id, form.getNewScheduledDate(), contractType.equals("professional"),
                localeResolver.resolveLocale(servletRequest));
        jobContractService.changeContractState(id, JobContract.ContractState.values()[form.getNewState()]);

        JobContractWithImage jobContract = jobContractService.findJobContractWithImage(id);
        JobPackage jobPackage = jobPackageService.findById(jobContract.getJobPackage().getId());
        JobPost jobPost = jobPostService.findById(jobPackage.getPostId());

        myContractsControllerLogger.debug("Sending email updating contract state for package {}, post {} and contract {}", jobPackage.getId(), jobPost.getId(), jobContract.getId());
        String webpageUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().scheme("http").replacePath(null).build().toUriString();
        mailingService.sendUpdateContractStatusEmail(jobContract, jobPackage, jobPost, localeResolver.resolveLocale(servletRequest),webpageUrl);

        return new ModelAndView("redirect:" + form.getReturnURL());
    }

}
