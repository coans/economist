/**
 * Every controller should be extended from this class
 *
 * @author Juraj Bilic
 * @version $Author: jbilic $ ($Revision: 1.2 $) - $Date: 2012-05-21 14:18:54 $
 * @since 1.0.0
 */

package com.economist.config;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.economist.auth.AuthorizationService;
import com.economist.db.entity.Nalog;
import com.economist.db.entity.User;

@Controller("/" + BaseController.CONTROLLER)
public class BaseController {

	public static final String CONTROLLER = "base";
	public static final String MODEL_USER = "user";

	private static final String BASEURL = "baseurl";
	private static final String VERISON = "verison";
	
	private static final String KONTA_KRATKO = "kontaKratko";
	private static final String DATUM_PATTERN = "datumPattern";
	private static final String POTRAZUJE = "potrazuje";
	private static final String DUGUJE = "duguje";
	
	
	@Value("${base.url}")
	protected String baseUrl;
	
	@Value("${product.version}")
	protected String productVersion;
	
	@Value("${datum.format}")
	protected String datumFormat;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(datumFormat);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@ModelAttribute(MODEL_USER)
	public User getUser() {
		return AuthorizationService.getAuthentificatedUser();
	}
	
	@ModelAttribute(BASEURL)
	public String getBaseUrl() {
		return baseUrl;
	}

	@ModelAttribute(VERISON)
	public String getProductVersion() {
		return productVersion;
	}

	@ModelAttribute(KONTA_KRATKO)
	public List<Integer> getKontaKratko() {
		return Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	}
	
	@ModelAttribute(DATUM_PATTERN)
	public String getDatumPattern() {
		return datumFormat;
	}
	
	public void getSaldo(List<Nalog> nalogs, ModelMap model) {
		BigDecimal duguje = BigDecimal.ZERO;
		BigDecimal potrazuje = BigDecimal.ZERO;
		for (Nalog nalog : nalogs) {
			duguje = duguje.add(nalog.getDuguje());
			potrazuje = potrazuje.add(nalog.getPotrazuje());
		}
		model.addAttribute(DUGUJE, duguje);
		model.addAttribute(POTRAZUJE, potrazuje);
	}
	
	public long getTimeZoneOffset(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		long timeOffset = 0l;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("timezoneoffset")) {
					timeOffset = Long.valueOf(cookie.getValue());
					break;
				}
			}
		}
		return timeOffset;
	}
}