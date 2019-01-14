package com.economist.service;

import java.util.List;

import com.economist.db.entity.Nalog;
import com.economist.db.entity.Preduzece;
import com.economist.dto.NalogDTO;

public interface NalogService {

	NalogDTO findOne(Integer id);
	Nalog find(Integer id);
	List<NalogDTO> findByPreduzece(Preduzece preduzece);
	void save(NalogDTO dto);
//	List<NalogDTO> analitika(Preduzece p, String sifraOd, String sifraDo, Date datumOd, Date datumDo);
//	List<NalogDTO> findByPreduzeceAndParent(Preduzece p, Integer parentId);
//	List<NalogDTO> kif(Preduzece p, Date datumOd, Date datumDo, Komitent komitent);
//	List<NalogDTO> kuf(Preduzece p, Date datumOd, Date datumDo, Komitent komitent);
}
