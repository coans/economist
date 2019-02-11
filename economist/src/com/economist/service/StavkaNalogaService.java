package com.economist.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.economist.db.entity.Komitent;
import com.economist.db.entity.Nalog;
import com.economist.db.entity.Preduzece;
import com.economist.db.entity.StavkaNaloga;
import com.economist.dto.KifKufDTO;
import com.economist.dto.StavkaNalogaDTO;
import com.economist.model.BilansResultBean;

public interface StavkaNalogaService {
	
	StavkaNalogaDTO findOne(Integer id);
	List<StavkaNalogaDTO> findByNalog(Nalog nalog);
	void save(StavkaNalogaDTO dto);
	BigDecimal getDugujeByNalog(Nalog nalog);
	BigDecimal getPotrazujeByNalog(Nalog nalog);
	BigDecimal getSaldoByNalog(Nalog nalog);
	List<StavkaNalogaDTO> analitika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece);
	List<StavkaNalogaDTO> analitika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	List<KifKufDTO> kif(Date datumOd, Date datumDo, Preduzece preduzece);
	List<KifKufDTO> kif(Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	List<KifKufDTO> kuf(Date datumOd, Date datumDo, Preduzece preduzece);
	List<KifKufDTO> kuf(Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	String getIdentifikatorById(Integer id);
	List<StavkaNaloga> findByIdentifikator(String identifikator);
	Map<Integer, List<BilansResultBean>> bilans(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece);
}
