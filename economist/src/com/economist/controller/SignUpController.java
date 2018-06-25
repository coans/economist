package com.economist.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
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

import com.economist.auth.AuthorizationService;
import com.economist.config.BaseController;
import com.economist.db.entity.User;
import com.economist.db.repository.UserRepository;
import com.economist.model.enums.EnumStatus;
import com.economist.util.EmailUtil;
import com.economist.validator.UserValidator;

@Controller
@RequestMapping("/" + SignUpController.CONTROLLER)
public class SignUpController extends BaseController {
	
	public static final String CONTROLLER = "signup";
	public static final String VIEW_DEFAULT = "signup";
	public static final String VIEW_EMAIL_SENT = "signup-email-sent";
	
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserValidator validator;
	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		User user = new User();
		model.addAttribute("usermodel", user);
		model.addAttribute("action", CONTROLLER + "/create");
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("usermodel") User user, Errors errors)
			throws NoSuchAlgorithmException, IOException {
		// this is to prevent setting it from the form
		user.setId(0);
		validator.validate(user, errors);
		if (errors.hasErrors()) {
			return VIEW_DEFAULT;
		}
		user.setCreated(new Date());
		String password = user.getPassword();
		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        String uuid = UUID.randomUUID().toString();
        user.setUuid(uuid);
        user.setStatus(EnumStatus.INACTIVE.toString());
        // if there is the inactive user, update it with new data
        User userDb = userRepository.findInactiveByEmail(user.getEmail());
        if (userDb != null) {
        	user.setId(userDb.getId());
        }
        userRepository.save(user);

        // send registration email
        sendRegistrationEmail(user.getEmail(), uuid);

		return VIEW_EMAIL_SENT;
	}

	private void sendRegistrationEmail(String email, String uuid) {
		String url = baseUrl + SignUpController.CONTROLLER + "/confirm?email=" + email + "&id=" + uuid;
		String subject = "Email Verification";
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("url", url);
		emailUtil.sendMail(email, subject, "registration.vm", model);
	}
	
	@RequestMapping(value = "confirm", method = RequestMethod.GET)
	public String confirmRegistration(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "id", required = true) String uuid,
			HttpServletRequest request, HttpSession session) {
		User user = userRepository.findInactiveByEmailAndUuid(email, uuid);
		if (user != null) {
			user.setStatus(EnumStatus.ACTIVE.toString());
			user.setUuid(null);
			userRepository.save(user);
			authorizationService.loginWithUsername(user.getEmail());
			return "redirect:/" + HomeController.CONTROLLER;
		}
		return "redirect:/" + LogInController.CONTROLLER;
	}

}
