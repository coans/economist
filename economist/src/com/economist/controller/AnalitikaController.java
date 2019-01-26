package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
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
import com.economist.db.entity.Konto;
import com.economist.dto.KomitentDTO;
import com.economist.dto.KontoDTO;
import com.economist.dto.StavkaNalogaDTO;
import com.economist.model.SearchBean;
import com.economist.service.KomitentService;
import com.economist.service.KontoService;
import com.economist.service.StavkaNalogaService;
import com.itextpdf.text.DocumentException;


@Controller
@RequestMapping(AnalitikaController.CONTROLLER)
public class AnalitikaController extends BaseController {
	
	final static Logger logger = Logger.getLogger(AnalitikaController.class);
	
	public static final String CONTROLLER = "api/analitika";
	public static final String VIEW_DEFAULT = "analitika";
	
	@Autowired
	private KontoService kontoService;
	@Autowired
	private KomitentService komitentService;
	@Autowired
	private StavkaNalogaService stavkaNalogaService;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new SearchBean());
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("konta", kontoService.findAnalitickaKonta(getUser().getAgencija()));
		List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
		komitents.add(0, new KomitentDTO());
		model.addAttribute("komitents", komitents);
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "generate", method = RequestMethod.POST)
	public String generate(@ModelAttribute("search") SearchBean search, Errors errors, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws NoSuchMessageException, DocumentException, IOException {
		List<StavkaNalogaDTO> result = getResult(search);
		if (request.getParameter("pretraga") != null) {
			model.addAttribute("search", search);
			model.addAttribute("action", CONTROLLER + "/generate");
			model.addAttribute("konta", kontoService.findAnalitickaKonta(getUser().getAgencija()));
			List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
			komitents.add(0, new KomitentDTO());
			model.addAttribute("komitents", komitents);
			model.addAttribute("stavkes", result);
			setZbirniRed(result, model);
		
			return VIEW_DEFAULT;
		} else {
			generatePDF(request, response, result, messageSource.getMessage("analitika.header", null, request.getLocale()), search, "analitika");
			return null;
		}
	}
	
	private List<StavkaNalogaDTO> getResult(SearchBean search) {
		List<StavkaNalogaDTO> result;
		Konto kontoOd = kontoService.find(search.getKontoOd().getId());
		search.setKontoOd(new KontoDTO(kontoOd));
		Konto kontoDo = kontoService.find(search.getKontoDo().getId());
		search.setKontoDo(new KontoDTO(kontoDo));
		if (search.getKomitent() != null && search.getKomitent().getId() != null) {
			Komitent komitent = komitentService.find(search.getKomitent().getId());
			search.setKomitent(new KomitentDTO(komitent));
			result = stavkaNalogaService.analitika(kontoOd.getSifra(), kontoDo.getSifra(), search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece(), komitent);
		} else {
			result = stavkaNalogaService.analitika(kontoOd.getSifra(), kontoDo.getSifra(), search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece());
		}
		return result;
	}

	@Override
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(KontoDTO.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				KontoDTO konto = new KontoDTO();
				konto.setId(Integer.parseInt(id));
				setValue(konto);
			}
		});
		
		binder.registerCustomEditor(KomitentDTO.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				if (!id.isEmpty()) {
					KomitentDTO komitent = new KomitentDTO();
					komitent.setId(Integer.parseInt(id));
					setValue(komitent);
				}
			}
		});
	}
}
