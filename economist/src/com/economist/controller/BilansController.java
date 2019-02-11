package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.economist.db.entity.Konto;
import com.economist.dto.KontoDTO;
import com.economist.model.BilansResultBean;
import com.economist.model.SearchBean;
import com.economist.service.KontoService;
import com.economist.service.StavkaNalogaService;
import com.itextpdf.text.DocumentException;


@Controller
@RequestMapping(BilansController.CONTROLLER)
public class BilansController extends BaseController {
	
	final static Logger logger = Logger.getLogger(BilansController.class);
	
	public static final String CONTROLLER = "api/bilans";
	public static final String VIEW_DEFAULT = "bilans";
	
	@Autowired
	private StavkaNalogaService stavkaNalogaService;
	@Autowired
	private KontoService kontoService;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new SearchBean());
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("kontos", kontoService.findSintetickaKonta(getUser().getAgencija()));
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "generate", method = RequestMethod.POST)
	public String generate(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("search") SearchBean search, Errors errors, ModelMap model) throws NoSuchMessageException, DocumentException, IOException {
		Konto kontoOd = kontoService.find(search.getKontoOd().getId());
		search.setKontoOd(new KontoDTO(kontoOd));
		Konto kontoDo = kontoService.find(search.getKontoDo().getId());
		search.setKontoDo(new KontoDTO(kontoDo));
		Map<Integer, List<BilansResultBean>> result = stavkaNalogaService.bilans(search.getKontoOd().getSifra(), search.getKontoDo().getSifra(), search.getDatumOd(), search.getDatumDo(), getUser().getPreduzece());
		if (request.getParameter("pretraga") != null) {
			model.addAttribute("search", search);
			model.addAttribute("action", CONTROLLER + "/generate");
			model.addAttribute("kontos", kontoService.findSintetickaKonta(getUser().getAgencija()));
			for (Integer key : result.keySet()) {
				model.addAttribute("konto" + key, result.get(key));
			}
			
			return VIEW_DEFAULT;
		} else {
			List<String> headers = Arrays.asList("#", messageSource.getMessage("konto", null, request.getLocale()), messageSource.getMessage("duguje", null, request.getLocale()),
					messageSource.getMessage("potrazuje", null, request.getLocale()), messageSource.getMessage("saldo", null, request.getLocale()));
			generateBilansPDF(response, result, messageSource.getMessage("bilans.header", null, request.getLocale()), search, headers, "bilans");
			return null;
		}
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
