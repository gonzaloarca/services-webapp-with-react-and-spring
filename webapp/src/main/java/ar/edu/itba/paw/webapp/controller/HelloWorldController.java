package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserServiceOriginal;
import ar.edu.itba.paw.webapp.form.ContractForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class HelloWorldController {
    @Autowired
    private UserServiceOriginal userService;

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
        //JobPackage jobPackage = TODO: buscar jobPackage por id
        //JobPost jobPost = TODO: buscar con el postId en el package
        //TODO: agregar datos a mav
        mav.addObject("packId", packId);
        mav.addObject("postImage", "/resources/images/worker-placeholder.jpg");
        return mav;
    }

    @RequestMapping(path = "/contract/package/{packId}", method = RequestMethod.POST)
    public ModelAndView submitContract(@PathVariable("packId") final long packId,
                @Valid @ModelAttribute("contractForm") final ContractForm form, final BindingResult errors){
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
