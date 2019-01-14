package com.economist.controller;

import java.beans.PropertyEditorSupport;
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

import com.economist.config.BaseController;
import com.economist.db.entity.Konto;
import com.economist.db.repository.KontoRepository;
import com.economist.db.repository.NalogRepository;
import com.economist.db.repository.PreduzeceRepository;
import com.economist.db.repository.UserRepository;
import com.economist.model.SearchBean;


@Controller
@RequestMapping(BilansController.CONTROLLER)
public class BilansController extends BaseController {
	
	final static Logger logger = Logger.getLogger(BilansController.class);
	
	public static final String CONTROLLER = "bilans";
	public static final String VIEW_DEFAULT = "bilans";
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private KontoRepository kontoRepository;
	@Autowired
	private NalogRepository nalogRepository;
	@Autowired
	private PreduzeceRepository preduzeceRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new SearchBean());
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("showTable", false);
//		model.addAttribute("konta", kontoRepository.findByUser(getUser()));
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "generate", method = RequestMethod.POST)
	public String generate(@ModelAttribute("search") SearchBean search, Errors errors, ModelMap model) {
		
/*		model.addAttribute("search", search);
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("konta", kontoRepository.findByAgencija(getUser().getAgencija()));
		model.addAttribute("showTable", true);
		Preduzece p = preduzeceRepository.findOne(1);
		
		for (int i = Integer.valueOf(search.getKontoOdBilans()); i <= Integer.valueOf(search.getKontoDoBilans());i ++) {
			List<Object> result = kontoRepository.bilans(p, String.valueOf(i), search.getDatumOd(), search.getDatumDo());
			List<BilansResultBean> kontos = new ArrayList<BilansResultBean>();
			BigDecimal duguje = BigDecimal.ZERO;
			BigDecimal potrazuje = BigDecimal.ZERO;
			
			for (Object object : result) {
				BilansResultBean bean = new BilansResultBean();
				Object[] objectArray = (Object[]) object;
				bean.setKonto((Konto)objectArray[0]);
				bean.setDuguje((BigDecimal)objectArray[1]);
				bean.setPotrazuje((BigDecimal) objectArray[2]);
				bean.setSaldo((BigDecimal) objectArray[3]);
				
				kontos.add(bean);
				
				duguje = duguje.add((BigDecimal)objectArray[1]);
				potrazuje = potrazuje.add((BigDecimal) objectArray[2]);
			}
			model.addAttribute("konto" + i, kontos);
			model.addAttribute("dugujeKonto" + i, duguje);
			model.addAttribute("potrazujeKonto" + i, potrazuje);
			model.addAttribute("saldoKonto" + i, duguje.subtract(potrazuje));
		}*/
		
		return VIEW_DEFAULT;
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
