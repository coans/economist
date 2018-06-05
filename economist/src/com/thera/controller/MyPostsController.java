package com.thera.controller;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thera.config.BaseController;
import com.thera.db.entity.Post;
import com.thera.db.repository.PostRepository;
import com.thera.util.UserPath;
import com.thera.validator.PostValidator;

@Controller
@RequestMapping(UserPath.MY + MyPostsController.CONTROLLER)
public class MyPostsController extends BaseController {
	
	public static final String CONTROLLER = "posts";
	public static final String VIEW_DEFAULT = "posts";
	
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostValidator validator;

	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		Post post = new Post();
		model.addAttribute("postmodel", post);
		model.addAttribute("action", UserPath.MY + CONTROLLER + "/create");
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("postmodel") Post post, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {
		// validate promise
		validator.validate(post, errors);
		if (errors.hasErrors()) {
			return VIEW_DEFAULT;
		}
		
		// save promise
		post.setId(0);
		post.setUser(super.getUser());
		post.setCreated(new Date());
		postRepository.save(post);
		return "redirect:/" + HomeController.CONTROLLER;
	}
	
}
