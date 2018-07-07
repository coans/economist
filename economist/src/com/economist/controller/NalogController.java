package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
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
	
	private static final String TITLE = "Dodaj novi nalog";

	final static Logger logger = Logger.getLogger(NalogController.class);
	
	public static final String CONTROLLER = "nalogs";
	public static final String VIEW_DEFAULT = "nalogs";
	private static final String VIEW_NEW = "nalog-new";
	
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
		model.addAttribute("nalogs", nalogRepository.findAll());//TODO by preduzece
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		Nalog nalog = new Nalog();
		nalog.setDuguje(BigDecimal.ZERO);
		nalog.setPotrazuje(BigDecimal.ZERO);
//		nalog.setDatum(/*dateFormat.getInstance().getCalendar().getTime()*/new Date());
		model.addAttribute("nalog", nalog);
		model.addAttribute("action", CONTROLLER + "/create");
		model.addAttribute("title", TITLE);
		model.addAttribute("konta", kontoRepository.findByUser(getUser()));
		model.addAttribute("vrstadokumentas", vrstaDokumentaRepository.findByUser(getUser()));
		List<Komitent> komitents = komitentRepository.findByUser(getUser());
		komitents.add(0, new Komitent());
		model.addAttribute("komitents", komitents);
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("nalog") Nalog nalog, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

//		validator.validate(food, errors);
		if (nalog.getKomitent().getId() == 0) {
			nalog.setKomitent(null);
		}
		if (errors.hasErrors()) {
			model.addAttribute("nalog", nalog);
			model.addAttribute("action", CONTROLLER + "/create");
			model.addAttribute("title", TITLE);
			model.addAttribute("konta", kontoRepository.findByUser(getUser()));
			model.addAttribute("vrstadokumentas", vrstaDokumentaRepository.findByUser(getUser()));
			List<Komitent> komitents = komitentRepository.findByUser(getUser());
			komitents.add(0, new Komitent());
			model.addAttribute("komitents", komitents);
			return VIEW_NEW;
		}
		
		Preduzece p = preduzeceRepository.findOne(1);
		nalog.setPreduzece(p);
		
		//set saldo
		nalog.setSaldo(nalog.getDuguje().subtract(nalog.getPotrazuje()));
		nalogRepository.save(nalog);
		
		return "redirect:/" + NalogController.CONTROLLER;
	}
/*	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer foodId, ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) throws SQLException, IOException {
		Food food = foodRepository.findOne(foodId);
		FoodBean foodBean = new FoodBean();
		foodBean.setCategory(food.getCategory());
		foodBean.setDescription(food.getDescription());
		foodBean.setId(food.getId());
		foodBean.setName(food.getName());
		foodBean.setPrice(food.getPrice());
		
		model.addAttribute("foodmodel", foodBean);
		model.addAttribute("action", CONTROLLER + "/update");
		model.addAttribute("title", "Edit food");
		model.addAttribute("categories", categoryRepository.findAll());
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("foodmodel") FoodBean foodBean, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

		Food food = foodRepository.findOne(foodBean.getId());
		food.setCategory(foodBean.getCategory());
		food.setDescription(foodBean.getDescription());
		food.setName(foodBean.getName());
		food.setPrice(foodBean.getPrice());
		try {
			if (foodBean.getImageFile().getBytes().length != 0) {
				food.setImage(BlobProxy.generateProxy(foodBean.getImageFile().getBytes()));
			}
		} catch (IOException e) {
			logger.error("Error updating food[" + foodBean.getId() + "]");
			e.printStackTrace();
		}
		
		validator.validate(food, errors);
		
		if (errors.hasErrors()) {
			model.addAttribute("foodmodel", foodBean);
			model.addAttribute("action", CONTROLLER + "/update");
			model.addAttribute("title", "Edit food");
			model.addAttribute("categories", categoryRepository.findAll());
			return VIEW_NEW;
		}
		
		foodRepository.save(food);
		
		return "redirect:/" + NalogController.CONTROLLER;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value = "id") Integer foodId) {
		//first delete bestseller
		bestsellerRepository.deleteBestsellerByFood(foodId);
		//delete food
		Food food = foodRepository.findOne(foodId);
		food.setDeleted(true);
		foodRepository.save(food);
		
		return "redirect:/" + NalogController.CONTROLLER;
	}
*/	
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
