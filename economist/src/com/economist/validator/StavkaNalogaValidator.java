package com.economist.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.economist.dto.StavkaNalogaDTO;

@Component
public class StavkaNalogaValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return StavkaNalogaDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		StavkaNalogaDTO stavka = (StavkaNalogaDTO) target;
		if (stavka.getOpis().isEmpty() || stavka.getOpis().length() > 45) {
			errors.rejectValue("opis", "error.stavka.naloga.opis.empty");
		}
		if (stavka.getDatum() == null) {
			errors.rejectValue("datum", "error.stavka.naloga.datum.empty");
		}
		if (stavka.getDuguje() == null) {
			errors.rejectValue("duguje", "error.stavka.naloga.duguje.empty");
		}
		if (stavka.getPotrazuje() == null) {
			errors.rejectValue("potrazuje", "error.stavka.naloga.potrazuje.empty");
		}
		if (stavka.getNalog() == null) {
			errors.rejectValue("datum", "error.stavka.naloga.nalog.empty");
		}
		if (stavka.getKonto() == null) {
			errors.rejectValue("konto", "error.stavka.naloga.konto.empty");
		}
		if ((stavka.getDuguje() == null && stavka.getPotrazuje() == null) || (stavka.getDuguje().equals(BigDecimal.ZERO) && stavka.getPotrazuje().equals(BigDecimal.ZERO))) {
			errors.rejectValue("duguje", "error.stavka.naloga.duguje.potrazuje.empty");
			errors.rejectValue("potrazuje", "error.stavka.naloga.duguje.potrazuje.empty");
		}
	}
}