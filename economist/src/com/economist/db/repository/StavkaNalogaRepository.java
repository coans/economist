package com.economist.db.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.economist.db.entity.Nalog;
import com.economist.db.entity.StavkaNaloga;

public interface StavkaNalogaRepository extends JpaRepository<StavkaNaloga, Integer> {
	
	List<StavkaNaloga> findByNalogOrderByDatumAsc(Nalog nalog);
	@Query("SELECT sum(sn.duguje) FROM StavkaNaloga sn WHERE sn.nalog = ?1")
	BigDecimal getDugujeByNalog(Nalog nalog);
	@Query("SELECT sum(sn.potrazuje) FROM StavkaNaloga sn WHERE sn.nalog = ?1")
	BigDecimal getPotrazujeByNalog(Nalog nalog);
	@Query("SELECT sum(sn.saldo) FROM StavkaNaloga sn WHERE sn.nalog = ?1")
	BigDecimal getSaldoByNalog(Nalog nalog);
}
