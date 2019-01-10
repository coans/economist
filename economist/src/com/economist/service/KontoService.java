package com.economist.service;

import java.util.List;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.Konto;
import com.economist.dto.KontoDTO;


public interface KontoService {

	KontoDTO findOne(Integer id);
	Konto find(Integer id);
	KontoDTO findBySifraAndAgencija(String sifra, Agencija agencija);
	List<KontoDTO> findByAgencija(Agencija agencija);
	void save(KontoDTO konto);
	List<KontoDTO> findSintetickaKonta(Agencija agencija);
}
