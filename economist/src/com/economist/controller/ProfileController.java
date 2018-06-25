package com.economist.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

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

import com.economist.config.BaseController;
import com.economist.db.entity.User;
import com.economist.db.repository.UserRepository;
import com.economist.util.UserPath;
import com.economist.validator.UserValidator;

@Controller
@RequestMapping(UserPath.MY + ProfileController.CONTROLLER)
public class ProfileController extends BaseController {
	
	private static final String PASSWORD = "password";
	public static final String CONTROLLER = "profile";
	public static final String VIEW_DEFAULT = "profile";
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserValidator validator;

	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		User user = getUser();
		user.setPassword(PASSWORD);
		model.addAttribute("usermodel", user);
		model.addAttribute("action", UserPath.MY + CONTROLLER + "/update");
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String create(@ModelAttribute("usermodel") User user, Errors errors)
			throws NoSuchAlgorithmException, IOException {
		User sessionUser = getUser();
		user.setId(sessionUser.getId());
		validator.validate(user, errors);
		if (errors.hasErrors()) {
			return VIEW_DEFAULT;
		}
		if (!user.getPassword().equals(PASSWORD)){
			String password = user.getPassword();
			StandardPasswordEncoder encoder = new StandardPasswordEncoder();
			String encodedPassword = encoder.encode(password);
			sessionUser.setPassword(encodedPassword);
		}
		sessionUser.setFirstName(user.getFirstName());
		sessionUser.setLastName(user.getLastName());
		sessionUser.setGender(user.getGender());
        userRepository.save(sessionUser);

        return "redirect:/" + HomeController.CONTROLLER;
	}
}
