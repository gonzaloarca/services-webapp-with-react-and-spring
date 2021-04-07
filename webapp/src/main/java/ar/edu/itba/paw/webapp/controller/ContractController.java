package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.MailingService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.exceptions.JobPackageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.webapp.form.ContractForm;
import ar.edu.itba.paw.webapp.validation.ImageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RequestMapping("/contract")
@Controller
public class ContractController {

    @Autowired
    private ImageValidator imageValidator;

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
                                       @ModelAttribute("jobPost") final JobPost jobPost,
                                       @Valid @ModelAttribute("contractForm") final ContractForm form, final BindingResult errors){

        //TODO encontrar si hay una mejor forma de validar la imagen:
        //imageValidator.validate(form.getImage(), errors);

        if(errors.hasErrors()){
            return createContract(packId, form);
        }

        jobContractService.create(packId, form.getDescription(), form.getEmail(), form.getName(), form.getPhone());

        //TODO: i18n del email
        String proName = jobPost.getUser().getUsername();
        String proEmail = jobPost.getUser().getEmail();
        String subject = form.getName() + " quiere contratar tu servicio";
        mailingService.sendTemplatedHTMLMessage(proEmail, subject, proName, form.getName(), form.getEmail(),
                form.getPhone(), form.getDescription());

        return new ModelAndView("redirect:/contract/package/" + packId + "/success");
    }

    @RequestMapping("/package/{packId}/success")
    public ModelAndView contractSuccess(@PathVariable String packId){
        return new ModelAndView("contractSubmitted");
    }

    @ModelAttribute("jobPack")
    public JobPackage getJobPackage(@PathVariable("packId") final long packId) {
        return jobPackageService.findById(packId).orElseThrow(JobPackageNotFoundException::new);
    }

    @ModelAttribute("jobPost")
    public JobPost getJobPost(@ModelAttribute("jobPack") final JobPackage jobPackage) {
        return jobPostService.findById(jobPackage.getPostId()).orElseThrow(JobPostNotFoundException::new);
    }
}
