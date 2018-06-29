/**
 * Every controller should be extended from this class
 *
 * @author Juraj Bilic
 * @version $Author: jbilic $ ($Revision: 1.2 $) - $Date: 2012-05-21 14:18:54 $
 * @since 1.0.0
 */

package com.economist.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.economist.auth.AuthorizationService;
import com.economist.db.entity.User;

@Controller("/" + BaseController.CONTROLLER)
public class BaseController {

	public static final String CONTROLLER = "base";
	public static final String MODEL_USER = "user";

	private static final String BASEURL = "baseurl";
	private static final String VERISON = "verison";
	
	private static final String VRSTA_DOKUMENTA = "vrstaDokumenta";
	
	@Value("${base.url}")
	protected String baseUrl;
	
	@Value("${product.version}")
	protected String productVersion;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
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
	
	@ModelAttribute(VRSTA_DOKUMENTA)
	public List<String> getQuantity() {
		return Arrays.asList("Ulazni", "Izlazni");
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