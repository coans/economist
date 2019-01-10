package com.economist.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.economist.db.entity.Konto;
import com.economist.dto.NalogDTO;
import com.economist.service.AgencijaService;
import com.economist.service.KontoService;

@Component
public class NalogValidator implements Validator {
	
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
		NalogDTO nalog = (NalogDTO) target;
		if (nalog.getBroj().isEmpty() || nalog.getBroj().length() > 45) {
			errors.rejectValue("broj", "error.nalog.broj.empty");
		}
		if (nalog.getOpis().isEmpty() || nalog.getOpis().length() > 50) {
			errors.rejectValue("opis", "error.nalog.opis.empty");
		}
		if (nalog.getVrstaDokumenta() == null || nalog.getVrstaDokumenta().getId() == null) {
			errors.rejectValue("vrstaDokumenta.id", "error.nalog.vrsta.dokumenta.empty");
		}
		if (nalog.getPreduzece() == null) {
			errors.rejectValue("broj", "error.nalog.preduzece.empty");
		}
		if (nalog.getModified() == null) {
			errors.rejectValue("broj", "error.nalog.datum.modifikovanja.empty");
		}
	}

}
