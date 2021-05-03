package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.webapp.form.ContractForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.security.Principal;
import java.util.Locale;

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

    @Autowired
    private JobPostImageService jobPostImageService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobCardService jobCardService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(path = "/package/{packId}", method = RequestMethod.GET)
    public ModelAndView createContract(@PathVariable("packId") final long packId,
                                       @ModelAttribute("jobPost") final JobPost jobPost,
                                       @ModelAttribute("contractForm") final ContractForm form) {

        final ModelAndView mav = new ModelAndView("createContract");
        List<JobPostImage> imageList = jobPostImageService.findImages(jobPost.getId());

        mav.addObject("imageList", imageList);

        return mav;

    }

    @RequestMapping(path = "/package/{packId}", method = RequestMethod.POST)
    public ModelAndView submitContract(@PathVariable("packId") final long packId,
                                       @ModelAttribute("jobPack") final JobPackage jobPack,
                                       @ModelAttribute("jobPost") final JobPost jobPost,
                                       @Valid @ModelAttribute("contractForm") final ContractForm form, final BindingResult errors,
                                       Principal principal) {
        if (errors.hasErrors()) {
            return createContract(packId, jobPost, form);
        }

        String email = principal.getName();
        JobContract jobContract;

        if (form.getImage().getSize() == 0)
            jobContract = jobContractService.create(email, packId, form.getDescription());
        else {
            try {
                jobContract = jobContractService.create(email, packId, form.getDescription(),
                        imageService.create(form.getImage().getBytes(), form.getImage().getContentType()));
            } catch (IOException e) {
                //fixme
                throw new RuntimeException(e.getMessage());
            }
        }

        mailingService.sendContractEmail(jobContract, jobPack, jobPost, LocaleContextHolder.getLocale());

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
