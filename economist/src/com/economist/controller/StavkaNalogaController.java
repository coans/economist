package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.Calendar;
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

import com.economist.config.BaseController;
import com.economist.db.entity.Nalog;
import com.economist.db.entity.StavkaNaloga;
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
	private static final String ACTION_UPDATE = "update";
	private static final String DODAJ_NOVU_STAVKU_TITLE = "Dodaj novu stavku";
	private static final String IZMIJENI_STAVKU_TITLE = "Izmijeni stavku";
	

	final static Logger logger = Logger.getLogger(StavkaNalogaController.class);
	
	public static final String CONTROLLER = "api/stavkas";
	public static final String VIEW_DEFAULT = "stavkas";
	private static final String VIEW_NEW = "stavka-new";
	
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
		stavka.setDatum(new Date());
		stavka.setNalog(nalogService.findOne(nalogId));
		stavka.setDugujeStavka(BigDecimal.ZERO);
		stavka.setPotrazujeStavka(BigDecimal.ZERO);
		stavka.setOpis(nalogService.findOne(nalogId).getBroj());
		
		stavka.setDugujeProtivStavka(BigDecimal.ZERO);
		stavka.setPotrazujeProtivStavka(BigDecimal.ZERO);
		
		stavka.setDugujePDV(BigDecimal.ZERO);
		stavka.setPotrazujePDV(BigDecimal.ZERO);

		setNalogModel(model, ACTION_CREATE, DODAJ_NOVU_STAVKU_TITLE, stavka);
		
		return VIEW_NEW;
	}

	private void setNalogModel(final ModelMap model, final String action, final String title, final StavkaNalogaDTO stavka) {
		model.addAttribute("stavka", stavka);
		model.addAttribute("action", CONTROLLER + "/" + action);
		model.addAttribute("title", title);
		List<KontoDTO> kontos = kontoService.findByAgencija(getUser().getAgencija());
		KontoDTO konto = new KontoDTO();
		konto.setId(-1);
		kontos.add(0, konto);		
		model.addAttribute("konta", kontos);
		List<KomitentDTO> komitents = komitentService.findByAgencija(getUser().getAgencija());
		KomitentDTO emptyKomitent = new KomitentDTO();
		emptyKomitent.setId(-1);
		komitents.add(0, emptyKomitent);
		model.addAttribute("komitents", komitents);
	}
	
	@RequestMapping(value = ACTION_CREATE, method = RequestMethod.POST)
	public String create(@ModelAttribute("stavka") StavkaNalogaDTO stavka, Errors errors, ModelMap model) {
		NalogDTO nalog = nalogService.findOne(stavka.getNalog().getId());
		stavka.setNalog(nalog);
		if (stavka.getKomitent() != null && stavka.getKomitent().getId() != null) {
			KomitentDTO komitent = komitentService.findOne(stavka.getKomitent().getId());
			stavka.setKomitent(komitent);
		}

		validator.validate(stavka, errors);
		if (errors.hasErrors()) {
			setNalogModel(model, ACTION_CREATE, DODAJ_NOVU_STAVKU_TITLE, stavka);
			return VIEW_NEW;
		}

		stavka.setSaldoStavka(stavka.getDugujeStavka().subtract(stavka.getPotrazujeStavka()));
		stavka.setSaldoProtivStavka(stavka.getDugujeProtivStavka().subtract(stavka.getPotrazujeProtivStavka()));
		if (stavka.getDugujePDV() != null && stavka.getPotrazujePDV() != null) {
			stavka.setSaldoPDV(stavka.getDugujePDV().subtract(stavka.getPotrazujePDV()));
		}
		stavka.setIdentifikator(String.valueOf(Calendar.getInstance().getTimeInMillis()) + getUser().getId());
		stavkaNalogaService.save(stavka);
		
		nalog.setModified(new Date());
		nalogService.save(nalog);

		return "redirect:/" + StavkaNalogaController.CONTROLLER + "/details/" + stavka.getNalog().getId();
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer id, HttpServletRequest request, ModelMap model) {
		StavkaNalogaDTO stavkaDTO = new StavkaNalogaDTO();
		String identifikator = stavkaNalogaService.getIdentifikatorById(id);
		List<StavkaNaloga> stavkes = stavkaNalogaService.findByIdentifikator(identifikator);
		if (stavkes != null && !stavkes.isEmpty()) {
			stavkaDTO.setDatum(stavkes.get(0).getDatum());
			stavkaDTO.setIdentifikator(identifikator);
			stavkaDTO.setKomitent(new KomitentDTO(stavkes.get(0).getKomitent()));
			stavkaDTO.setNalog(new NalogDTO(stavkes.get(0).getNalog(), null, null, null));
			stavkaDTO.setOpis(stavkes.get(0).getOpis());
			stavkaDTO.setBrojFakture(stavkes.get(0).getBrojFakture());
		}
		for (StavkaNaloga stavkaNaloga : stavkes) {
			switch (stavkaNaloga.getVrsta()) {
			case "STAVKA":
				stavkaDTO.setDugujeStavka(stavkaNaloga.getDuguje());
				stavkaDTO.setPotrazujeStavka(stavkaNaloga.getPotrazuje());
				stavkaDTO.setSaldoStavka(stavkaNaloga.getSaldo());
				stavkaDTO.setKontoStavka(new KontoDTO(stavkaNaloga.getKonto()));
				stavkaDTO.setIdStavka(stavkaNaloga.getId());
				break;
			case "PROTIVSTAVKA":
				stavkaDTO.setDugujeProtivStavka(stavkaNaloga.getDuguje());
				stavkaDTO.setPotrazujeProtivStavka(stavkaNaloga.getPotrazuje());
				stavkaDTO.setSaldoProtivStavka(stavkaNaloga.getSaldo());
				stavkaDTO.setKontoProtivStavka(new KontoDTO(stavkaNaloga.getKonto()));
				stavkaDTO.setIdProtivStavka(stavkaNaloga.getId());
				break;
			case "PDV":
				stavkaDTO.setDugujePDV(stavkaNaloga.getDuguje());
				stavkaDTO.setPotrazujePDV(stavkaNaloga.getPotrazuje());
				stavkaDTO.setSaldoPDV(stavkaNaloga.getSaldo());
				stavkaDTO.setKontoPDV(new KontoDTO(stavkaNaloga.getKonto()));
				stavkaDTO.setIdPDV(stavkaNaloga.getId());
				break;
			}
		}
		
		setNalogModel(model, ACTION_UPDATE, IZMIJENI_STAVKU_TITLE, stavkaDTO);
		
		return VIEW_NEW;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("stavka") StavkaNalogaDTO stavka, HttpServletRequest request, Errors errors, ModelMap model) {
		return create(stavka, errors, model);
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
