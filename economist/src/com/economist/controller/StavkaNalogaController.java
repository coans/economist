package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.economist.config.BaseController;
import com.economist.db.entity.Nalog;
import com.economist.dto.KomitentDTO;
import com.economist.dto.KontoDTO;
import com.economist.dto.NalogDTO;
import com.economist.dto.StavkaNalogaDTO;
import com.economist.dto.VrstaDokumentaDTO;
import com.economist.service.KomitentService;
import com.economist.service.KontoService;
import com.economist.service.NalogService;
import com.economist.service.StavkaNalogaService;
import com.economist.service.VrstaDokumentaService;
import com.economist.validator.StavkaNalogaValidator;


@Controller
@RequestMapping(StavkaNalogaController.CONTROLLER)
public class StavkaNalogaController extends BaseController {
	
	private static final String ACTION_CREATE = "create";
	private static final String DODAJ_NOVU_STAVKU_TITLE = "Dodaj novu stavku";

	final static Logger logger = Logger.getLogger(StavkaNalogaController.class);
	
	public static final String CONTROLLER = "api/stavkes";
	public static final String VIEW_DEFAULT = "stavkes";
	private static final String VIEW_NEW = "stavke-new";
	
	@Autowired
	private NalogService nalogService;
	@Autowired
	private StavkaNalogaService stavkaNalogaService;
	@Autowired
	private KontoService kontoService;
	@Autowired
	private VrstaDokumentaService vrstaDokumentaService;
	@Autowired
	private KomitentService komitentService;
	@Autowired
	private StavkaNalogaValidator validator;
	
	@RequestMapping(value = "/details/{nalogId}", method = RequestMethod.GET)
	public String defaultView(@PathVariable(value = "nalogId") Integer nalogId, ModelMap model, HttpServletRequest request, HttpSession session, Locale locale/*, @RequestParam(required = false) Integer preduzeceId*/) {
		final Nalog nalog = nalogService.find(nalogId);
		final List<StavkaNalogaDTO> stavke = stavkaNalogaService.findByNalog(nalog);
		setZbirniRed(stavke, model);
		model.addAttribute("stavkes", stavke);
		model.addAttribute("nalogId", nalog.getId());
		model.addAttribute("nalogBroj", nalog.getBroj());
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new/{nalogId}", method = RequestMethod.GET)
	public String add(@PathVariable(value = "nalogId") Integer nalogId, ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		StavkaNalogaDTO stavka = new StavkaNalogaDTO();
		stavka.setDuguje(BigDecimal.ZERO);
		stavka.setPotrazuje(BigDecimal.ZERO);
		stavka.setDatum(new Date());
		stavka.setNalog(nalogService.findOne(nalogId));
		setNalogModel(model, ACTION_CREATE, DODAJ_NOVU_STAVKU_TITLE, stavka);
		
		return VIEW_NEW;
	}

	private void setNalogModel(final ModelMap model, final String action, final String title, final StavkaNalogaDTO stavka) {
		model.addAttribute("stavka", stavka);
		model.addAttribute("action", CONTROLLER + "/" + action);
		model.addAttribute("title", title);
		model.addAttribute("konta", kontoService.findByAgencija(getUser().getAgencija()));
		List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
		komitents.add(0, new KomitentDTO());
		model.addAttribute("komitents", komitents);
	}
	
	@RequestMapping(value = ACTION_CREATE, method = RequestMethod.POST)
	public String create(@ModelAttribute("stavka") StavkaNalogaDTO stavka, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {
		NalogDTO nalog = nalogService.findOne(stavka.getNalog().getId());
		stavka.setNalog(nalog);

		validator.validate(stavka, errors);
		if (errors.hasErrors()) {
			setNalogModel(model, ACTION_CREATE, DODAJ_NOVU_STAVKU_TITLE, stavka);
			return VIEW_NEW;
		}

		stavka.setSaldo(stavka.getDuguje().subtract(stavka.getPotrazuje()));
		stavkaNalogaService.save(stavka);
		
		nalog.setModified(new Date());
		nalogService.save(nalog);
		
		return "redirect:/" + StavkaNalogaController.CONTROLLER + "/details/" + stavka.getNalog().getId();
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
		
		binder.registerCustomEditor(VrstaDokumentaDTO.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				VrstaDokumentaDTO vd = new VrstaDokumentaDTO();
				vd.setId(Integer.parseInt(id));
				setValue(vd);
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
