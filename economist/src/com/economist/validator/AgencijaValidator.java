package com.economist.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.economist.dto.AgencijaDTO;

@Component
public class AgencijaValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return AgencijaDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AgencijaDTO agencija = (AgencijaDTO) target;
		if (agencija.getNaziv().isEmpty() || agencija.getNaziv().length() > 100) {
			errors.rejectValue("naziv", "error.agencija.naziv.empty");
		}
	}

}
