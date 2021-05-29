package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.PaginationService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractCard;
import ar.edu.itba.paw.models.UserAuth;
import exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/{contractType}/{contractState}")
    public ModelAndView getMyContracts(Principal principal,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
                                       @PathVariable final String contractType, @PathVariable final String contractState,
                                       @ModelAttribute("contractStates") List<JobContract.ContractState> states) {
        if (page < 1)
            throw new IllegalArgumentException();

        long id = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new).getId();
        int maxPage;
        List<JobContractCard> jobContractCards;

        if (contractType.equals("professional")) {
            maxPage = paginationService.findMaxPageContractsByProIdAndStates(id, states);
            jobContractCards = jobContractService
                    .findJobContractCardsByProIdAndStates(id, states, page - 1);
        } else if (contractType.equals("client")) {
            maxPage = paginationService.findMaxPageContractsByClientIdAndStates(id, states);
            jobContractCards = jobContractService
                    .findJobContractCardsByClientIdAndStates(id, states, page - 1);
        } else
            throw new IllegalArgumentException();


        UserAuth userAuth = userService.getAuthInfo(principal.getName()).orElseThrow(UserNotFoundException::new);

        myContractsControllerLogger.debug("Finding contract cards for professional {}", id);

        return new ModelAndView("myContracts")
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage)
                .addObject("contractCards", jobContractCards)
                .addObject("isPro", userAuth.getRoles().contains(UserAuth.Role.PROFESSIONAL))
                .addObject("contractType", contractType);
    }

    @ModelAttribute("contractStates")
    List<JobContract.ContractState> getStates(@PathVariable final String contractState) {

        List<JobContract.ContractState> states = new ArrayList<>();

        switch (contractState) {
            case "active":
                states.add(JobContract.ContractState.APPROVED);
                break;
            case "pending":
                states.add(JobContract.ContractState.PENDING_APPROVAL);
                states.add(JobContract.ContractState.PRO_MODIFIED);
                states.add(JobContract.ContractState.CLIENT_MODIFIED);
                break;
            case "finalized":
                states.add(JobContract.ContractState.COMPLETED);
                states.add(JobContract.ContractState.PRO_CANCELLED);
                states.add(JobContract.ContractState.PRO_REJECTED);
                states.add(JobContract.ContractState.CLIENT_CANCELLED);
                states.add(JobContract.ContractState.CLIENT_REJECTED);
                break;
        }

        return states;
    }
}
