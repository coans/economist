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
import com.economist.dto.KomitentDTO;
import com.economist.dto.StavkaNalogaDTO;
import com.economist.model.KifKufSearchBean;
import com.economist.service.KomitentService;
import com.economist.service.StavkaNalogaService;


@Controller
@RequestMapping(KifKufController.CONTROLLER)
public class KifKufController extends BaseController {
	
	final static Logger logger = Logger.getLogger(KifKufController.class);
	
	public static final String CONTROLLER = "api/kif-kuf";
	public static final String KIF = "kif";
	public static final String KUF = "kuf";
	
	@Autowired
	private StavkaNalogaService stavkaNalogaService;
	@Autowired
	private KomitentService komitentService;
	
	@RequestMapping(value = "kif", method = RequestMethod.GET)
	public String kif(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new KifKufSearchBean());
		model.addAttribute("action", CONTROLLER + "/generate-kif");
		List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
		komitents.add(0, new KomitentDTO());
		model.addAttribute("komitents", komitents);
		return KIF;
	}
	
	@RequestMapping(value = "generate-kif", method = RequestMethod.POST)
	public String generate(@ModelAttribute("search") KifKufSearchBean search, Errors errors, ModelMap model) {
		
		List<StavkaNalogaDTO> result = null;
		if (search.getKomitent() != null && search.getKomitent().getId() != null) {
			Komitent komitent = komitentService.find(search.getKomitent().getId());
			result = stavkaNalogaService.kif(search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece(), komitent);
		} else {
			result = stavkaNalogaService.kif(search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece());
		}
		
		model.addAttribute("search", search);
		model.addAttribute("action", CONTROLLER + "/generate-kif");
		List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
		komitents.add(0, new KomitentDTO());
		model.addAttribute("komitents", komitents);
		model.addAttribute("stavkes", result);
		setZbirniRed(result, model);
		
		return KIF;
	}

	@RequestMapping(value = "kuf", method = RequestMethod.GET)
	public String kuf(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new KifKufSearchBean());
		model.addAttribute("action", CONTROLLER + "/generate-kuf");
		List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
		komitents.add(0, new KomitentDTO());
		model.addAttribute("komitents", komitents);
		
		return KUF;
	}
	
	@RequestMapping(value = "generate-kuf", method = RequestMethod.POST)
	public String generateKuf(@ModelAttribute("search") KifKufSearchBean search, Errors errors, ModelMap model) {
		List<StavkaNalogaDTO> result = null;
		if (search.getKomitent() != null && search.getKomitent().getId() != null) {
			Komitent komitent = komitentService.find(search.getKomitent().getId());
			result = stavkaNalogaService.kuf(search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece(), komitent);
		} else {
			result = stavkaNalogaService.kuf(search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece());
		}
		
		model.addAttribute("search", search);
		model.addAttribute("action", CONTROLLER + "/generate-kuf");
		List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
		komitents.add(0, new KomitentDTO());
		model.addAttribute("komitents", komitents);
		model.addAttribute("stavkes", result);
		setZbirniRed(result, model);
		
		return KUF;
	}
	
	@Override
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(KomitentDTO.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				KomitentDTO komitent = new KomitentDTO();
				komitent.setId(Integer.parseInt(id));
				setValue(komitent);
			}
		});
	}
}
