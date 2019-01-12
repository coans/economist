package com.economist.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.economist.dto.KomitentDTO;

@Component
public class KomitentValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return KomitentDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		KomitentDTO komitent = (KomitentDTO) target;
		if (komitent.getNaziv().isEmpty() || komitent.getNaziv().length() > 100) {
			errors.rejectValue("naziv", "error.komitent.naziv.empty");
		}
		if (komitent.getMesto().isEmpty() || komitent.getMesto().length() > 100) {
			errors.rejectValue("mesto", "error.komitent.mjesto.empty");
		}
		if (komitent.getAdresa().isEmpty() || komitent.getAdresa().length() > 200) {
			errors.rejectValue("adresa", "error.komitent.adresa.empty");
		}
		if (komitent.getZiroracun().length() > 50) {
			errors.rejectValue("ziroracun", "error.komitent.ziroracun.length");
		}
		if (komitent.getNapomena().length() > 200) {
			errors.rejectValue("napomena", "error.komitent.napomena.length");
		}
		if (komitent.getTelefon().length() > 45) {
			errors.rejectValue("telefon", "error.komitent.telefon.length");
		}
		if (komitent.getLokacija().isEmpty()) {
			errors.rejectValue("lokacija", "error.komitent.lokacija.empty");
		}
		if (komitent.getAgencijaId() == null) {
			errors.rejectValue("naziv", "error.komitent.agencija.empty");
		}
	}

}
