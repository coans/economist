package com.economist.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.economist.db.entity.VrstaDokumenta;

@Component
public class VrstaDokumentaValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return VrstaDokumenta.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		VrstaDokumenta vrstaDokumenta = (VrstaDokumenta) target;
		if (vrstaDokumenta.getSifra() == null) {
			errors.rejectValue("naziv", "error.vrsta.dokumenta.sifra.empty");
		}
		if (vrstaDokumenta.getNaziv().isEmpty() || vrstaDokumenta.getNaziv().length() > 45) {
			errors.rejectValue("naziv", "error.vrsta.dokumenta.naziv.empty");
		}
		if (vrstaDokumenta.getAgencija() == null) {
			errors.rejectValue("naziv", "error.vrsta.dokumenta.agencija.empty");
		}
		if (vrstaDokumenta.isPrikaziukif() && vrstaDokumenta.isPrikaziukuf()) {
			errors.rejectValue("prikaziukif", "error.vrsta.dokumenta.kif.kuf");
			errors.rejectValue("prikaziukuf", "error.vrsta.dokumenta.kif.kuf");
		}
	}

}
