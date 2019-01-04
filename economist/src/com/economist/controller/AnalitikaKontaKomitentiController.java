package com.economist.controller;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.economist.config.BaseController;
import com.economist.db.entity.Komitent;
import com.economist.db.entity.Konto;
import com.economist.db.entity.Preduzece;
import com.economist.db.repository.KomitentRepository;
import com.economist.db.repository.KontoRepository;
import com.economist.db.repository.NalogRepository;
import com.economist.db.repository.PreduzeceRepository;
import com.economist.db.repository.UserRepository;
import com.economist.model.AnalitikaKontoKomitentResultBean;
import com.economist.model.AnalitikaSearchBean;


@Controller
@RequestMapping(AnalitikaKontaKomitentiController.CONTROLLER)
public class AnalitikaKontaKomitentiController extends BaseController {
	
	final static Logger logger = Logger.getLogger(AnalitikaKontaKomitentiController.class);
	
	public static final String CONTROLLER = "analitika-konta-komitenti";
	public static final String VIEW_DEFAULT = "analitika-konta-komitenti";
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private KontoRepository kontoRepository;
	@Autowired
	private NalogRepository nalogRepository;
	@Autowired
	private PreduzeceRepository preduzeceRepository;
	@Autowired
	private KomitentRepository komitentRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request, HttpSession session, Locale locale) {
		model.addAttribute("search", new AnalitikaSearchBean());
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("konta", kontoRepository.findByAgencija(getUser().getAgencija()));
		model.addAttribute("komitents", komitentRepository.findByAgencija(getUser().getAgencija()));
		return VIEW_DEFAULT;
	}
	
	@RequestMapping(value = "generate", method = RequestMethod.POST)
	public String generate(@ModelAttribute("search") AnalitikaSearchBean search, Errors errors, ModelMap model) {
		
		model.addAttribute("search", search);
		model.addAttribute("action", CONTROLLER + "/generate");
		model.addAttribute("konta", kontoRepository.findByAgencija(getUser().getAgencija()));
		model.addAttribute("komitents", komitentRepository.findByAgencija(getUser().getAgencija()));
		Preduzece p = preduzeceRepository.findOne(1);
		
		List<Object> result = kontoRepository.analitikaKontoKomitent(p, String.valueOf(search.getKontoOd().getId()), search.getDatumOd(), search.getDatumDo(), search.getKomitent());
		List<AnalitikaKontoKomitentResultBean> resultBean = new ArrayList<AnalitikaKontoKomitentResultBean>();
		BigDecimal duguje = BigDecimal.ZERO;
		BigDecimal potrazuje = BigDecimal.ZERO;
		BigDecimal saldo = BigDecimal.ZERO;
		
		for (Object object : result) {
			AnalitikaKontoKomitentResultBean bean = new AnalitikaKontoKomitentResultBean();
			Object[] objectArray = (Object[]) object;
			bean.setKonto((Konto)objectArray[0]);
			bean.setKomitent((Komitent)objectArray[1]);
			bean.setDuguje((BigDecimal)objectArray[2]);
			bean.setPotrazuje((BigDecimal) objectArray[3]);
			bean.setSaldo((BigDecimal) objectArray[4]);
			
			resultBean.add(bean);
			
			duguje = duguje.add((BigDecimal)objectArray[2]);
			potrazuje = potrazuje.add((BigDecimal) objectArray[3]);
			saldo = saldo.add((BigDecimal) objectArray[4]);
		}
		model.addAttribute("beans", resultBean);
		model.addAttribute("dugujeBean", duguje);
		model.addAttribute("potrazujeBean", potrazuje);
		model.addAttribute("saldoBean", saldo);
		
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
