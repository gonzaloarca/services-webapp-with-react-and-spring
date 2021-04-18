package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostImageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.webapp.exceptions.JobPackageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.webapp.form.JobPostForm;
import ar.edu.itba.paw.webapp.form.PackageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class JobPostController {

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobPostImageService jobPostImageService;

    @RequestMapping("/job/{postId}")
    public ModelAndView jobPostDetails(@PathVariable("postId") final long id) {
        final ModelAndView mav = new ModelAndView("jobPostDetails");
        JobPost jobPost = jobPostService.findById(id);
        List<JobPostImage> imageList = jobPostImageService.findByPostId(jobPost.getId());

        mav.addObject("jobPost", jobPost);
        mav.addObject("packages", jobPackageService.findByPostId(id).orElseThrow(JobPackageNotFoundException::new));
        mav.addObject("contractsCompleted",
                jobContractService.findContractsQuantityByProId(jobPost.getUser().getId()));
        mav.addObject("imageList", imageList);

        return mav;
    }

    @RequestMapping(path = "/create-job-post", method = RequestMethod.GET)
    public ModelAndView createJobPost(@ModelAttribute("createJobPostForm") final JobPostForm form) {
        return new ModelAndView("createJobPost").addObject("jobTypes", JobPost.JobType.values())
                .addObject("zoneValues", JobPost.Zone.values());
    }

    @RequestMapping(path = "/create-job-post", method = RequestMethod.POST)
    public ModelAndView submitJobPost(@Valid @ModelAttribute("createJobPostForm") final JobPostForm form,
                                      final BindingResult errors) {
        if (errors.hasErrors()) {
            return createJobPost(form);
        }

        JobPost jobPost = jobPostService.create(form.getEmail(), form.getProfessionalName(), form.getPhone(),
                form.getTitle(), form.getAvailableHours(),
                JobPost.JobType.values()[Integer.parseInt(form.getJobType())],
                Arrays.stream(form.getZones()).mapToObj(z -> JobPost.Zone.values()[z]).collect(Collectors.toList()));
        double price;
        for (PackageForm packageForm : form.getPackages()) {
            price = packageForm.getPrice().isEmpty() ? -1.0 : Double.parseDouble(packageForm.getPrice());
            jobPackageService.create(jobPost.getId(), packageForm.getTitle(), packageForm.getDescription(),
                        price, JobPackage.RateType.values()[packageForm.getRateType()]);
        }

        return new ModelAndView("redirect:/job/" + jobPost.getId());
    }


}
