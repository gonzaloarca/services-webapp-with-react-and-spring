package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.webapp.form.PasswordChangeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserService userService;

	@RequestMapping(path = "/details", method = RequestMethod.GET)
	public ModelAndView personalData() {
		final ModelAndView mav = new ModelAndView("myAccountSettings");
		return mav;
	}

	@RequestMapping(path = "/security", method = RequestMethod.GET)
	public ModelAndView securityData(@ModelAttribute("passChangeForm") PasswordChangeForm form) {
		return new ModelAndView("myAccountSecurity");
	}

	@RequestMapping(path = "/security", method = RequestMethod.POST)
	public ModelAndView changePass(@Valid @ModelAttribute("passChangeForm") PasswordChangeForm form,
								   final BindingResult errors, Principal principal) {

		String email = principal.getName();

		if(!userService.validCredentials(email, form.getCurrentPass()))
			errors.rejectValue("currentPass", "account.settings.security.badPassword");

		if (errors.hasErrors()) {
			return securityData(form);
		}

		userService.changeUserPassword(email, form.getNewPass());

		return new ModelAndView("redirect:/password_changed");
	}
}
