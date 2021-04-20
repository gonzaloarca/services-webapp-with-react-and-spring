package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.webapp.form.AccountChangeForm;
import ar.edu.itba.paw.webapp.form.PasswordChangeForm;
import exceptions.UserNotFoundException;
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
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserService userService;

	@Autowired
	private ImageService imageService;

	@RequestMapping(path = "/details", method = RequestMethod.GET)
	public ModelAndView personalData(@ModelAttribute("accountChangeForm") AccountChangeForm form,
									 Principal principal) {
		String email = principal.getName();
		User currentUser = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
		UserAuth auth = userService.getAuthInfo(email).orElseThrow(UserNotFoundException::new);
		ModelAndView mav =  new ModelAndView("myAccountSettings");

		form.setName(currentUser.getUsername());
		form.setPhone(currentUser.getPhone());
		mav.addObject("user", currentUser);
		mav.addObject("isPro", auth.getRoles().contains(UserAuth.Role.PROFESSIONAL));

		return	mav;
	}

	@RequestMapping(path = "/details", method = RequestMethod.POST)
	public ModelAndView changePersonalData(@Valid @ModelAttribute("accountChangeForm") AccountChangeForm form,
										   final BindingResult errors, Principal principal) {

		User currentUser = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
		UserAuth auth = userService.getAuthInfo(principal.getName()).orElseThrow(UserNotFoundException::new);
		ModelAndView mav = new ModelAndView("myAccountSettings");
		mav.addObject("isPro", auth.getRoles().contains(UserAuth.Role.PROFESSIONAL));

		if(errors.hasErrors())
			return mav.addObject("user", currentUser);

		User updatedUser;

		if(form.getAvatar().getSize() != 0){
			try {
				updatedUser = userService.updateUserById(currentUser.getId(), form.getName(), form.getPhone(),
						imageService.create(form.getAvatar().getBytes(), form.getAvatar().getContentType()));
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		} else
			updatedUser = userService.updateUserById(currentUser.getId(), form.getName(), form.getPhone());

		mav.addObject("user", updatedUser);
		mav.addObject("success", true);

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
