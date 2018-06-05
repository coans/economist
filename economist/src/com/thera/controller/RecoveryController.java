package com.thera.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.thera.config.BaseController;
import com.thera.db.entity.User;
import com.thera.db.repository.UserRepository;
import com.thera.model.ResetPassword;
import com.thera.util.EmailUtil;
import com.thera.validator.ResetPasswordValidator;

@Controller
@RequestMapping("/" + RecoveryController.CONTROLLER)
public class RecoveryController extends BaseController {
	
	public static final String CONTROLLER = "recovery";
	public static final String VIEW_DEFAULT = "recovery";
	public static final String VIEW_RESET = "reset";
	
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ResetPasswordValidator validator;

	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("action", CONTROLLER + "/sendemail");
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "sendemail", method = RequestMethod.POST)
	public String create(@RequestParam("email") String email, HttpServletRequest request, HttpSession session) {
		if (email != null) {
			User user = userRepository.findActiveByEmail(email);
			if (user != null) {
				// save new uuid
		        String uuid = UUID.randomUUID().toString();
		        user.setUuid(uuid);
		        userRepository.save(user);

		        // send reset password email
		        sendResetPassword(email, uuid);
			}
		}
		return "redirect:/" + HomeController.CONTROLLER;
	}


	private void sendResetPassword(String email, String uuid) {
		String url = baseUrl + RecoveryController.CONTROLLER + "/reset?email=" + email + "&id=" + uuid;
		String subject = "Instructions for Resetting Password";
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("url", url);
		emailUtil.sendMail(email, subject, "email-recovery.vm", model);
	}
	
	@RequestMapping(value = "reset", method = RequestMethod.GET)
	public String resetPassword(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "id", required = true) String uuid,
			ModelMap model, HttpServletRequest request, HttpSession session) {
		User user = userRepository.findActiveByEmailAndUuid(email, uuid);
		if (user != null) {
			ResetPassword resetPassword = new ResetPassword(email, uuid);
			model.addAttribute("resetmodel", resetPassword);
			model.addAttribute("action", CONTROLLER + "/doreset");
		}
		return VIEW_RESET;
	}

	@RequestMapping(value = "doreset", method = RequestMethod.POST)
	public String create(@ModelAttribute("resetmodel") ResetPassword resetPassword, Errors errors) {
		validator.validate(resetPassword, errors);
		if (errors.hasErrors()) {
			return VIEW_RESET;
		}
		User user = userRepository.findActiveByEmailAndUuid(resetPassword.getEmail(), resetPassword.getId());
		if (user != null) {
			StandardPasswordEncoder encoder = new StandardPasswordEncoder();
	        String encodedPassword = encoder.encode(resetPassword.getPassword());
	        user.setPassword(encodedPassword);
	        user.setUuid(null);
	        userRepository.save(user);
		}
		return "redirect:/" + LogInController.CONTROLLER;
	}
}
