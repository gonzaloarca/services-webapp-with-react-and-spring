package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.LoginForm;
import ar.edu.itba.paw.webapp.form.RecoverForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.models.exceptions.UserAlreadyExistsException;
import ar.edu.itba.paw.models.exceptions.UserNotVerifiedException;
import ar.edu.itba.paw.models.exceptions.VerificationTokenExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class AuthenticationController {

    private final Logger authenticationLogger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(@ModelAttribute("registerForm") RegisterForm registerForm) {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerForm(@Valid @ModelAttribute("registerForm") RegisterForm registerForm, BindingResult errors,
                                     HttpServletRequest servletRequest) {
        if (errors.hasErrors()) {
            authenticationLogger.error("Register form has errors: {}",errors.getAllErrors().toString());
            return register(registerForm);
        }
        ByteImage byteImage = null;

        if (registerForm.getAvatar().getSize() != 0) {
            try {
                byteImage = imageService.create(registerForm.getAvatar().getBytes(), registerForm.getAvatar().getContentType());
            } catch (IOException ignored) {
                //Se ignora porque la imagen queda en valor null
            }
        }

        String email = registerForm.getEmail();
        String password = registerForm.getPassword();
        String name = registerForm.getName();
        String phone = registerForm.getPhone();

        try {
            authenticationLogger.debug("Registering user with data: email: {}, password: {}, name: {}, phone: {}, has image:{}",email,password,name,phone,byteImage != null);
            userService.register(email, password, name, phone, byteImage, localeResolver.resolveLocale(servletRequest));
        } catch (UserAlreadyExistsException e) {
            authenticationLogger.error("Register error: email already exists");
            errors.rejectValue("email", "register.existingemail");
            return register(registerForm);
        } catch (UserNotVerifiedException e) {
            authenticationLogger.error("Register error: user not verified");
            return new ModelAndView("tokenViews").addObject("resend", true);
        }

        return new ModelAndView("tokenViews").addObject("send", true);
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(@ModelAttribute("loginForm") LoginForm loginForm) {
        return new ModelAndView("login");
    }

    @RequestMapping("/password_changed")
    public ModelAndView passwordChanged() {
        SecurityContextHolder.clearContext();
        return new ModelAndView("passwordChanged");
    }

    @RequestMapping("/token")
    public ModelAndView verifyToken(@RequestParam("user_id") final long user_id, @RequestParam("token") final String token) {

        ModelAndView mav = new ModelAndView("tokenViews");

        authenticationLogger.debug("Finding user with id: {}",user_id);
        User user = userService.findById(user_id);

        if (user.isVerified()) {
            authenticationLogger.debug("User already verified");
            return new ModelAndView("error/404");
        }
        try {
            authenticationLogger.debug("verifying token: {} for user:{}",token,user.getId());
            verificationTokenService.verifyToken(user, token);
        } catch (VerificationTokenExpiredException e) {
            authenticationLogger.debug("Token expired");
            return mav.addObject("expired", true);
        }

        return mav.addObject("success", true);
    }

    @RequestMapping(value = "/recover", method = RequestMethod.GET)
    public ModelAndView recoverPassword(@ModelAttribute("recoverForm") RecoverForm form) {
        return new ModelAndView("recover");
    }

    @RequestMapping(value = "/recover", method = RequestMethod.POST)
    public ModelAndView recoverPasswordPost(@ModelAttribute("recoverForm") RecoverForm form, BindingResult errors,
                                            HttpServletRequest servletRequest) {
        if (errors.hasErrors()) {
            authenticationLogger.error("Recover form has errors: {}",errors.getAllErrors().toString());
            return recoverPassword(form);
        }

        authenticationLogger.debug("Recovering password for email: {}", form.getEmail());
        userService.recoverUserPassword(form.getEmail(), localeResolver.resolveLocale(servletRequest));

        return new ModelAndView("recover").addObject("confirmed", true);
    }
}
