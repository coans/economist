package com.economist.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.economist.config.BaseController;

@Controller
@RequestMapping("/" + LogInController.CONTROLLER)
public class LogInController extends BaseController {

	public static final String CONTROLLER = "login";
	public static final String VIEW_DEFAULT = "login";

	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		return VIEW_DEFAULT;
	}
}
