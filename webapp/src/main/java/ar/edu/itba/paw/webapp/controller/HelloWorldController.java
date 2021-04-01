package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserServiceOriginal;
import ar.edu.itba.paw.models.UserOriginal;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

}
