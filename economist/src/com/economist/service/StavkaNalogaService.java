package com.economist.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.economist.db.entity.Komitent;
import com.economist.db.entity.Nalog;
import com.economist.db.entity.Preduzece;
import com.economist.dto.StavkaNalogaDTO;

public interface StavkaNalogaService {
	
	StavkaNalogaDTO findOne(Integer id);
	List<StavkaNalogaDTO> findByNalog(Nalog nalog);
	void save(StavkaNalogaDTO dto);
	BigDecimal getDugujeByNalog(Nalog nalog);
	BigDecimal getPotrazujeByNalog(Nalog nalog);
	BigDecimal getSaldoByNalog(Nalog nalog);
	List<StavkaNalogaDTO> sintetika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece);
	List<StavkaNalogaDTO> sintetika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	List<StavkaNalogaDTO> analitika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece);
	List<StavkaNalogaDTO> analitika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	List<StavkaNalogaDTO> kif(Date datumOd, Date datumDo, Preduzece preduzece);
	List<StavkaNalogaDTO> kif(Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	List<StavkaNalogaDTO> kuf(Date datumOd, Date datumDo, Preduzece preduzece);
	List<StavkaNalogaDTO> kuf(Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
}
