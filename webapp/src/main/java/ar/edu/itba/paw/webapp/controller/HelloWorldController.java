package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserServiceOriginal;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.ContractForm;
import ar.edu.itba.paw.webapp.validation.ImageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class HelloWorldController {
    @Autowired
    private UserServiceOriginal userService;

    @Autowired
    private ImageValidator imageValidator;

    @RequestMapping("/")
    public ModelAndView home() {
        final ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @RequestMapping("/create-job-post")
    public ModelAndView createJobPost() {
        final ModelAndView mav = new ModelAndView("createJobPost");
        return mav;
    }

    @RequestMapping(path = "/contract/package/{packId}", method = RequestMethod.GET)
    public ModelAndView createContract(@PathVariable("packId") final long packId,
                @ModelAttribute("contractForm") final ContractForm form) {

        final ModelAndView mav = new ModelAndView("createContract");

        // TODO: buscar usando los servicios
        JobPackage jobPackage = new JobPackage(packId, "Arreglo de techo", "", 50.0, JobPackage.RateType.HOURLY);
        User pro = new User("pro@gmail.com", "Gustavo", "", "45879621", true);
        JobPost jobPost = new JobPost(pro, "Servicio de techista", "Lunes a Viernes: 9:00 a 18:00 & Sabados: 12:00 a 17:00",
                JobPost.JobType.CARPENTRY, Arrays.asList(JobPost.Zone.BELGRANO, JobPost.Zone.COLEGIALES));

        mav.addObject("packId", packId);
        mav.addObject("postImage", "/resources/images/worker-placeholder.jpg");
        //TODO: JobType a String
        mav.addObject("jobType", "Techista");
        mav.addObject("jobTitle", jobPost.getTitle());
        mav.addObject("packTitle", jobPackage.getTitle());
        //TODO: Zone[] a String
        mav.addObject("jobZone", "Belgrano, Colegiales");
        mav.addObject("proName", pro.getUsername());
        mav.addObject("jobHours", jobPost.getAvailableHours());
        //TODO: price +  RateType a String
        mav.addObject("packPrice", "$" + jobPackage.getPrice() + "/Hora");

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
        //TODO: registrar el contacto con los datos

        ModelAndView mav = new ModelAndView("contractSubmitted"); //TODO: decidir si se redirecciona a otra pagina
        return mav;
    }

/*
    @RequestMapping("/user/{userId}")
    public ModelAndView getUser(@PathVariable("userId") final long id) {
        final ModelAndView mav = new ModelAndView("index");
        UserOriginal aux = userService.findById(id).orElseThrow(UserNotFoundException::new);
        mav.addObject("greeting", aux.getName());
        mav.addObject("password", aux.getPassword());
        return mav;
    }

    @RequestMapping(path = {"/create"})//, method = RequestMethod.POST)
    public ModelAndView registerUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        final UserOriginal user = userService.register(username, password);
        return new ModelAndView("redirect:/user/" + user.getId());
    }
*/
}
