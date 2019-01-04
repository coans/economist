package com.economist.service;

import java.util.List;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.VrstaDokumenta;
import com.economist.dto.VrstaDokumentaDTO;

public interface VrstaDokumentaService {

	VrstaDokumentaDTO findOne(Integer id);
	VrstaDokumenta find(Integer id);
	List<VrstaDokumentaDTO> findByAgencija(Agencija agencija);
	void save(VrstaDokumentaDTO dto);
	Integer findNextSifra(Agencija agencija);
}
