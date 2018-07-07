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
import com.economist.db.entity.Komitent;
import com.economist.db.repository.KomitentRepository;
import com.economist.db.repository.UserRepository;


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
	private KomitentRepository komitentRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("komitents", komitentRepository.findByUser(getUser()));
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		Komitent komitent = new Komitent();
		
		model.addAttribute("komitent", komitent);
		model.addAttribute("action", CONTROLLER + "/create");
		model.addAttribute("title", TITLE);
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("komitent") Komitent komitent, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

		komitent.setUser(getUser());
		
//TODO		validator.validate(food, errors);
		if (errors.hasErrors()) {
			model.addAttribute("komitent", komitent);
			model.addAttribute("action", CONTROLLER + "/create");
			model.addAttribute("title", TITLE);
			return VIEW_NEW;
		}
		
		komitentRepository.save(komitent);
		
		return "redirect:/" + KomitentController.CONTROLLER;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer id, ModelMap model) {
		Komitent komitent = komitentRepository.findOne(id);
		
		model.addAttribute("komitent", komitent);
		model.addAttribute("action", CONTROLLER + "/update");
		model.addAttribute("title", TITLE_EDIT);
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("komitent") Komitent komitent, Errors errors, ModelMap model) {

		komitent.setUser(getUser());		
//TODO		validator.validate(food, errors);
		
		if (errors.hasErrors()) {
			model.addAttribute("komitent", komitent);
			model.addAttribute("action", CONTROLLER + "/update");
			model.addAttribute("title", TITLE_EDIT);
			return VIEW_NEW;
		}
		
		komitentRepository.save(komitent);
		
		return "redirect:/" + KomitentController.CONTROLLER;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value = "id") Integer id) {
		komitentRepository.delete(id);
		
		return "redirect:/" + KomitentController.CONTROLLER;
	}
}
