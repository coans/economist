package com.economist.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.economist.dto.PreduzeceDTO;

@Component
public class PreduzeceValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PreduzeceDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PreduzeceDTO preduzece = (PreduzeceDTO) target;
		if (preduzece.getNaziv().isEmpty() || preduzece.getNaziv().length() > 80) {
			errors.rejectValue("naziv", "error.preduzece.naziv.empty");
		}
		if (preduzece.getAdresa().isEmpty() || preduzece.getAdresa().length() > 150) {
			errors.rejectValue("adresa", "error.preduzece.adresa.empty");
		}
		if (preduzece.getTelefon().isEmpty() || preduzece.getTelefon().length() > 45) {
			errors.rejectValue("telefon", "error.preduzece.telefon.empty");
		}
		if (preduzece.getMobilni().isEmpty() || preduzece.getMobilni().length() > 45) {
			errors.rejectValue("mobilni", "error.preduzece.mobilni.empty");
		}
		if (preduzece.getZiroracun().isEmpty() || preduzece.getZiroracun().length() > 45) {
			errors.rejectValue("ziroracun", "error.preduzece.ziroracun.empty");
		}
		if (preduzece.getAgencija() == null) {
			errors.rejectValue("agencija.id", "error.preduzece.agencija.empty");
		}
	}

}
