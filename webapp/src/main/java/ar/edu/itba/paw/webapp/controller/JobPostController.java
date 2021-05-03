package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.webapp.form.DeleteItemForm;
import ar.edu.itba.paw.webapp.form.EditJobPostForm;
import ar.edu.itba.paw.webapp.form.JobPostForm;
import ar.edu.itba.paw.webapp.form.PackageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JobPostController {

    @Autowired
    PaginationService paginationService;
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

    @RequestMapping("/job/{postId}")
    public ModelAndView jobPostDetails(@PathVariable("postId") final long id, @RequestParam(value = "page", required = false, defaultValue = "1") final int page, Principal principal) {
        if (page < 1)
            throw new IllegalArgumentException();
        boolean isOwner = false;
        final ModelAndView mav = new ModelAndView("jobPostDetails");
        JobPost jobPost = jobPostService.findById(id);
        if (principal != null && jobPost.getUser().getEmail().equals(principal.getName()))
            isOwner = true;
        List<JobPostImage> imageList = jobPostImageService.findImages(jobPost.getId());

        //TODO: BUSCAR SI HAY UNA MEJOR MANERA

        int maxPage = paginationService.findMaxPageReviewsByPostId(id);

        mav.addObject("isOwner", isOwner).addObject("jobPost", jobPost)
                .addObject("imageList", imageList)
                .addObject("totalContractsCompleted",
                        jobContractService.findContractsQuantityByProId(jobPost.getUser().getId()))
                .addObject("packages", jobPackageService.findByPostId(id))
                .addObject("contractsCompleted",
                        jobContractService.findContractsQuantityByPostId(jobPost.getUser().getId()))
                .addObject("avgRate",
                        Math.floor(reviewService.findJobPostAvgRate(id) * 100) / 100)
                .addObject("totalReviewsSize", reviewService.findJobPostReviewsSize(id))
                .addObject("reviews", reviewService.findReviewsByPostId(jobPost.getId(), page - 1))
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage);
        return mav;
    }

    @RequestMapping(value = "/job/{postId}/edit", method = RequestMethod.GET)
    public ModelAndView jobPostDetailsEdit(@PathVariable("postId") final long id, @ModelAttribute("editJobPostForm") final EditJobPostForm form) {
        EditJobPostForm jobPostForm;
        JobPost jobPost = jobPostService.findById(id);

        // Si algun campo del form viene en null es porque todavia no hubo errores en validacion, por lo cual cargo el
        // model dentro del form asi la vista levanta los datos correctos
        if (form.getTitle() == null) {
            jobPostForm = new EditJobPostForm();
            jobPostForm.setJobType(jobPost.getJobType().ordinal());
            jobPostForm.setAvailableHours(jobPost.getAvailableHours());
            int[] zoneInts = new int[jobPost.getZones().size()];
            List<JobPost.Zone> zonesList = jobPost.getZones();
            for (int i = 0; i < zonesList.size(); i++) {
                zoneInts[i] = zonesList.get(i).ordinal();
            }
            jobPostForm.setZones(zoneInts);
            jobPostForm.setTitle(jobPost.getTitle());
        } else {
            jobPostForm = form;
        }

        return new ModelAndView("editJobPost").addObject("jobTypes", JobPost.JobType.values())
                .addObject("zoneValues", JobPost.Zone.values())
                .addObject("editJobPostForm", jobPostForm).addObject("id", id);
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
                                    final BindingResult errors, @PathVariable("postId") final long id) {

        if (errors.hasErrors()) {
            return jobPostDetailsEdit(id, form);
        }
        if (!jobPostService.updateJobPost(id, form.getTitle(), form.getAvailableHours(), form.getJobType(), form.getZones())) {
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
    public ModelAndView viewPackages(@ModelAttribute("deletePackageForm") DeleteItemForm form,
                                     @PathVariable final long postId) {
        JobPost jobPost = jobPostService.findById(postId);
        List<JobPackage> jobPackages = jobPackageService.findByPostId(postId);

        return new ModelAndView("viewPackages")
                .addObject("jobPost", jobPost)
                .addObject("packages", jobPackages);
    }

    @RequestMapping(path = "/job/{postId}/packages/{packageId}/edit", method = RequestMethod.GET)
    public ModelAndView editPackage(@ModelAttribute("editPackageForm") PackageForm form,
                                    @PathVariable final long postId, @PathVariable final long packageId) {
        JobPackage jobPackage = jobPackageService.findById(packageId);
        PackageForm editPackageForm;

        // si el form tiene al menos un campo en null, quiere decir que todavia no hubo errores en la validacion.
        // esto se debe a que un input vacio se recibe como string vacio, y no como null
        if (form.getTitle() == null) {
            // por lo tanto, si es null, procedo a cargarle los datos del modelo ya existente
            editPackageForm = new PackageForm();
            editPackageForm.setTitle(jobPackage.getTitle());
            editPackageForm.setDescription(jobPackage.getDescription());
            editPackageForm.setRateType(jobPackage.getRateType().ordinal());
            editPackageForm.setPrice(jobPackage.getPrice().toString());
        } else
            editPackageForm = form;

        return new ModelAndView("editPackage")
                .addObject("editPackageForm", editPackageForm)
                .addObject("existing", true);
    }

    @RequestMapping(path = "/job/{postId}/packages/{packageId}/edit", method = RequestMethod.POST)
    public ModelAndView submitEditPackage(@Valid @ModelAttribute("editPackageForm") PackageForm form, BindingResult errors,
                                          @PathVariable final long postId, @PathVariable final long packageId) {
        if (errors.hasErrors())
            return editPackage(form, postId, packageId);

        //FIXME: Error al updatear
        if (!jobPackageService.updateJobPackage(packageId, form.getTitle(), form.getDescription(), form.getPrice(),
                form.getRateType()))
            throw new RuntimeException();

        return new ModelAndView("redirect:/job/" + postId + "/packages");
    }

    @RequestMapping(path = "/job/{postId}/packages", method = RequestMethod.POST)
    public ModelAndView deletePackage(@ModelAttribute("deletePackageForm") DeleteItemForm form,
                                      @PathVariable final long postId) {
        //FIXME: Error al eliminar
        if (!jobPackageService.deleteJobPackage(form.getId()))
            throw new RuntimeException();
        return viewPackages(form, postId);
    }

    @RequestMapping(path = "/job/{postId}/packages/add", method = RequestMethod.GET)
    public ModelAndView addPackage(@ModelAttribute("editPackageForm") PackageForm form,
                                   @PathVariable final long postId) {
        return new ModelAndView("editPackage")
                .addObject("existing", false)
                .addObject("packageId", null);
    }

    @RequestMapping(path = "/job/{postId}/packages/add", method = RequestMethod.POST)
    public ModelAndView submitPackage(@Valid @ModelAttribute("editPackageForm") PackageForm form,
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


    //TODO: Este comporatmiento se repite en profile delete job post, tal vez sea mejor mergearlos
    @RequestMapping(path = "/job/{postId}/delete")
    public ModelAndView deleteJobPost(@PathVariable("postId") int postId){
        //FIXME: Excepcion correcta
        if(!jobPostService.deleteJobPost(postId)){
            throw new RuntimeException();
        }
        //TODO: Redireccionar a un success de delete?
        return new ModelAndView("redirect:/");
    }


}
