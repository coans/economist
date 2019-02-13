package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.economist.config.BaseController;
import com.economist.dto.PreduzeceDTO;
import com.economist.dto.UserDTO;
import com.economist.service.PreduzeceService;
import com.economist.service.UserService;
import com.economist.validator.UserValidator;


@Controller
@RequestMapping(UserController.CONTROLLER)
public class UserController extends BaseController {
	
	final static Logger logger = Logger.getLogger(UserController.class);
	
	public static final String CONTROLLER = "admin/users";
	public static final String VIEW_DEFAULT = "users";
	private static final String VIEW_NEW = "user-new";
	
	@Autowired
	private UserService userService;
	@Autowired
	private PreduzeceService preduzeceService;
	@Autowired
	private UserValidator validator;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("users", userService.findAll());
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		UserDTO userDTO = new UserDTO();
		
		model.addAttribute("newuser", userDTO);
		model.addAttribute("preduzeca", preduzeceService.findAll());
		model.addAttribute("action", CONTROLLER + "/create");
		model.addAttribute("title", messageSource.getMessage("novi.user", null, request.getLocale()));
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("newuser") UserDTO userDTO, HttpServletRequest request, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {
		validator.validate(userDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute("newuser", userDTO);
			model.addAttribute("preduzeca", preduzeceService.findAll());
			model.addAttribute("action", CONTROLLER + "/create");
			model.addAttribute("title", messageSource.getMessage("novi.user", null, request.getLocale()));
			return VIEW_NEW;
		}
		
		userService.save(userDTO);
		
		return "redirect:/" + UserController.CONTROLLER;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer id, HttpServletRequest request, ModelMap model) {
		UserDTO userDTO = userService.findOne(id);
		
		model.addAttribute("newuser", userDTO);
		model.addAttribute("action", CONTROLLER + "/update");
		model.addAttribute("preduzeca", preduzeceService.findAll());
		model.addAttribute("title", messageSource.getMessage("izmijeni.user", null, request.getLocale()));
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("newuser") UserDTO userDTO, HttpServletRequest request, Errors errors, ModelMap model) {
		validator.validate(userDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute("newuser", userDTO);
			model.addAttribute("action", CONTROLLER + "/update");
			model.addAttribute("preduzeca", preduzeceService.findAll());
			model.addAttribute("title", messageSource.getMessage("izmijeni.user", null, request.getLocale()));
			return VIEW_NEW;
		}
		
		userService.save(userDTO);
		
		return "redirect:/" + UserController.CONTROLLER;
	}
	
	@Override
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(PreduzeceDTO.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				PreduzeceDTO preduzece = new PreduzeceDTO();
				preduzece.setId(Integer.parseInt(id));
				setValue(preduzece);
			}
		});
	}
}
