package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.MailingService;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.form.ContractForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@RequestMapping("/contract")
@Controller
public class ContractController {

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private MailingService mailingService;

    @RequestMapping(path = "/package/{packId}", method = RequestMethod.GET)
    public ModelAndView createContract(@PathVariable("packId") final long packId,
                                       @ModelAttribute("contractForm") final ContractForm form) {

        final ModelAndView mav = new ModelAndView("createContract");

        //TODO: reemplazar por la imagen default o la correspondiente al post
        mav.addObject("postImage", "/resources/images/worker-placeholder.jpg");

        return mav;
    }

    @RequestMapping(path = "/package/{packId}", method = RequestMethod.POST)
    public ModelAndView submitContract(@PathVariable("packId") final long packId,
                                       @ModelAttribute("jobPack") final JobPackage jobPack,
                                       @ModelAttribute("jobPost") final JobPost jobPost,
                                       @Valid @ModelAttribute("contractForm") final ContractForm form, final BindingResult errors,
                                       Principal principal) {
        if (errors.hasErrors()) {
            return createContract(packId, form);
        }

        String email = principal.getName();
        JobContract jobContract;

        if(form.getImage().getSize() == 0)
            jobContract = jobContractService.create(email,packId, form.getDescription());
        else {
            try {
                jobContract = jobContractService.create(email,packId, form.getDescription(), form.getImage().getBytes());
            } catch (IOException e){
                //fixme
                throw new RuntimeException(e.getMessage());
            }
        }

        mailingService.sendContractEmail(jobContract, jobPack, jobPost);

        return new ModelAndView("redirect:/contract/package/" + packId + "/success");
    }

    //TODO: encontrar si se puede realizar sin packId
    @RequestMapping("/package/{packId}/success")
    public ModelAndView contractSuccess(@PathVariable String packId) {
        return new ModelAndView("contractSubmitted");
    }

    @ModelAttribute("jobPack")
    public JobPackage getJobPackage(@PathVariable("packId") final long packId) {
        return jobPackageService.findById(packId);
    }

    @ModelAttribute("jobPost")
    public JobPost getJobPost(@ModelAttribute("jobPack") final JobPackage jobPackage) {
        return jobPostService.findById(jobPackage.getPostId());
    }
}
