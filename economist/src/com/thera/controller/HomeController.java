package com.thera.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thera.config.BaseController;
import com.thera.db.entity.Post;
import com.thera.db.repository.PostRepository;

@Controller
@RequestMapping("/" + HomeController.CONTROLLER)
public class HomeController extends BaseController {

	public static final String CONTROLLER = "";
	public static final String VIEW_DEFAULT = "home";

	@Autowired
	private PostRepository postRepository;

	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		List<Post> posts = postRepository.findAll();
		model.addAttribute("posts", posts);
		return VIEW_DEFAULT;
	}
	
}
