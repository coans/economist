package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.economist.config.BaseController;
import com.economist.db.entity.Konto;
import com.economist.db.entity.Preduzece;
import com.economist.db.repository.NalogRepository;
import com.economist.db.repository.PreduzeceRepository;
import com.economist.db.repository.UserRepository;
import com.economist.model.SearchBean;
import com.economist.service.KontoService;


@Controller
@RequestMapping(AnalitikaController.CONTROLLER)
public class AnalitikaController extends BaseController {
	
	final static Logger logger = Logger.getLogger(AnalitikaController.class);
	
	public static final String CONTROLLER = "api/analitika";
	public static final String VIEW_DEFAULT = "api/analitika";
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private KontoService kontoService;
	@Autowired
	private NalogRepository nalogRepository;
	@Autowired
	private PreduzeceRepository preduzeceRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new SearchBean());
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("konta", kontoService.findSintetickaKonta(getUser().getAgencija()));
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "generate", method = RequestMethod.POST)
	public String generate(@ModelAttribute("search") SearchBean search, Errors errors, ModelMap model) {
		
		model.addAttribute("search", new SearchBean());
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("konta", kontoService.findSintetickaKonta(getUser().getAgencija()));
		Preduzece p = getUser().getPreduzece();
//		Konto kontoOd = kontoRepository.findOne(search.getKontoOd().getId());
//		Konto kontoDo = kontoRepository.findOne(search.getKontoDo().getId());
//		List<NalogDTO> result = nalogRepository.analitika(p, kontoOd.getSifra(), kontoDo.getSifra(), search.getDatumOd(), search.getDatumDo());
//		model.addAttribute("nalogs", result);
//		setZbirniRed(result, model);
		
		return VIEW_DEFAULT;
	}

	@Override
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(Konto.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				Konto konto = new Konto();
				konto.setId(Integer.parseInt(id));
				setValue(konto);
			}
		});
	}
}
