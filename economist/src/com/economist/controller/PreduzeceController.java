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
import com.economist.dto.AgencijaDTO;
import com.economist.dto.PreduzeceDTO;
import com.economist.service.AgencijaService;
import com.economist.service.PreduzeceService;
import com.economist.validator.PreduzeceValidator;


@Controller
@RequestMapping(PreduzeceController.CONTROLLER)
public class PreduzeceController extends BaseController {
	
	final static Logger logger = Logger.getLogger(PreduzeceController.class);
	
	public static final String CONTROLLER = "admin/preduzeces";
	public static final String VIEW_DEFAULT = "preduzeces";
	private static final String VIEW_NEW = "preduzece-new";
	
	@Autowired
	private AgencijaService agencijaService;
	@Autowired
	private PreduzeceService preduzeceService;
	@Autowired
	private PreduzeceValidator validator;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("preduzeces", preduzeceService.findAll());
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		PreduzeceDTO preduzeceDTO = new PreduzeceDTO();
		
		model.addAttribute("preduzece", preduzeceDTO);
		model.addAttribute("agencije", agencijaService.findAllDTO());
		model.addAttribute("action", CONTROLLER + "/create");
		model.addAttribute("title", messageSource.getMessage("dodaj.novo.preduzece", null, request.getLocale()));
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("preduzece") PreduzeceDTO preduzeceDTO, HttpServletRequest request, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

		validator.validate(preduzeceDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute("preduzece", preduzeceDTO);
			model.addAttribute("agencije", agencijaService.findAllDTO());
			model.addAttribute("action", CONTROLLER + "/create");
			model.addAttribute("title", messageSource.getMessage("dodaj.novo.preduzece", null, request.getLocale()));
			return VIEW_NEW;
		}
		
		preduzeceService.save(preduzeceDTO);
		
		return "redirect:/" + PreduzeceController.CONTROLLER;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer id, HttpServletRequest request, ModelMap model) {
		PreduzeceDTO preduzeceDTO = preduzeceService.findOne(id);
		
		model.addAttribute("preduzece", preduzeceDTO);
		model.addAttribute("action", CONTROLLER + "/update");
		model.addAttribute("agencije", agencijaService.findAllDTO());
		model.addAttribute("title", messageSource.getMessage("izmijeni.preduzece", null, request.getLocale()));
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("preduzece") PreduzeceDTO preduzeceDTO, HttpServletRequest request, Errors errors, ModelMap model) {

		validator.validate(preduzeceDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute("preduzece", preduzeceDTO);
			model.addAttribute("action", CONTROLLER + "/update");
			model.addAttribute("agencije", agencijaService.findAllDTO());
			model.addAttribute("title", messageSource.getMessage("izmijeni.preduzece", null, request.getLocale()));
			return VIEW_NEW;
		}
		
		preduzeceService.save(preduzeceDTO);
		
		return "redirect:/" + PreduzeceController.CONTROLLER;
	}
	
	@Override
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(AgencijaDTO.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				AgencijaDTO agencija = new AgencijaDTO();
				agencija.setId(Integer.parseInt(id));
				setValue(agencija);
			}
		});
	}
}
