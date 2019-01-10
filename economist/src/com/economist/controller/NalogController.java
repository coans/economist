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
import com.economist.db.repository.KomitentRepository;
import com.economist.db.repository.KontoRepository;
import com.economist.dto.NalogDTO;
import com.economist.dto.PreduzeceDTO;
import com.economist.dto.VrstaDokumentaDTO;
import com.economist.service.NalogService;
import com.economist.service.PreduzeceService;
import com.economist.service.VrstaDokumentaService;


@Controller
@RequestMapping(NalogController.CONTROLLER)
public class NalogController extends BaseController {
	
	private static final String ACTION_CREATE = "create";
	private static final String ACTION_ADD = "add";
	private static final String DODAJ_NOVI_NALOG_TITLE = "Dodaj novi nalog";
	private static final String DODAJ_NOVU_STAVKU_TITLE = "Dodaj novu stavku";

	final static Logger logger = Logger.getLogger(NalogController.class);
	
	public static final String CONTROLLER = "api/nalogs";
	public static final String VIEW_DEFAULT = "nalogs";
	private static final String VIEW_NEW = "nalog-new";
	private static final String VIEW_DETAILS = "nalog-details";
	
	@Autowired
	private NalogService nalogService;
	@Autowired
	private KontoRepository kontoRepository;
	@Autowired
	private PreduzeceService preduzeceService;
	@Autowired
	private VrstaDokumentaService vrstaDokumentaService;
	@Autowired
	private KomitentRepository komitentRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale/*, @RequestParam(required = false) Integer preduzeceId*/) {
//		if (categoryId != null && categoryId != 0) {
//			model.addAttribute("foods", foodRepository.findFoodByCategory(categoryId));
//			model.addAttribute("selectedCategoryId", categoryId);
//		} else {
//			model.addAttribute("foods", foodRepository.findAll());
//		}
//		List<Category> categories = categoryRepository.findAll();
//		categories.add(0, new Category("Select category..."));
//		model.addAttribute("categories", categories);
		final List<NalogDTO> nalogs = nalogService.findByPreduzece(getUser().getPreduzece());
		model.addAttribute("nalogs", nalogs);
		setUkupno(nalogs, model);
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		NalogDTO nalog = new NalogDTO();
		setNalogModel(model, nalog, ACTION_CREATE, DODAJ_NOVI_NALOG_TITLE, false);
		
		return VIEW_NEW;
	}

	private void setNalogModel(final ModelMap model, final NalogDTO nalog, final String action, final String title, final boolean disabled) {
		model.addAttribute("nalog", nalog);
		model.addAttribute("action", CONTROLLER + "/" + action);
		model.addAttribute("title", title);
		model.addAttribute("vrstadokumentas", vrstaDokumentaService.findByAgencija(getUser().getAgencija()));
//		model.addAttribute("konta", kontoRepository.findByAgencija(getUser().getAgencija()));
//		model.addAttribute("vrstadokumentas", vrstaDokumentaService.findByAgencija(getUser().getAgencija()));
//		List<KomitentDTO> komitents = komitentRepository.findByAgencija(getUser().getAgencija());
//		komitents.add(0, new KomitentDTO());
//		model.addAttribute("komitents", komitents);
//		model.addAttribute("disabled", disabled);
	}
	
	@RequestMapping(value = ACTION_CREATE, method = RequestMethod.POST)
	public String create(@ModelAttribute("nalog") NalogDTO nalog, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

//		validator.validate(food, errors);
		if (errors.hasErrors()) {
			setNalogModel(model, nalog, ACTION_CREATE, DODAJ_NOVI_NALOG_TITLE, false);
			return VIEW_NEW;
		}
		nalog.setPreduzece(new PreduzeceDTO(getUser().getPreduzece()));
		nalog.setModified(new Date());
		nalog.setZakljucan(Boolean.FALSE);
		
		nalogService.save(nalog);
		
		return "redirect:/" + NalogController.CONTROLLER;
	}
	
//	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
//	public String novaStavkaNaloga(@PathVariable(value = "id") Integer nalogId, ModelMap model) {
//		NalogDTO parentNalog = nalogService.findOne(nalogId);
//		NalogDTO childNalog = new NalogDTO();
//		childNalog.setParentId(parentNalog.getId());
//		childNalog.setBroj(parentNalog.getBroj());
//		childNalog.setPreduzece(new PreduzeceDTO(getUser().getPreduzece()));
//		childNalog.setVrstaDokumenta(parentNalog.getVrstaDokumenta());
//		childNalog.setDuguje(BigDecimal.ZERO);
//		childNalog.setPotrazuje(BigDecimal.ZERO);
//		childNalog.setDatum(new Date());
//
//		setNalogModel(model, childNalog, ACTION_ADD, DODAJ_NOVU_STAVKU_TITLE, true);
//		
//		return VIEW_NEW;
//	}
	
//	@RequestMapping(value = "add", method = RequestMethod.POST)
//	public String dodajStavkuNaloga(@ModelAttribute("nalog") NalogDTO nalog, Errors errors, ModelMap model) {
//
////		validator.validate(food, errors);
//		if (errors.hasErrors()) {
//			setNalogModel(model, nalog, ACTION_ADD, DODAJ_NOVU_STAVKU_TITLE, true);
//			return VIEW_NEW;
//		}
//		nalog.setPreduzece(new PreduzeceDTO(getUser().getPreduzece()));
//		nalog.setVrstaDokumenta(new VrstaDokumentaDTO(nalogService.find(nalog.getParentId()).getVrstaDokumenta()));
//		
//		//set saldo
//		nalog.setSaldo(nalog.getDuguje().subtract(nalog.getPotrazuje()));
//		nalogService.save(nalog);
//		
//		return "redirect:/" + NalogController.CONTROLLER + "/details/" + nalog.getParentId();
//	}	
	@RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
	public String details(@PathVariable(value = "id") Integer nalogId, ModelMap model)
	{
//		List<NalogDTO> nalogs = nalogService.findByPreduzeceAndParent(getUser().getPreduzece(), nalogId);
//		model.addAttribute("nalogs", nalogs);
//		model.addAttribute("nalogId", nalogId);
//		setZbirniRed(nalogs, model);
		
		return "redirect:/" + StavkaNalogaController.CONTROLLER + "/" + nalogId;
	}
	
	@RequestMapping(value = "/zakljucaj/{id}", method = RequestMethod.GET)
	public String zakljucaj(@PathVariable(value = "id") Integer nalogId, ModelMap model)
	{
		NalogDTO nalog = nalogService.findOne(nalogId);
		nalog.setZakljucan(Boolean.TRUE);
		
		nalogService.save(nalog);
		
		return "redirect:/" + NalogController.CONTROLLER;
	}

	@RequestMapping(value = "/otkljucaj/{id}", method = RequestMethod.GET)
	public String otkljucaj(@PathVariable(value = "id") Integer nalogId, ModelMap model)
	{
		NalogDTO nalog = nalogService.findOne(nalogId);
		nalog.setZakljucan(Boolean.FALSE);
		
		nalogService.save(nalog);
		
		return "redirect:/" + NalogController.CONTROLLER;
	}
	
	public void setUkupno(List<NalogDTO> stavke, ModelMap model) {
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
