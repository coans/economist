package com.economist.service;

import java.util.List;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.Komitent;
import com.economist.dto.KomitentDTO;

public interface KomitentService {

	KomitentDTO findOne(Integer id);
	Komitent find(Integer id);
	List<KomitentDTO> findByAgencija(Agencija agencija);
	void save(KomitentDTO komitent);
}
