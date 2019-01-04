package com.economist.service;

import java.math.BigDecimal;
import java.util.List;

import com.economist.db.entity.Nalog;
import com.economist.dto.StavkaNalogaDTO;

public interface StavkaNalogaService {
	
	StavkaNalogaDTO findOne(Integer id);
	List<StavkaNalogaDTO> findByNalog(Nalog nalog);
	void save(StavkaNalogaDTO dto);
	BigDecimal getDugujeByNalog(Nalog nalog);
	BigDecimal getPotrazujeByNalog(Nalog nalog);
	BigDecimal getSaldoByNalog(Nalog nalog);
}
