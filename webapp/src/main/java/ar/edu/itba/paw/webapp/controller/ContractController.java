package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractWithImage;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.form.ChangeContractStateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ContractController {

    private final Logger contractControllerLogger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping(path = "/contract/state/update", method = RequestMethod.POST)
    public ModelAndView updateContractState(@ModelAttribute("changeContractStateForm") ChangeContractStateForm form, HttpServletRequest servletRequest) {
        contractControllerLogger.debug("Updating state in contract {} to {}", form.getId(), form.getNewState());

        jobContractService.changeContractState(form.getId(), JobContract.ContractState.values()[form.getNewState()]);

        JobContractWithImage jobContract = jobContractService.findJobContractWithImage(form.getId());
        JobPackage jobPackage = jobPackageService.findById(jobContract.getJobPackage().getId());
        JobPost jobPost = jobPostService.findById(jobPackage.getPostId());

        //TODO: Indicar a quien se manda? (ojo que depende del estado, ver el metodo de email para entenderlo)
        contractControllerLogger.debug("Sending email updating contract state for package {}, post {} and contract {}", jobPackage.getId(), jobPost.getId(), jobContract.getId());
        mailingService.sendUpdateContractStatusEmail(jobContract, jobPackage, jobPost, localeResolver.resolveLocale(servletRequest));

        return new ModelAndView("redirect:" + form.getReturnURL());
    }

}
