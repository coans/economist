package com.economist.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.economist.config.BaseController;
import com.economist.db.entity.Post;
import com.economist.model.Item;
import com.economist.util.UserCardUtil;
import com.economist.util.UserPath;

@Controller
@RequestMapping("/" + PolicyController.CONTROLLER)
public class PolicyController extends BaseController {
	//  --add 8080 testotoz.pagekite.me
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/items")
	public String countryCousineView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		Post post = new Post();
		model.addAttribute("postmodel", post);
		model.addAttribute("action", UserPath.MY + CONTROLLER + "/create");
		return "items";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/add")
	public String addItem(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		Item item = new Item("Proizvod 1", "20", "1");
		UserCardUtil.getUserCard().add(item);
		return "redirect:/" + PolicyController.CONTROLLER + "/my-orders";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/my-orders")
	public String myItems(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("items", UserCardUtil.getUserCard());
		
		return "orders";
	}	
	
	@RequestMapping(method = RequestMethod.GET, value = "/confirm")
	public String confirm(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		return "confirm";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/details")
	public String itemDetailsView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		Post post = new Post();
		model.addAttribute("postmodel", post);
		model.addAttribute("action", UserPath.MY + CONTROLLER + "/create");
		return "details";
	}
}
