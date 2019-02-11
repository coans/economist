package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
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
import com.economist.dto.KifKufDTO;
import com.economist.dto.KomitentDTO;
import com.economist.model.KifKufSearchBean;
import com.economist.service.KomitentService;
import com.economist.service.StavkaNalogaService;
import com.itextpdf.text.DocumentException;


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
	@Autowired
	private MessageSource messageSource;
	
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
	public String generate(@ModelAttribute("search") KifKufSearchBean search, Errors errors, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException {
		List<KifKufDTO> result = getKifResult(search);
		if (request.getParameter("pretraga") != null) {
			model.addAttribute("search", search);
			model.addAttribute("action", CONTROLLER + "/generate-kif");
			List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
			komitents.add(0, new KomitentDTO());
			model.addAttribute("komitents", komitents);
			model.addAttribute("stavkes", result);
		
			return KIF;
		} else {
			List<String> headers = Arrays.asList("#", messageSource.getMessage("broj.fakture", null, request.getLocale()), messageSource.getMessage("datum", null, request.getLocale()),
					messageSource.getMessage("komitent", null, request.getLocale()), messageSource.getMessage("iznos", null, request.getLocale()), messageSource.getMessage("kif.osnovica", null, request.getLocale()),
					messageSource.getMessage("kif.pdv", null, request.getLocale()));
			generateKifKufPDF(request, response, result, messageSource.getMessage("kif", null, request.getLocale()), search, headers, "kif");
			return null;
		}
	}

	private List<KifKufDTO> getKifResult(KifKufSearchBean search) {
		List<KifKufDTO> result = null;
		if (search.getKomitent() != null && search.getKomitent().getId() != null) {
			Komitent komitent = komitentService.find(search.getKomitent().getId());
			search.setKomitent(new KomitentDTO(komitent));
			result = stavkaNalogaService.kif(search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece(), komitent);
		} else {
			result = stavkaNalogaService.kif(search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece());
		}
		return result;
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
	public String generateKuf(@ModelAttribute("search") KifKufSearchBean search, Errors errors, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws NoSuchMessageException, DocumentException, IOException {
		List<KifKufDTO> result = getKufResult(search);
		if (request.getParameter("pretraga") != null) {
			model.addAttribute("search", search);
			model.addAttribute("action", CONTROLLER + "/generate-kuf");
			List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
			komitents.add(0, new KomitentDTO());
			model.addAttribute("komitents", komitents);
			model.addAttribute("stavkes", result);
			
			return KUF;
		} else {
			List<String> headers = Arrays.asList("#", messageSource.getMessage("broj.fakture", null, request.getLocale()), messageSource.getMessage("datum", null, request.getLocale()),
					messageSource.getMessage("komitent", null, request.getLocale()), messageSource.getMessage("kuf.ukupno", null, request.getLocale()), messageSource.getMessage("iznos", null, request.getLocale()),
					messageSource.getMessage("kuf.pdv", null, request.getLocale()));
			generateKifKufPDF(request, response, result, messageSource.getMessage("kuf", null, request.getLocale()), search, headers, "kuf");
			return null;
		}
	}

	private List<KifKufDTO> getKufResult(KifKufSearchBean search) {
		List<KifKufDTO> result = null;
		if (search.getKomitent() != null && search.getKomitent().getId() != null) {
			Komitent komitent = komitentService.find(search.getKomitent().getId());
			search.setKomitent(new KomitentDTO(komitent));
			result = stavkaNalogaService.kuf(search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece(), komitent);
		} else {
			result = stavkaNalogaService.kuf(search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece());
		}
		return result;
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
