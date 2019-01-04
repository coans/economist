package com.economist.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.economist.config.BaseController;
import com.economist.db.repository.UserRepository;
import com.economist.dto.KomitentDTO;
import com.economist.service.KomitentService;


@Controller
@RequestMapping(KomitentController.CONTROLLER)
public class KomitentController extends BaseController {
	
	private static final String TITLE = "Dodaj novog komitenta";
	private static final String TITLE_EDIT = "Izmijeni komitenta";

	final static Logger logger = Logger.getLogger(KomitentController.class);
	
	public static final String CONTROLLER = "komitents";
	public static final String VIEW_DEFAULT = "komitents";
	private static final String VIEW_NEW = "komitent-new";
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private KomitentService komitentService;
	
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
		model.addAttribute("title", TITLE);
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("komitent") KomitentDTO komitentDTO, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

		komitentDTO.setAgencijaId(getUser().getAgencija().getId());
		
//TODO		validator.validate(food, errors);
		if (errors.hasErrors()) {
			model.addAttribute("komitent", komitentDTO);
			model.addAttribute("action", CONTROLLER + "/create");
			model.addAttribute("title", TITLE);
			return VIEW_NEW;
		}
		
		komitentService.save(komitentDTO);
		
		return "redirect:/" + KomitentController.CONTROLLER;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer id, ModelMap model) {
		KomitentDTO komitentDTO = komitentService.findOne(id);
		
		model.addAttribute("komitent", komitentDTO);
		model.addAttribute("action", CONTROLLER + "/update");
		model.addAttribute("title", TITLE_EDIT);
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("komitent") KomitentDTO komitentDTO, Errors errors, ModelMap model) {

		komitentDTO.setAgencijaId(getUser().getAgencija().getId());		
//TODO		validator.validate(food, errors);
		
		if (errors.hasErrors()) {
			model.addAttribute("komitent", komitentDTO);
			model.addAttribute("action", CONTROLLER + "/update");
			model.addAttribute("title", TITLE_EDIT);
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
}
