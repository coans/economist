package com.economist.service;

import java.util.List;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.Preduzece;
import com.economist.dto.PreduzeceDTO;

public interface PreduzeceService {
	
	PreduzeceDTO findOne(Integer id);
	Preduzece find(Integer id);
	List<PreduzeceDTO> findByAgencija(Agencija agencija);
	List<PreduzeceDTO> findAll();
	void save(PreduzeceDTO dto);
}
