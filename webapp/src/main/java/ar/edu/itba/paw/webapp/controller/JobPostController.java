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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger jobPostControllerLogger = LoggerFactory.getLogger(JobPostController.class);

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
        if (page < 1) {
            jobPostControllerLogger.debug("Invalid page: {}",page);
            throw new IllegalArgumentException();
        }
        //Chequeo manual del user dado a que tengo que mostrar diferentes campos si es uno u otro
        boolean isOwner = false;
        final ModelAndView mav = new ModelAndView("jobPostDetails");
        jobPostControllerLogger.debug("Finding job post by id: {}",id);
        JobPost jobPost = jobPostService.findByIdWithInactive(id);
        if (principal != null && jobPost.getUser().getEmail().equals(principal.getName())) {
            isOwner = true;
        }
        jobPostControllerLogger.debug("Is owner: {}",isOwner);

        jobPostControllerLogger.debug("Finding images for post: {}",jobPost.getId());
        List<JobPostImage> imageList = jobPostImageService.findImages(jobPost.getId());

        //TODO: BUSCAR SI HAY UNA MEJOR MANERA

        int maxPage = paginationService.findMaxPageReviewsByPostId(id);


        mav.addObject("isOwner", isOwner).addObject("jobPost", jobPost)
                .addObject("imageList", imageList)
                .addObject("totalContractsCompleted",
                        jobContractService.findContractsQuantityByPostId(jobPost.getId()));

        jobPostControllerLogger.debug("Finding packages for post: {}",id);
        mav.addObject("packages", jobPackageService.findByPostId(id))
                .addObject("contractsCompleted",
                        jobContractService.findContractsQuantityByPostId(jobPost.getUser().getId()))
                .addObject("avgRate",
                        Math.floor(reviewService.findJobPostAvgRate(id) * 100) / 100)
                .addObject("totalReviewsSize", reviewService.findJobPostReviewsSize(id));

        jobPostControllerLogger.debug("Finding reviews for post: {}",id);
        mav.addObject("reviews", reviewService.findReviewsByPostId(jobPost.getId(), page - 1))
                .addObject("currentPages", paginationService.findCurrentPages(page, maxPage))
                .addObject("maxPage", maxPage);
        return mav;
    }

    @RequestMapping(value = "/job/{postId}/edit", method = RequestMethod.GET)
    public ModelAndView jobPostDetailsEdit(@PathVariable("postId") final long id, @ModelAttribute("editJobPostForm") final EditJobPostForm form) {
        EditJobPostForm jobPostForm;

        jobPostControllerLogger.debug("Finding job post with id: {}",id);
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
            jobPostControllerLogger.debug("Job post form has errors: {}",errors.getAllErrors().toString());
            return createJobPost(form);
        }

        String currentUserEmail = principal.getName();
        String title = form.getTitle();
        String availableHours = form.getAvailableHours();
        int jobType = form.getJobType();
        int[] zones = form.getZones();
        jobPostControllerLogger.debug("Creating job post with data: email: {}, title: {}, available hours: {}, job type: {}, zones: {}",currentUserEmail,title,availableHours,jobType,zones);
        JobPost jobPost = jobPostService.create(currentUserEmail, title, availableHours, jobType, zones);
        PackageForm packageForm = form.getJobPackage();

        long postId = jobPost.getId();
        String packageTitle = packageForm.getTitle();
        String packageDescription = packageForm.getDescription();
        String price = packageForm.getPrice();
        int rateType = packageForm.getRateType();

        jobPostControllerLogger.debug("Creating job package with data: post id: {}, title: {}, description: {}, price: {}, rate type:{}",postId,packageTitle,packageDescription,price,rateType);
        jobPackageService.create(postId, packageTitle, packageDescription,price, rateType);

        List<ByteImage> byteImages = new ArrayList<>();
        jobPostControllerLogger.debug("Loading job services images");
        for (MultipartFile file : form.getServicePics()) {
            if (!file.isEmpty()) {
                try {
                    byteImages.add(imageService.create(file.getBytes(), file.getContentType()));
                } catch (IOException e) {
                    jobPostControllerLogger.debug("Error loading images");
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        jobPostControllerLogger.debug("Adding {} images to post {}",byteImages.size(),postId);
        jobPostImageService.addImages(postId, byteImages);

        return new ModelAndView("redirect:/create-job-post/success?postId=" + postId);
    }

    @RequestMapping(path = "job/{postId}/edit", method = RequestMethod.POST)
    public ModelAndView editJobPost(@Valid @ModelAttribute("editJobPostForm") final EditJobPostForm form,
                                    final BindingResult errors, @PathVariable("postId") final long id) {

        if (errors.hasErrors()) {
            jobPostControllerLogger.debug("Job post form has errors: {}",errors.getAllErrors().toString());
            return jobPostDetailsEdit(id, form);
        }

        String title = form.getTitle();
        String availableHours = form.getAvailableHours();
        int jobType = form.getJobType();
        int[] zones = form.getZones();

        jobPostControllerLogger.debug("Updating post {} with data: title: {}, available hours: {}, job type: {}, zones: {}",id,title,availableHours,jobType,zones);
        if (!jobPostService.updateJobPost(id, title, availableHours, jobType, zones)) {
            //FIXME: ERROR AL UPDATEAR
            jobPostControllerLogger.debug("Error updating job post {}",id);
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
        jobPostControllerLogger.debug("Finding post {}", postId);
        JobPost jobPost = jobPostService.findById(postId);
        jobPostControllerLogger.debug("finding packages for post {}",postId);
        List<JobPackage> jobPackages = jobPackageService.findByPostId(postId);

        return new ModelAndView("viewPackages")
                .addObject("jobPost", jobPost)
                .addObject("packages", jobPackages);
    }

    @RequestMapping(path = "/job/{postId}/packages/{packageId}/edit", method = RequestMethod.GET)
    public ModelAndView editPackage(@ModelAttribute("editPackageForm") PackageForm form,
                                    @PathVariable final long postId, @PathVariable final long packageId) {
        jobPostControllerLogger.debug("finding package {}",packageId);
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
        if (errors.hasErrors()) {
            jobPostControllerLogger.debug("Package form has errors: {}",errors.getAllErrors().toString());
            return editPackage(form, postId, packageId);
        }

        String title = form.getTitle();
        String description = form.getDescription();
        String price = form.getPrice();
        int rateType = form.getRateType();

        jobPostControllerLogger.debug("Updating package {} with data: title: {}, description: {}, price: {}, rate type: {}",packageId,title,description,price,rateType);
        //FIXME: Error al updatear
        if (!jobPackageService.updateJobPackage(packageId, title, description, price, rateType)) {
            jobPostControllerLogger.debug("Error updating package {}",packageId);
            throw new RuntimeException();
        }

        return new ModelAndView("redirect:/job/" + postId + "/packages");
    }

    @RequestMapping(path = "/job/{postId}/packages", method = RequestMethod.POST)
    public ModelAndView deletePackage(@ModelAttribute("deletePackageForm") DeleteItemForm form,
                                      @PathVariable final long postId) {
        //FIXME: Error al eliminar
        jobPostControllerLogger.debug("Deleting package {}",form.getId());
        if (!jobPackageService.deleteJobPackage(form.getId())) {
            jobPostControllerLogger.debug("Error deleting package {}",form.getId());
            throw new RuntimeException();
        }
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
            jobPostControllerLogger.debug("Package form has errors: {}",errors.getAllErrors().toString());
            return addPackage(form, postId);
        }

        String title = form.getTitle();
        String description = form.getDescription();
        String price = form.getPrice();
        int rateType = form.getRateType();
        jobPostControllerLogger.debug("Creating package for post {} with data: title: {}, description: {}, price: {}, rate type:{}",postId,title,description,price,rateType );
        jobPackageService.create(postId, title, description, price, rateType);

        return new ModelAndView("redirect:/job/" + postId + "/packages/add/success");
    }

    @RequestMapping(path = "/job/{postId}/packages/add/success", method = RequestMethod.GET)
    public ModelAndView addPackageSuccess(@PathVariable final long postId) {
        return new ModelAndView("addPackageSuccess").addObject("postId", postId);
    }


    //TODO: Este comporatmiento se repite en profile delete job post, tal vez sea mejor mergearlos
    @RequestMapping(path = "/job/{postId}/delete")
    public ModelAndView deleteJobPost(@PathVariable("postId") int postId) {
        //FIXME: Excepcion correcta
        jobPostControllerLogger.debug("Deleting post {}",postId);
        if(!jobPostService.deleteJobPost(postId)){
            jobPostControllerLogger.debug("Error deleting post {}" ,postId);
            throw new RuntimeException();
        }
        //TODO: Redireccionar a un success de delete?
        return new ModelAndView("redirect:/");
    }


}
