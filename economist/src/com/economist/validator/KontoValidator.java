package com.economist.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.economist.db.entity.Konto;
import com.economist.dto.KontoDTO;
import com.economist.service.AgencijaService;
import com.economist.service.KontoService;
import com.economist.util.ValidatorUtil;

@Component
public class KontoValidator implements Validator {
	
	@Autowired
	private KontoService kontoService;
	@Autowired
	private AgencijaService agencijaService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Konto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		KontoDTO konto = (KontoDTO) target;
		if (konto.getSifra().isEmpty()) {
			errors.rejectValue("sifra", "error.konto.sifra.empty");
		}
		if (konto.getSifra().length() > 6) {
			errors.rejectValue("sifra", "error.konto.sifra.length", new Integer[] {6}, ValidatorUtil.DEFAULT_MESSAGE);
		}
		try {
			Integer.valueOf(konto.getSifra());
		} catch (NumberFormatException e) {
			errors.rejectValue("sifra", "error.konto.sifra.number");
		}
		if (kontoService.findBySifraAndAgencija(konto.getSifra(), agencijaService.findOne(konto.getAgencijaId())) != null) {
			errors.rejectValue("sifra", "error.konto.sifra.exist");
		}
		if (konto.getNaziv().isEmpty() || konto.getNaziv().length() > 500) {
			errors.rejectValue("naziv", "error.konto.naziv", new Integer[] {500}, ValidatorUtil.DEFAULT_MESSAGE);
		}
		if (konto.getNapomena().length() > 50) {
			errors.rejectValue("napomena", "error.konto.napomena", new Integer[] {50}, ValidatorUtil.DEFAULT_MESSAGE);
		}
		if (konto.getAgencijaId() == null) {
			errors.rejectValue("sifra", "error.konto.agencija");
		}
	}

}
