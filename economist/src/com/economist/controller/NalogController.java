package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.economist.config.BaseController;
import com.economist.dto.NalogDTO;
import com.economist.dto.PreduzeceDTO;
import com.economist.dto.VrstaDokumentaDTO;
import com.economist.service.NalogService;
import com.economist.service.PreduzeceService;
import com.economist.service.VrstaDokumentaService;
import com.economist.validator.NalogValidator;


@Controller
@RequestMapping(NalogController.CONTROLLER)
public class NalogController extends BaseController {
	final static Logger logger = Logger.getLogger(NalogController.class);
	
	private static final String ACTION_CREATE = "create";
	public static final String CONTROLLER = "api/nalogs";
	public static final String VIEW_DEFAULT = "nalogs";
	private static final String VIEW_NEW = "nalog-new";
	
	@Autowired
	private NalogService nalogService;
	@Autowired
	private PreduzeceService preduzeceService;
	@Autowired
	private VrstaDokumentaService vrstaDokumentaService;
	@Autowired
	private NalogValidator validator;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale, @RequestParam(required = false) Integer status) {
		List<NalogDTO> nalogs = null;
		if (status != null) {
			nalogs = nalogService.findByPreduzeceAndStatus(getUser().getPreduzece(), status);
		} else {
			nalogs = nalogService.findByPreduzeceAndStatus(getUser().getPreduzece(), 0);
		}
		model.addAttribute("statusSelected", status);
		model.addAttribute("statuses", Arrays.asList(0, 1));
		model.addAttribute("nalogs", nalogs);
		setUkupno(nalogs, model);
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		NalogDTO nalog = new NalogDTO();
		setNalogModel(model, nalog, ACTION_CREATE, false);
		
		return VIEW_NEW;
	}

	private void setNalogModel(final ModelMap model, final NalogDTO nalog, final String action, final boolean disabled) {
		model.addAttribute("nalog", nalog);
		model.addAttribute("action", CONTROLLER + "/" + action);
		List<VrstaDokumentaDTO> vrstaDokumentas = vrstaDokumentaService.findByAgencija(getUser().getPreduzece().getAgencija());
		vrstaDokumentas.add(0, new VrstaDokumentaDTO());
		model.addAttribute("vrstadokumentas", vrstaDokumentas);
	}
	
	@RequestMapping(value = ACTION_CREATE, method = RequestMethod.POST)
	public String create(@ModelAttribute("nalog") NalogDTO nalog, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

		nalog.setPreduzece(new PreduzeceDTO(getUser().getPreduzece()));
		nalog.setModified(new Date());
		nalog.setZakljucan(0);
		
		validator.validate(nalog, errors);
		if (errors.hasErrors()) {
			setNalogModel(model, nalog, ACTION_CREATE, false);
			return VIEW_NEW;
		}
		
		nalogService.save(nalog);
		
		return "redirect:/" + NalogController.CONTROLLER;
	}
	
	@RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
	public String details(@PathVariable(value = "id") Integer nalogId, ModelMap model) {
		return "redirect:/" + StavkaNalogaController.CONTROLLER + "/" + nalogId;
	}
	
	@RequestMapping(value = "/zakljucaj/{id}", method = RequestMethod.GET)
	public String zakljucaj(@PathVariable(value = "id") Integer nalogId, ModelMap model) {
		NalogDTO nalog = nalogService.findOne(nalogId);
		nalog.setZakljucan(1);
		
		nalogService.save(nalog);
		
		return "redirect:/" + NalogController.CONTROLLER;
	}

	@RequestMapping(value = "/otkljucaj/{id}", method = RequestMethod.GET)
	public String otkljucaj(@PathVariable(value = "id") Integer nalogId, ModelMap model) {
		NalogDTO nalog = nalogService.findOne(nalogId);
		nalog.setZakljucan(0);
		
		nalogService.save(nalog);
		
		return "redirect:/" + NalogController.CONTROLLER;
	}
	
	private void setUkupno(List<NalogDTO> stavke, ModelMap model) {
		BigDecimal duguje = BigDecimal.ZERO;
		BigDecimal potrazuje = BigDecimal.ZERO;
		
		
		for (NalogDTO stavka : stavke) {
			duguje = duguje.add(stavka.getDuguje());
			potrazuje = potrazuje.add(stavka.getPotrazuje());
		}
		model.addAttribute("duguje", duguje);
		model.addAttribute("potrazuje", potrazuje);
		model.addAttribute("saldo", duguje.subtract(potrazuje));
	}
	
	@Override
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		
		binder.registerCustomEditor(VrstaDokumentaDTO.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				VrstaDokumentaDTO vd = new VrstaDokumentaDTO();
				vd.setId(Integer.parseInt(id));
				setValue(vd);
			}
		});
	}
}
