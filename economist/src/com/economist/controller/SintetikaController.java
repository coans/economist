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
import com.economist.db.entity.Konto;
import com.economist.db.repository.NalogRepository;
import com.economist.dto.StavkaNalogaDTO;
import com.economist.model.SearchBean;
import com.economist.service.KomitentService;
import com.economist.service.KontoService;
import com.economist.service.StavkaNalogaService;


@Controller
@RequestMapping(SintetikaController.CONTROLLER)
public class SintetikaController extends BaseController {
	
	final static Logger logger = Logger.getLogger(SintetikaController.class);
	
	public static final String CONTROLLER = "api/sintetika";
	public static final String VIEW_DEFAULT = "sintetika";
	
	@Autowired
	private KontoService kontoService;
	@Autowired
	private KomitentService komitentService;
	@Autowired
	private StavkaNalogaService stavkaNalogaService;
	
	@Autowired
	private NalogRepository nalogRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new SearchBean());
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("kontos", kontoService.findSintetickaKonta(getUser().getAgencija()));
		model.addAttribute("komitents", komitentService.findByAgencija(getUser().getAgencija()));
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "generate", method = RequestMethod.POST)
	public String generate(@ModelAttribute("search") SearchBean search, Errors errors, ModelMap model) {
		
		model.addAttribute("search", new SearchBean());
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("kontos", kontoService.findSintetickaKonta(getUser().getAgencija()));
		model.addAttribute("komitents", komitentService.findByAgencija(getUser().getAgencija()));
		Konto kontoOd = kontoService.find(search.getKontoOd().getId());
		Konto kontoDo = kontoService.find(search.getKontoDo().getId());
		List<StavkaNalogaDTO> result = null;
		if (search.getKomitent() != null) {
			Komitent komitent = komitentService.find(search.getKomitent().getId());
			result = stavkaNalogaService.sintetika(kontoOd.getSifra() + "00", kontoDo.getSifra() + "99", search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece(), komitent);
		} else {
			result = stavkaNalogaService.sintetika(kontoOd.getSifra() + "00", kontoDo.getSifra() + "99", search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece());
		}
		
		model.addAttribute("stavkes", result);
		setZbirniRed(result, model);
		
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
