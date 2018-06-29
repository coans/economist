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
import com.economist.db.entity.VrstaDokumenta;
import com.economist.db.repository.UserRepository;
import com.economist.db.repository.VrstaDokumentaRepository;


@Controller
@RequestMapping(VrstaDokumentaController.CONTROLLER)
public class VrstaDokumentaController extends BaseController {
	
	private static final String TITLE = "Dodaj novu vrstu dokumenta";
	private static final String TITLE_EDIT = "Izmijeni vrstu dokumenta";

	final static Logger logger = Logger.getLogger(VrstaDokumentaController.class);
	
	public static final String CONTROLLER = "vrstadokumentas";
	public static final String VIEW_DEFAULT = "vrstadokumentas";
	private static final String VIEW_NEW = "vrstadokumenta-new";
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private VrstaDokumentaRepository vrstaDokumentaRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("vrstadokumentas", vrstaDokumentaRepository.findByUser(getUser()));
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		VrstaDokumenta vrstaDokumenta = new VrstaDokumenta();
		final int sifra = vrstaDokumentaRepository.findNextSifra(getUser()) == null ? 1 : vrstaDokumentaRepository.findNextSifra(getUser()) + 1;
		
		vrstaDokumenta.setSifra(sifra);
		
		model.addAttribute("vrstadokumenta", vrstaDokumenta);
		model.addAttribute("action", CONTROLLER + "/create");
		model.addAttribute("title", TITLE);
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("vrstadokumenta") VrstaDokumenta vrstaDokumenta, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

		vrstaDokumenta.setUser(getUser());
		
//TODO		validator.validate(food, errors);
		if (errors.hasErrors()) {
			model.addAttribute("vrstadokumenta", vrstaDokumenta);
			model.addAttribute("action", CONTROLLER + "/create");
			model.addAttribute("title", TITLE);
			return VIEW_NEW;
		}
		
		vrstaDokumentaRepository.save(vrstaDokumenta);
		
		return "redirect:/" + VrstaDokumentaController.CONTROLLER;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer vdId, ModelMap model) {
		VrstaDokumenta vrstaDokumenta = vrstaDokumentaRepository.findOne(vdId);
		
		model.addAttribute("vrstadokumenta", vrstaDokumenta);
		model.addAttribute("action", CONTROLLER + "/update");
		model.addAttribute("title", TITLE_EDIT);
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("vrstadokumenta") VrstaDokumenta vrstaDokumenta, Errors errors, ModelMap model) {

		vrstaDokumenta.setUser(getUser());		
//TODO		validator.validate(food, errors);
		
		if (errors.hasErrors()) {
			model.addAttribute("vrstadokumenta", vrstaDokumenta);
			model.addAttribute("action", CONTROLLER + "/update");
			model.addAttribute("title", TITLE_EDIT);
			return VIEW_NEW;
		}
		
		vrstaDokumentaRepository.save(vrstaDokumenta);
		
		return "redirect:/" + VrstaDokumentaController.CONTROLLER;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value = "id") Integer vdId) {
		vrstaDokumentaRepository.delete(vdId);
		
		return "redirect:/" + VrstaDokumentaController.CONTROLLER;
	}
}
