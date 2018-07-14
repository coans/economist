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
import com.economist.db.entity.Komitent;
import com.economist.db.entity.Konto;
import com.economist.db.entity.Nalog;
import com.economist.db.entity.Preduzece;
import com.economist.db.entity.VrstaDokumenta;
import com.economist.db.repository.KomitentRepository;
import com.economist.db.repository.KontoRepository;
import com.economist.db.repository.NalogRepository;
import com.economist.db.repository.PreduzeceRepository;
import com.economist.db.repository.VrstaDokumentaRepository;


@Controller
@RequestMapping(NalogController.CONTROLLER)
public class NalogController extends BaseController {
	
	private static final String ACTION_CREATE = "create";
	private static final String ACTION_ADD = "add";
	private static final String DODAJ_NOVI_NALOG_TITLE = "Dodaj novi nalog";
	private static final String DODAJ_NOVU_STAVKU_TITLE = "Dodaj novu stavku";

	final static Logger logger = Logger.getLogger(NalogController.class);
	
	public static final String CONTROLLER = "nalogs";
	public static final String VIEW_DEFAULT = "nalogs";
	private static final String VIEW_NEW = "nalog-new";
	private static final String VIEW_DETAILS = "nalog-details";
	
	@Autowired
	private NalogRepository nalogRepository;
	@Autowired
	private KontoRepository kontoRepository;
	@Autowired
	private PreduzeceRepository preduzeceRepository;
	@Autowired
	private VrstaDokumentaRepository vrstaDokumentaRepository;
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
		final List<Nalog> nalogs = nalogRepository.findAll();
		getZbirniRed(nalogs, model);
		Preduzece p = preduzeceRepository.findOne(1);//TODO fix this
		model.addAttribute("nalogs", nalogRepository.findParentNalogByPreduzece(p));//TODO by preduzece
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		Nalog nalog = new Nalog();
		nalog.setDuguje(BigDecimal.ZERO);
		nalog.setPotrazuje(BigDecimal.ZERO);
		nalog.setDatum(new Date());
		setNalogModel(model, nalog, ACTION_CREATE, DODAJ_NOVI_NALOG_TITLE, false);
		
		return VIEW_NEW;
	}

	private void setNalogModel(final ModelMap model, final Nalog nalog, final String action, final String title, final boolean disabled) {
		model.addAttribute("nalog", nalog);
		model.addAttribute("action", CONTROLLER + "/" + action);
		model.addAttribute("title", title);
		model.addAttribute("konta", kontoRepository.findByUser(getUser()));
		model.addAttribute("vrstadokumentas", vrstaDokumentaRepository.findByUser(getUser()));
		List<Komitent> komitents = komitentRepository.findByUser(getUser());
		komitents.add(0, new Komitent());
		model.addAttribute("komitents", komitents);
		model.addAttribute("disabled", disabled);
	}
	
	@RequestMapping(value = ACTION_CREATE, method = RequestMethod.POST)
	public String create(@ModelAttribute("nalog") Nalog nalog, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

//		validator.validate(food, errors);
		if (nalog.getKomitent().getId() == 0) {
			nalog.setKomitent(null);
		}
		if (errors.hasErrors()) {
			setNalogModel(model, nalog, ACTION_CREATE, DODAJ_NOVI_NALOG_TITLE, false);
			return VIEW_NEW;
		}
		Preduzece p = preduzeceRepository.findOne(1);//TODO fix this
		nalog.setPreduzece(p);
		
		//set saldo
		nalog.setSaldo(nalog.getDuguje().subtract(nalog.getPotrazuje()));
		nalogRepository.save(nalog);
		
		return "redirect:/" + NalogController.CONTROLLER;
	}
	
	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer nalogId, ModelMap model) {
		Nalog parentNalog = nalogRepository.findOne(nalogId);
		Nalog childNalog = new Nalog();
		childNalog.setParentId(parentNalog.getId());
		childNalog.setBroj(parentNalog.getBroj());
		childNalog.setPreduzece(parentNalog.getPreduzece());
		childNalog.setVrstaDokumenta(parentNalog.getVrstaDokumenta());
		childNalog.setDuguje(BigDecimal.ZERO);
		childNalog.setPotrazuje(BigDecimal.ZERO);

		setNalogModel(model, childNalog, ACTION_ADD, DODAJ_NOVU_STAVKU_TITLE, true);
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String update(@ModelAttribute("nalog") Nalog nalog, Errors errors, ModelMap model) {

//		validator.validate(food, errors);
		if (nalog.getKomitent().getId() == 0) {
			nalog.setKomitent(null);
		}
		if (errors.hasErrors()) {
			setNalogModel(model, nalog, ACTION_ADD, DODAJ_NOVU_STAVKU_TITLE, true);
			return VIEW_NEW;
		}
		Preduzece p = preduzeceRepository.findOne(1);//TODO fix this
		nalog.setPreduzece(p);
		
		//set saldo
		nalog.setSaldo(nalog.getDuguje().subtract(nalog.getPotrazuje()));
		nalogRepository.save(nalog);
		
		return "redirect:/" + NalogController.CONTROLLER + "/details/" + nalog.getParentId();
	}	
	@RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
	public String details(@PathVariable(value = "id") Integer nalogId, ModelMap model)
	{
		Preduzece p = preduzeceRepository.findOne(1);//TODO fix this
		model.addAttribute("nalogs", nalogRepository.findByPreduzeceAndParent(p, nalogId));//TODO by preduzece
		model.addAttribute("nalogId", nalogId);
		
		return VIEW_DETAILS;
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
		
		binder.registerCustomEditor(VrstaDokumenta.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String id) {
				VrstaDokumenta vd = new VrstaDokumenta();
				vd.setId(Integer.parseInt(id));
				setValue(vd);
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
