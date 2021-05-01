package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.webapp.form.EditJobPostForm;
import ar.edu.itba.paw.webapp.form.DeletePackageForm;
import ar.edu.itba.paw.webapp.form.JobPostForm;
import ar.edu.itba.paw.webapp.form.PackageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ImageService imageService;

    @Autowired
    PaginationService paginationService;

    @RequestMapping("/job/{postId}")
    public ModelAndView jobPostDetails(@PathVariable("postId") final long id, @RequestParam(value = "page", required = false, defaultValue = "1") final int page,Principal principal) {
        if (page < 1)
            throw new IllegalArgumentException();
        boolean isOwner = false;
        final ModelAndView mav = new ModelAndView("jobPostDetails");
        JobPost jobPost = jobPostService.findById(id);
        if(principal != null && jobPost.getUser().getEmail().equals(principal.getName()))
            isOwner=true;
        List<JobPostImage> imageList = jobPostImageService.findImages(jobPost.getId());
        //BUSCAR SI HAY UNA MEJOR MANERA
        mav.addObject("isOwner",isOwner);

        mav.addObject("jobPost", jobPost);
        mav.addObject("imageList", imageList);
        mav.addObject("totalContractsCompleted", jobContractService.findContractsQuantityByProId(jobPost.getUser().getId()));
        mav.addObject("packages", jobPackageService.findByPostId(id));
        mav.addObject("contractsCompleted",
                jobContractService.findContractsQuantityByPostId(jobPost.getUser().getId()));
        mav.addObject("avgRate", Math.floor(reviewService.findJobPostAvgRate(id) *100)/100);
        mav.addObject("totalReviewsSize", reviewService.findJobPostReviewsSize(id));
        mav.addObject("reviews", reviewService.findReviewsByPostId(jobPost.getId(), page - 1));
        int maxPage = paginationService.findMaxPageReviewsByPostId(id);
        mav.addObject("currentPages", paginationService.findCurrentPages(page, maxPage));
        mav.addObject("maxPage", maxPage);
        return mav;
    }

    @RequestMapping(value = "/job/{postId}/edit",method = RequestMethod.GET)
    public ModelAndView jobPostDetailsEdit(@PathVariable("postId") final long id,@ModelAttribute("editJobPostForm") final EditJobPostForm form) {
        final ModelAndView mav = new ModelAndView("editJobPost")
                .addObject("jobTypes", JobPost.JobType.values())
                .addObject("zoneValues", JobPost.Zone.values());
        EditJobPostForm jobPostForm = new EditJobPostForm();
        JobPost jobPost = jobPostService.findById(id);
        jobPostForm.setJobType(jobPost.getJobType().ordinal());
            jobPostForm.setAvailableHours(jobPost.getAvailableHours());

        int[] zoneInts = new int[jobPost.getZones().size()];
        List<JobPost.Zone> zonesList = jobPost.getZones();
        for (int i = 0; i< zonesList.size() ; i++) {
            zoneInts[i] = zonesList.get(i).ordinal();
        }
        jobPostForm.setZones(zoneInts);
        jobPostForm.setTitle(jobPost.getTitle());
        mav.addObject("editJobPostForm", jobPostForm);
        mav.addObject("id", id);
        return mav;
    }

    @RequestMapping(path = "/create-job-post", method = RequestMethod.GET)
    public ModelAndView createJobPost(@ModelAttribute("createJobPostForm") final JobPostForm form) {

        return new ModelAndView("createJobPostSteps")
                .addObject("jobTypes", JobPost.JobType.values())
                .addObject("zoneValues", JobPost.Zone.values());
    }

    @RequestMapping(path = "/create-job-post", method = RequestMethod.POST)
    public ModelAndView submitJobPost(@Valid @ModelAttribute("createJobPostForm") final JobPostForm form,
                                      final BindingResult errors, Principal principal) {

        if (errors.hasErrors()) {
            return createJobPost(form);
        }

        String currentUserEmail = principal.getName();
        JobPost jobPost = jobPostService.create(currentUserEmail, form.getTitle(), form.getAvailableHours(),
                form.getJobType(), form.getZones());
        PackageForm packageForm = form.getJobPackage();

        jobPackageService.create(jobPost.getId(), packageForm.getTitle(), packageForm.getDescription(),
                packageForm.getPrice(), packageForm.getRateType());

        List<ByteImage> byteImages = new ArrayList<>();
        for (MultipartFile file : form.getServicePics()) {
            if (!file.isEmpty()) {
                try {
                    byteImages.add(imageService.create(file.getBytes(), file.getContentType()));
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        jobPostImageService.addImages(jobPost.getId(), byteImages);

        return new ModelAndView("redirect:/create-job-post/success?postId=" + jobPost.getId());
    }

    @RequestMapping(path = "job/{postId}/edit", method = RequestMethod.POST)
    public ModelAndView editJobPost(@Valid @ModelAttribute("editJobPostForm") final EditJobPostForm form,
                                      final BindingResult errors ,@PathVariable("postId") final long id) {

        if (errors.hasErrors()) {
            return jobPostDetailsEdit(id,form);
        }
        if(!jobPostService.updateJobPost(id, form.getTitle(), form.getAvailableHours(), form.getJobType(), form.getZones())){
            //FIXME: ERROR AL UPDATEAR
            throw new RuntimeException();
        }

        return new ModelAndView("redirect:/job/" + id);
    }

    @RequestMapping("/create-job-post/success")
    public ModelAndView createSuccess(@RequestParam("postId") final long postId) {

        return new ModelAndView("createJobPostSuccess").addObject("postId", postId);
    }

    @RequestMapping(value = "/job/{postId}/packages", method = RequestMethod.GET)
    public ModelAndView viewPackages(@ModelAttribute("deletePackageForm") DeletePackageForm form,
                                     @PathVariable final long postId) {
        JobPost jobPost = jobPostService.findById(postId);
        List<JobPackage> jobPackages = jobPackageService.findByPostId(postId);

        return new ModelAndView("viewPackages")
                .addObject("jobPost", jobPost)
                .addObject("packages", jobPackages);
    }

    @RequestMapping(path = "/job/{postId}/packages/{packageId}/edit", method = RequestMethod.GET)
    public ModelAndView editPackage(@PathVariable final long postId, @PathVariable final long packageId) {
        JobPackage jobPackage = jobPackageService.findById(packageId);

        return new ModelAndView("editPackage").addObject("package", jobPackage);
    }

    @RequestMapping(path = "/job/{postId}/packages", method = RequestMethod.POST)
    public ModelAndView deletePackage(@ModelAttribute("deletePackageForm") DeletePackageForm form,
                                      @PathVariable final long postId) {
        jobPackageService.deleteJobPackage(form.getId());
        return viewPackages(form, postId);
    }

    @RequestMapping(path = "/job/{postId}/packages/add", method = RequestMethod.GET)
    public ModelAndView addPackage(@ModelAttribute("createPackageForm") PackageForm form,
                                   @PathVariable final long postId) {
        return new ModelAndView("addPackage").addObject("postId", postId);
    }

    @RequestMapping(path = "/job/{postId}/packages/add", method = RequestMethod.POST)
    public ModelAndView submitPackage(@Valid @ModelAttribute("createPackageForm") PackageForm form,
                                      final BindingResult errors,
                                      @PathVariable final long postId) {
        if (errors.hasErrors()) {
            return addPackage(form, postId);
        }

        jobPackageService.create(postId, form.getTitle(), form.getDescription(), form.getPrice(), form.getRateType());

        return new ModelAndView("redirect:/job/" + postId + "/packages/add/success");
    }

    @RequestMapping(path = "/job/{postId}/packages/add/success", method = RequestMethod.GET)
    public ModelAndView addPackageSuccess(@PathVariable final long postId) {
        return new ModelAndView("addPackageSuccess").addObject("postId", postId);
    }


}
