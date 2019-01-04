package com.economist.service;

import java.util.List;

import com.economist.db.entity.Agencija;
import com.economist.dto.PreduzeceDTO;

public interface PreduzeceService {
	
	PreduzeceDTO findOne(Integer id);
	List<PreduzeceDTO> findByAgencija(Agencija agencija);
	void save(PreduzeceDTO dto);
}
