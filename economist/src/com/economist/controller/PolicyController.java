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
@RequestMapping("/" + PolicyController.CONTROLLER)
public class PolicyController extends BaseController {

	public static final String CONTROLLER = "policies";
	public static final String VIEW_TERMS = "terms";
	public static final String VIEW_PRIVACY = "privacy";
	
	@RequestMapping(value = "terms", method = RequestMethod.GET)
	public String termsView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		return VIEW_TERMS;
	}
	
	@RequestMapping(value = "privacy", method = RequestMethod.GET)
	public String privacyView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		return VIEW_PRIVACY;
	}
}
