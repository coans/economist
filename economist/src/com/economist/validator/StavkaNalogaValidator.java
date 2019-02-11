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
		if (stavka.getNalog() == null) {
			errors.rejectValue("datum", "error.stavka.naloga.nalog.empty");
		}
		if (stavka.getBrojFakture().length() > 50) {
			errors.rejectValue("brojFakture", "error.stavka.naloga.broj.fakture.empty");
		}
		if (stavka.getKontoStavka() == null || stavka.getKontoStavka().getId().equals(-1)) {
			errors.rejectValue("kontoStavka.id", "error.stavka.naloga.konto.empty");
		}
		if (stavka.getDugujeStavka() == null) {
			errors.rejectValue("dugujeStavka", "error.stavka.naloga.duguje.empty");
		}
		if (stavka.getPotrazujeStavka() == null) {
			errors.rejectValue("potrazujeStavka", "error.stavka.naloga.potrazuje.empty");
		}
		//duguje i potrazuje 0(null)
		if ((stavka.getDugujeStavka() == null || BigDecimal.ZERO.compareTo(stavka.getDugujeStavka()) == 0) && 
				(stavka.getPotrazujeStavka() == null || BigDecimal.ZERO.compareTo(stavka.getPotrazujeStavka()) == 0)) {
			errors.rejectValue("dugujeStavka", "error.stavka.naloga.duguje.potrazuje.empty");
			errors.rejectValue("potrazujeStavka", "error.stavka.naloga.duguje.potrazuje.empty");
		}
		//popunjeno i duguje i potrazuje
		if ((stavka.getDugujeStavka() != null && BigDecimal.ZERO.compareTo(stavka.getDugujeStavka()) < 0) && 
				(stavka.getPotrazujeStavka() != null && BigDecimal.ZERO.compareTo(stavka.getPotrazujeStavka()) < 0)) {
			errors.rejectValue("dugujeStavka", "error.stavka.naloga.duguje.potrazuje.full");
			errors.rejectValue("potrazujeStavka", "error.stavka.naloga.duguje.potrazuje.full");
		}
		
		//validate protivStavka
		if (stavka.getKontoProtivStavka() == null || stavka.getKontoProtivStavka().getId().equals(-1)) {
			errors.rejectValue("kontoProtivStavka.id", "error.stavka.naloga.konto.empty");
		}
		if (stavka.getDugujeProtivStavka() == null) {
			errors.rejectValue("dugujeProtivStavka", "error.stavka.naloga.duguje.empty");
		}
		if (stavka.getPotrazujeProtivStavka() == null) {
			errors.rejectValue("potrazujeProtivStavka", "error.stavka.naloga.potrazuje.empty");
		}
		//duguje i potrazuje 0(null)
		if ((stavka.getDugujeProtivStavka() == null || BigDecimal.ZERO.compareTo(stavka.getDugujeProtivStavka()) == 0) && 
				(stavka.getPotrazujeProtivStavka() == null || BigDecimal.ZERO.compareTo(stavka.getPotrazujeProtivStavka()) == 0)) {
			errors.rejectValue("dugujeProtivStavka", "error.stavka.naloga.duguje.potrazuje.empty");
			errors.rejectValue("potrazujeProtivStavka", "error.stavka.naloga.duguje.potrazuje.empty");
		}
		//popunjeno i duguje i potrazuje
		if ((stavka.getDugujeProtivStavka() != null && BigDecimal.ZERO.compareTo(stavka.getDugujeProtivStavka()) < 0) && 
				(stavka.getPotrazujeProtivStavka() != null && BigDecimal.ZERO.compareTo(stavka.getPotrazujeProtivStavka()) < 0)) {
			errors.rejectValue("dugujeProtivStavka", "error.stavka.naloga.duguje.potrazuje.full");
			errors.rejectValue("potrazujeProtivStavka", "error.stavka.naloga.duguje.potrazuje.full");
		}
		//svi iznosi na dugovnoj strani
		if (stavka.getDugujeStavka() != null && stavka.getDugujeStavka().compareTo(BigDecimal.ZERO) > 0 && 
				stavka.getDugujeProtivStavka() != null && stavka.getDugujeProtivStavka().compareTo(BigDecimal.ZERO) > 0) {
			errors.rejectValue("dugujeStavka", "error.stavka.naloga.dugujeStavke.dugujeProtivStavka");
			errors.rejectValue("dugujeProtivStavka", "error.stavka.naloga.dugujeStavke.dugujeProtivStavka");
		}
		//svi iznosi na potraznoj strani
		if (stavka.getPotrazujeStavka() != null && stavka.getPotrazujeStavka().compareTo(BigDecimal.ZERO) > 0 && 
				stavka.getPotrazujeProtivStavka() != null && stavka.getPotrazujeProtivStavka().compareTo(BigDecimal.ZERO) > 0) {
			errors.rejectValue("potrazujeStavka", "error.stavka.naloga.potrazujeStavke.potrazujeProtivStavka");
			errors.rejectValue("potrazujeProtivStavka", "error.stavka.naloga.potrazujeStavke.potrazujeProtivStavka");
		}
		
		if (stavka.getKomitent() != null && stavka.getKomitent().getId() != -1) {
			if (stavka.getKomitent().getUsistemupdv()) {
				//validate pdv
				if (stavka.getKontoPDV() == null || stavka.getKontoPDV().getId().equals(-1)) {
					errors.rejectValue("kontoPDV.id", "error.stavka.naloga.konto.empty");
				}
				if (stavka.getDugujePDV() == null) {
					errors.rejectValue("dugujePDV", "error.stavka.naloga.duguje.empty");
				}
				if (stavka.getPotrazujePDV() == null) {
					errors.rejectValue("potrazujePDV", "error.stavka.naloga.potrazuje.empty");
				}
				//duguje i potrazuje 0(null)
				if ((stavka.getDugujePDV() == null || BigDecimal.ZERO.compareTo(stavka.getDugujePDV()) == 0) && 
						(stavka.getPotrazujePDV() == null || BigDecimal.ZERO.compareTo(stavka.getPotrazujePDV()) == 0)) {
					errors.rejectValue("dugujePDV", "error.stavka.naloga.duguje.potrazuje.empty");
					errors.rejectValue("potrazujePDV", "error.stavka.naloga.duguje.potrazuje.empty");
				}
				//popunjeno i duguje i potrazuje
				if ((stavka.getDugujePDV() != null && BigDecimal.ZERO.compareTo(stavka.getDugujePDV()) < 0) && 
						(stavka.getPotrazujePDV() != null && BigDecimal.ZERO.compareTo(stavka.getPotrazujePDV()) < 0)) {
					errors.rejectValue("dugujePDV", "error.stavka.naloga.duguje.potrazuje.full");
					errors.rejectValue("potrazujePDV", "error.stavka.naloga.duguje.potrazuje.full");
				}
			}
		}
	}
}
