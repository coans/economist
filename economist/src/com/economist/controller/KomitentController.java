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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.economist.config.BaseController;
import com.economist.dto.KomitentDTO;
import com.economist.service.KomitentService;
import com.economist.validator.KomitentValidator;


@Controller
@RequestMapping(KomitentController.CONTROLLER)
public class KomitentController extends BaseController {
	
	final static Logger logger = Logger.getLogger(KomitentController.class);
	
	public static final String CONTROLLER = "api/komitents";
	public static final String VIEW_DEFAULT = "komitents";
	private static final String VIEW_NEW = "komitent-new";
	
	@Autowired
	private KomitentService komitentService;
	@Autowired
	private KomitentValidator validator;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("komitents", komitentService.findByAgencija(getUser().getAgencija()));
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		KomitentDTO komitentDTO = new KomitentDTO();
		
		model.addAttribute("komitent", komitentDTO);
		model.addAttribute("action", CONTROLLER + "/create");
		model.addAttribute("title", messageSource.getMessage("dodaj.novog.komitenta", null, request.getLocale()));
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("komitent") KomitentDTO komitentDTO, HttpServletRequest request, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

		komitentDTO.setAgencijaId(getUser().getAgencija().getId());
		validator.validate(komitentDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute("komitent", komitentDTO);
			model.addAttribute("action", CONTROLLER + "/create");
			model.addAttribute("title", messageSource.getMessage("dodaj.novog.komitenta", null, request.getLocale()));
			return VIEW_NEW;
		}
		
		komitentService.save(komitentDTO);
		
		return "redirect:/" + KomitentController.CONTROLLER;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer id, HttpServletRequest request, ModelMap model) {
		KomitentDTO komitentDTO = komitentService.findOne(id);
		
		model.addAttribute("komitent", komitentDTO);
		model.addAttribute("action", CONTROLLER + "/update");
		model.addAttribute("title", messageSource.getMessage("izmijeni.komitenta", null, request.getLocale()));
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("komitent") KomitentDTO komitentDTO, HttpServletRequest request, Errors errors, ModelMap model) {

		komitentDTO.setAgencijaId(getUser().getAgencija().getId());		
		validator.validate(komitentDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute("komitent", komitentDTO);
			model.addAttribute("action", CONTROLLER + "/update");
			model.addAttribute("title", messageSource.getMessage("izmijeni.komitenta", null, request.getLocale()));
			return VIEW_NEW;
		}
		
		komitentService.save(komitentDTO);
		
		return "redirect:/" + KomitentController.CONTROLLER;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value = "id") Integer id) {
		//komitentRepository.delete(id);
		
		return "redirect:/" + KomitentController.CONTROLLER;
	}
	
	@RequestMapping(value = "/usistemupdv/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Boolean usistemupdv(@PathVariable(value = "id") Integer id, HttpServletRequest request, ModelMap model) {
		if (id != null) {
			KomitentDTO komitentDTO = komitentService.findOne(id);
			if (komitentDTO != null) {
				return komitentDTO.getUsistemupdv();
			}			
		}
		
		return Boolean.FALSE;
	}
}
