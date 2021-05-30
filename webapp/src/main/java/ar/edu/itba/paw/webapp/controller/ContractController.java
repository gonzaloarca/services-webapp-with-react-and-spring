package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.webapp.form.ChangeContractStateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContractController {

    private final Logger contractControllerLogger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private JobContractService jobContractService;

    @RequestMapping(path = "/contract/state/update", method = RequestMethod.POST)
    public ModelAndView updateContractState(@ModelAttribute("changeContractStateForm") ChangeContractStateForm form) {
        contractControllerLogger.debug("Updating state in contract {} to {}", form.getId(), form.getNewState());

        jobContractService.changeContractState(form.getId(), JobContract.ContractState.values()[form.getNewState()]);

        return new ModelAndView("redirect:" + form.getReturnURL());

    }
}
