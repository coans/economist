package com.economist.service;

import java.util.List;

import com.economist.db.entity.Agencija;
import com.economist.dto.AgencijaDTO;

public interface AgencijaService {

	Agencija findOne(int id);
	AgencijaDTO findOneDTO(int id);
	List<Agencija> findAll();
	List<AgencijaDTO> findAllDTO();
	void save(AgencijaDTO agencijaDTO);	
}
