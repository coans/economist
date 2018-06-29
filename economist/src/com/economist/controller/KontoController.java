package com.economist.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.economist.config.BaseController;
import com.economist.db.entity.Konto;
import com.economist.db.repository.KontoRepository;
import com.economist.db.repository.UserRepository;


@Controller
@RequestMapping(KontoController.CONTROLLER)
public class KontoController extends BaseController {
	
	private static final String TITLE = "Dodaj novi konto";

	final static Logger logger = Logger.getLogger(KontoController.class);
	
	public static final String CONTROLLER = "kontos";
	public static final String VIEW_DEFAULT = "kontos";
	private static final String VIEW_NEW = "konto-new";
	
	@Autowired
	private KontoRepository kontoRepository;
	@Autowired
	private UserRepository userRepository;
	
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
		model.addAttribute("kontos", kontoRepository.findAll());
		
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		Konto konto = new Konto();
//		nalog.setDatum(/*dateFormat.getInstance().getCalendar().getTime()*/new Date());
		model.addAttribute("konto", konto);
		model.addAttribute("action", CONTROLLER + "/create");
		model.addAttribute("title", TITLE);
		
		return VIEW_NEW;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("konto") Konto konto, Errors errors, ModelMap model,
			final RedirectAttributes redirectAttributes) {

//		validator.validate(food, errors);
		konto.setUser(getUser());		
		if (errors.hasErrors()) {
			model.addAttribute("konto", konto);
			model.addAttribute("action", CONTROLLER + "/create");
			model.addAttribute("title", TITLE);
			return VIEW_NEW;
		}
		
		kontoRepository.save(konto);
		
		return "redirect:/" + KontoController.CONTROLLER;
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
	}*/
}
