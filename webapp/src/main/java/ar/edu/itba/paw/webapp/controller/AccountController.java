package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

//@Component
//@Path("/account")
//public class AccountController {

//    private final Logger accountControllerLogger = LoggerFactory.getLogger(AccountController.class);
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ImageService imageService;
//
//    //TODO: Cambiar a data real
//    private String email = "manuelfparma@gmail.com";
//
//    @Path("/details")
//    @GET
//    @Produces(value = {MediaType.APPLICATION_JSON})
//    public Response personalData() {
//        accountControllerLogger.debug("Finding user with email {}", email);
//        User currentUser = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
//        accountControllerLogger.debug("Finding auth info for user with email {}", email);
//        UserAuth auth = userService.getAuthInfo(email).orElseThrow(UserNotFoundException::new);
//
//        UserDto userAnswer = UserDto.fromUser(currentUser);
//        userAnswer.setRoles(auth.getRoles());
//        return Response.ok(userAnswer).build();
//    }
//
//    @Path("/details")
//    @PUT
//    @Consumes(value = {MediaType.APPLICATION_JSON})
//    @Produces(value = {MediaType.APPLICATION_JSON})
//    public Response changePersonalData(final UserDto user) {
//        accountControllerLogger.debug("Finding user with email {}", email);
//        User currentUser = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
//        accountControllerLogger.debug("Finding auth info for user with email {}", email);
//        UserAuth auth = userService.getAuthInfo(email).orElseThrow(UserNotFoundException::new);
//
//        User updatedUser;
//
//        if (user.getAvatar().getSize() != 0) {
//            try {
//                accountControllerLogger.debug("Updating user {} with data: name: {}, phone: {}, with image", user.getId(),
//                        user.getUsername(), user.getPhone());
//                updatedUser = userService.updateUserById(currentUser.getId(), user.getUsername(), user.getPhone(),
//                        imageService.create(user.getAvatar().getBytes(), user.getAvatar().getContentType()));
//            } catch (IOException e) {
//                accountControllerLogger.debug("Error updating user");
//                throw new RuntimeException(e.getMessage());
//            }
//        } else {
//            accountControllerLogger.debug("Updating user {} with data: name: {}, phone: {}", currentUser.getId(),
//                    user.getUsername(), user.getPhone());
//            updatedUser = userService.updateUserById(currentUser.getId(), user.getUsername(), user.getPhone());
//        }
//
//        UserDto userAnswer = UserDto.fromUser(updatedUser);
//        userAnswer.setRoles(auth.getRoles());
//        return Response.ok(userAnswer).build();
//    }

//	@RequestMapping(path = "/details", method = RequestMethod.POST)
//	public ModelAndView changePersonalData(@Valid @ModelAttribute("accountChangeForm") AccountChangeForm form,
//										   final BindingResult errors, Principal principal) {
//
//		//Principal no es null porque para acceder a este mapping tiene que estar logeado segun Auth config
//		accountControllerLogger.debug("Finding user with email {}",principal.getName());
//		User currentUser = userService.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
//		accountControllerLogger.debug("Finding auth info for user with email {}",principal.getName());
//		UserAuth auth = userService.getAuthInfo(principal.getName()).orElseThrow(UserNotFoundException::new);
//		ModelAndView mav = new ModelAndView("myAccountSettings");
//		mav.addObject("isPro", auth.getRoles().contains(UserAuth.Role.PROFESSIONAL));
//
//		if(errors.hasErrors()) {
//			accountControllerLogger.debug("Account change form has errors: {}",errors.getAllErrors().toString());
//			return mav.addObject("user", currentUser);
//		}
//
//		User updatedUser;
//
//		if(form.getAvatar().getSize() != 0){
//			try {
//				accountControllerLogger.debug("Updating user {} with data: name: {}, phone: {}, with image",currentUser.getId(),form.getName(),form.getPhone());
//				updatedUser = userService.updateUserById(currentUser.getId(), form.getName(), form.getPhone(),
//						imageService.create(form.getAvatar().getBytes(), form.getAvatar().getContentType()));
//			} catch (IOException e) {
//				accountControllerLogger.debug("Error updating user");
//				throw new RuntimeException(e.getMessage());
//			}
//		} else {
//			accountControllerLogger.debug("Updating user {} with data: name: {}, phone: {}",currentUser.getId(),form.getName(),form.getPhone());
//			updatedUser = userService.updateUserById(currentUser.getId(), form.getName(), form.getPhone());
//		}
//
//		mav.addObject("user", updatedUser);
//		mav.addObject("success", true);
//
//		return mav;
//	}

//	@RequestMapping(path = "/security", method = RequestMethod.GET)
//	public ModelAndView securityData(@ModelAttribute("passChangeForm") PasswordChangeForm form) {
//		return new ModelAndView("myAccountSecurity");
//	}
//
//	@RequestMapping(path = "/security", method = RequestMethod.POST)
//	public ModelAndView changePass(@Valid @ModelAttribute("passChangeForm") PasswordChangeForm form,
//								   final BindingResult errors, Principal principal) {
//
//
//		//Principal no es null porque para acceder a este mapping tiene que estar logeado segun Auth config
//
//		String email = principal.getName();
//
//		accountControllerLogger.debug("Checking credentials before password change: password {}",form.getCurrentPass());
//		if(!userService.validCredentials(email, form.getCurrentPass())) {
//			accountControllerLogger.debug("Bad credentials");
//			errors.rejectValue("currentPass", "account.settings.security.badPassword");
//		}
//
//		if (errors.hasErrors()) {
//			accountControllerLogger.debug("Password changed form has errors: {}",errors.getAllErrors().toString());
//			return securityData(form);
//		}
//
//		accountControllerLogger.debug("Changing user {} password",email);
//		userService.changeUserPassword(email, form.getNewPass());
//
//		return new ModelAndView("redirect:/password-changed");
//	}

//}
