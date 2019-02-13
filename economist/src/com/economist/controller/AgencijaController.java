package com.economist.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.economist.config.BaseController;
import com.economist.dto.AgencijaDTO;
import com.economist.service.AgencijaService;
import com.economist.validator.AgencijaValidator;


@Controller
@RequestMapping(AgencijaController.CONTROLLER)
public class AgencijaController extends BaseController {
	
	final static Logger logger = Logger.getLogger(AgencijaController.class);
	
	public static final String CONTROLLER = "admin/agencijas";
	public static final String VIEW_DEFAULT = "agencijas";
	private static final String VIEW_NEW = "agencija-new";
	
	@Autowired
	private AgencijaService agencijaService;
	@Autowired
	private AgencijaValidator validator;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("agencijas", agencijaService.findAllDTO());
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		AgencijaDTO agencijaDTO = new AgencijaDTO();
		
		model.addAttribute("agencija", agencijaDTO);
		model.addAttribute("action", CONTROLLER + "/create");
		model.addAttribute("title", messageSource.getMessage("dodaj.novu.agenciju", null, request.getLocale()));
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("agencija") AgencijaDTO agencijaDTO, HttpServletRequest request, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

		validator.validate(agencijaDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute("agencija", agencijaDTO);
			model.addAttribute("action", CONTROLLER + "/create");
			model.addAttribute("title", messageSource.getMessage("dodaj.novu.agenciju", null, request.getLocale()));
			return VIEW_NEW;
		}
		
		agencijaService.save(agencijaDTO);
		
		return "redirect:/" + AgencijaController.CONTROLLER;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer id, HttpServletRequest request, ModelMap model) {
		AgencijaDTO agencijaDTO = agencijaService.findOneDTO(id);
		
		model.addAttribute("agencija", agencijaDTO);
		model.addAttribute("action", CONTROLLER + "/update");
		model.addAttribute("title", messageSource.getMessage("izmijeni.agenciju", null, request.getLocale()));
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("agencija") AgencijaDTO agencijaDTO, HttpServletRequest request, Errors errors, ModelMap model) {

		validator.validate(agencijaDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute("agencija", agencijaDTO);
			model.addAttribute("action", CONTROLLER + "/update");
			model.addAttribute("title", messageSource.getMessage("izmijeni.agenciju", null, request.getLocale()));
			return VIEW_NEW;
		}
		
		agencijaService.save(agencijaDTO);
		
		return "redirect:/" + AgencijaController.CONTROLLER;
	}
}
