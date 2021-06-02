package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.form.ContractForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.security.Principal;

@RequestMapping("/hire")
@Controller
public class HireController {

    private final Logger hireControllerLogger = LoggerFactory.getLogger(HireController.class);

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private JobPostImageService jobPostImageService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping(path = "/package/{packId}", method = RequestMethod.GET)
    public ModelAndView createContract(@PathVariable("packId") final long packId,
                                       @ModelAttribute("jobPost") final JobPost jobPost,
                                       @ModelAttribute("contractForm") final ContractForm form) {

        final ModelAndView mav = new ModelAndView("createContract");
        hireControllerLogger.debug("Finding images for post {}", jobPost.getId());
        List<Long> imageList = jobPostImageService.getImagesIdsByPostId(jobPost.getId());

        mav.addObject("imageList", imageList);

        return mav;

    }

    @RequestMapping(path = "/package/{packId}", method = RequestMethod.POST)
    public ModelAndView submitContract(@PathVariable("packId") final long packId,
                                       @ModelAttribute("jobPack") final JobPackage jobPack,
                                       @ModelAttribute("jobPost") final JobPost jobPost,
                                       @Valid @ModelAttribute("contractForm") final ContractForm form, final BindingResult errors,
                                       Principal principal, HttpServletRequest servletRequest) {
        if (errors.hasErrors()) {
            hireControllerLogger.debug("Contract form has errors: {}", errors.getAllErrors().toString());
            return createContract(packId, jobPost, form);
        }

        String email = principal.getName();

        if (form.getImage().getSize() == 0) {
            hireControllerLogger.debug("Creating contract fo package {} with data: email:{}, description:{}", packId, email, form.getDescription());
            jobContractService.create(email, packId, form.getDescription(), localeResolver.resolveLocale(servletRequest));
        } else {
            try {
                hireControllerLogger.debug("Creating contract fo package {} with data: email:{}, description:{} with image", packId, email, form.getDescription());
                jobContractService.create(email, packId, form.getDescription(),
                        imageService.create(form.getImage().getBytes(), form.getImage().getContentType()), localeResolver.resolveLocale(servletRequest));
            } catch (IOException e) {
                hireControllerLogger.debug("Error creating contract");
                throw new RuntimeException(e.getMessage());
            }
        }

        return new ModelAndView("redirect:/hire/" + packId + "/success");
    }

    @RequestMapping("/{packId}/success")
    public ModelAndView contractSuccess(@PathVariable final long packId) {
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
