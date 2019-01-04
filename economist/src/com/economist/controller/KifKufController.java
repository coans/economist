package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.util.List;
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
import com.economist.db.entity.Komitent;
import com.economist.db.entity.Preduzece;
import com.economist.db.repository.KomitentRepository;
import com.economist.db.repository.KontoRepository;
import com.economist.db.repository.NalogRepository;
import com.economist.db.repository.PreduzeceRepository;
import com.economist.db.repository.UserRepository;
import com.economist.dto.NalogDTO;
import com.economist.model.KifKufSearchBean;


@Controller
@RequestMapping(KifKufController.CONTROLLER)
public class KifKufController extends BaseController {
	
	final static Logger logger = Logger.getLogger(KifKufController.class);
	
	public static final String CONTROLLER = "kif-kuf";
	public static final String KIF = "kif";
	public static final String KUF = "kuf";
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private KontoRepository kontoRepository;
	@Autowired
	private NalogRepository nalogRepository;
	@Autowired
	private PreduzeceRepository preduzeceRepository;
	@Autowired
	private KomitentRepository komitentRepository;
	
	@RequestMapping(value = "kif", method = RequestMethod.GET)
	public String kif(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new KifKufSearchBean());
		model.addAttribute("action", CONTROLLER + "/generate-kif");
		model.addAttribute("komitents", komitentRepository.findByAgencija(getUser().getAgencija()));
		return KIF;
	}
	
	@RequestMapping(value = "generate-kif", method = RequestMethod.POST)
	public String generate(@ModelAttribute("search") KifKufSearchBean search, Errors errors, ModelMap model) {
		
		model.addAttribute("search", search);
		model.addAttribute("action", CONTROLLER + "/generate-kif");
		model.addAttribute("komitents", komitentRepository.findByAgencija(getUser().getAgencija()));
		Preduzece p = preduzeceRepository.findOne(1);//TODO
		Komitent komitent = komitentRepository.findOne(search.getKomitent().getId());
		List<NalogDTO> result = nalogRepository.kif(p, search.getDatumOd(), search.getDatumDo(), komitent);
		model.addAttribute("nalogs", result);
		setZbirniRed(result, model);
		
		return KIF;
	}

	@RequestMapping(value = "kuf", method = RequestMethod.GET)
	public String kuf(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new KifKufSearchBean());
		model.addAttribute("action", CONTROLLER + "/generate-kuf");
		model.addAttribute("komitents", komitentRepository.findByAgencija(getUser().getAgencija()));
		return KUF;
	}
	
	@RequestMapping(value = "generate-kuf", method = RequestMethod.POST)
	public String generateKuf(@ModelAttribute("search") KifKufSearchBean search, Errors errors, ModelMap model) {
		
		model.addAttribute("search", search);
		model.addAttribute("action", CONTROLLER + "/generate-kuf");
		model.addAttribute("komitents", komitentRepository.findByAgencija(getUser().getAgencija()));
		Preduzece p = preduzeceRepository.findOne(1);//TODO
		Komitent komitent = komitentRepository.findOne(search.getKomitent().getId());
		List<NalogDTO> result = nalogRepository.kuf(p, search.getDatumOd(), search.getDatumDo(), komitent);
		model.addAttribute("nalogs", result);
		setZbirniRed(result, model);
		
		return KIF;
	}
	
	@Override
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(Komitent.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				Komitent komitent = new Komitent();
				komitent.setId(Integer.parseInt(id));
				setValue(komitent);
			}
		});
	}
}
