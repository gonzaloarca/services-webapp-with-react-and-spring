package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import ar.edu.itba.paw.webapp.utils.JobContractCard;
import exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/my-contracts")
    public ModelAndView myContracts(Principal principal) {
        final ModelAndView mav = new ModelAndView("myContracts");
        List<JobContractCard> jobContractCards = new ArrayList<>();
        jobContractService.findByClientId(
                //TODO: SERVICE DEVUELVE OPTIONAL?
                userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new).getId()).forEach(
                (jobContract -> jobContractCards.add(
                        new JobContractCard(jobContract, jobCardService.findByPostId(jobContract.getJobPackage().getPostId()),
                                reviewService.findContractReview(jobContract.getId()).orElse(null))))
        );

        mav.addObject("contractCards", jobContractCards);
        return mav;
    }

    @RequestMapping(value = "/qualify-contract/{contractId}")
    public ModelAndView qualifyContract(@PathVariable("contractId") final long id,
                                        @ModelAttribute("reviewForm") ReviewForm reviewForm) {
        String a =jobContractService.findById(id).getClient().getEmail();
        //TODO: VERIFICAR QUE SEA EL CLIENTE DESDE SPRING SECURITY
        if (!reviewService.findContractReview(id).isPresent()) {
            final ModelAndView mav = new ModelAndView("qualifyContract");
            mav.addObject("jobCard", jobCardService.findByPostId(
                    jobContractService.findById(id)
                            .getJobPackage().getPostId()));
            mav.addObject("contractId", id);
            return mav;
        } else return new ModelAndView("redirect:/my-contracts");
    }

    @RequestMapping(value = "/qualify-contract/{contractId}", method = RequestMethod.POST)
    public ModelAndView qualifyContractSubmit(@PathVariable("contractId") final long id,
                                              @Valid @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                              final BindingResult errors) {
        if (!reviewService.findContractReview(id).isPresent()) {
            //TODO: VERIFICAR QUE SEA EL CLIENTE DESDE SPRING SECURITY
            if (errors.hasErrors())
                return qualifyContract(id, reviewForm);

            reviewService.create(id, reviewForm.getRate(), reviewForm.getTitle(), reviewForm.getDescription());
        }
        return new ModelAndView("redirect:/my-contracts");
    }
}
