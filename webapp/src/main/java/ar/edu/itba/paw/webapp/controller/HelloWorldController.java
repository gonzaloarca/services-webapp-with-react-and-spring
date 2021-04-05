package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.ContractForm;
import ar.edu.itba.paw.webapp.validation.ImageValidator;
import ar.edu.itba.paw.webapp.exceptions.JobPostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

import java.util.Map;

@Controller
public class HelloWorldController {

    @Autowired
    private ImageValidator imageValidator;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobContractService jobContractService;

    @RequestMapping("/")
    public ModelAndView home() {
        final ModelAndView mav = new ModelAndView("index");
        Map<JobPost, JobPackage> jobCards = jobPostService.findAllWithCheapierPackage();
        mav.addObject("jobCards", jobCards);
        return mav;
    }

    @RequestMapping("/create-job-post")
    public ModelAndView createJobPost() {
        final ModelAndView mav = new ModelAndView("createJobPost");
        return mav;
    }

    @RequestMapping("/job/{postId}")
    public ModelAndView jobPostDetails(@PathVariable("postId") final long id) {
        final ModelAndView mav = new ModelAndView("jobPostDetails");
        mav.addObject("jobPost", jobPostService.findById(id).orElseThrow(JobPostNotFoundException::new));
        mav.addObject("packages", jobPackageService.findByPostId(id).orElseThrow(JobPostNotFoundException::new));
        return mav;
    }

    @RequestMapping(path = "/contract/package/{packId}", method = RequestMethod.GET)
    public ModelAndView createContract(@PathVariable("packId") final long packId,
                @ModelAttribute("contractForm") final ContractForm form) {

        final ModelAndView mav = new ModelAndView("createContract");

        Optional<JobPackage> jobPackage = jobPackageService.findById(packId);
        Optional<JobPost> jobPost;
        User professional;
        if(jobPackage.isPresent()) {
            jobPost = jobPostService.findById(jobPackage.get().getPostId());
            if(jobPost.isPresent()){
                professional = jobPost.get().getUser();

                mav.addObject("packId", packId);
                mav.addObject("postImage", "/resources/images/worker-placeholder.jpg");
                //TODO: JobType a String
                mav.addObject("jobType", "Techista");
                mav.addObject("jobTitle", jobPost.get().getTitle());
                mav.addObject("packTitle", jobPackage.get().getTitle());
                //TODO: Zone[] a String
                mav.addObject("jobZone", "Belgrano, Colegiales");
                mav.addObject("proName", professional.getUsername());
                mav.addObject("jobHours", jobPost.get().getAvailableHours());
                //TODO: price +  RateType a String
                //String aux = "$" + jobPackage.get().getPrice() + "/" + stringDispatcher.getRateType(jobPackage.get().getRateType());
                mav.addObject("packPrice", "$");

            } else {
                //TODO: PostID not found
                System.out.println("Post Not Present");
            }
        } else {
            //TODO: 404
            System.out.println("Packgage Not Present");
        }

        return mav;
    }

    @RequestMapping(path = "/contract/package/{packId}", method = RequestMethod.POST)
    public ModelAndView submitContract(@PathVariable("packId") final long packId,
                @Valid @ModelAttribute("contractForm") final ContractForm form, final BindingResult errors){

        //TODO encontrar si hay una mejor forma de validar la imagen:
        imageValidator.validate(form.getImage(), errors);

        if(errors.hasErrors()){
            return createContract(packId, form);
        }

        jobContractService.create(packId, form.getDescription(), form.getEmail(), form.getName(), form.getPhone());
        //TODO: mandar email

        ModelAndView mav = new ModelAndView("redirect:/contract/success");
        return mav;
    }

    @RequestMapping("/contract/success")
    public ModelAndView contractSuccess(){
        return new ModelAndView("contractSubmitted");
    }


//    @RequestMapping("/user/{userId}")
//    public ModelAndView getUser(@PathVariable("userId") final long id) {
//        final ModelAndView mav = new ModelAndView("index");
//        UserOriginal aux = userService.findById(id).orElseThrow(UserNotFoundException::new);
//        mav.addObject("greeting", aux.getName());
//        mav.addObject("password", aux.getPassword());
//        return mav;
//    }
//
//    @RequestMapping(path = {"/create"})//, method = RequestMethod.POST)
//    public ModelAndView registerUser(@RequestParam("username") String username, @RequestParam("password") String password) {
//        final UserOriginal user = userService.register(username, password);
//        return new ModelAndView("redirect:/user/" + user.getId());
//    }

}
