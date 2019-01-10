package com.economist.db.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.economist.db.entity.Komitent;
import com.economist.db.entity.Nalog;
import com.economist.db.entity.Preduzece;
import com.economist.db.entity.StavkaNaloga;

public interface StavkaNalogaRepository extends JpaRepository<StavkaNaloga, Integer> {
	
	List<StavkaNaloga> findByNalogOrderByDatumAsc(Nalog nalog);
	@Query("SELECT sum(sn.duguje) FROM StavkaNaloga sn WHERE sn.nalog = ?1")
	BigDecimal getDugujeByNalog(Nalog nalog);
	@Query("SELECT sum(sn.potrazuje) FROM StavkaNaloga sn WHERE sn.nalog = ?1")
	BigDecimal getPotrazujeByNalog(Nalog nalog);
	@Query("SELECT sum(sn.saldo) FROM StavkaNaloga sn WHERE sn.nalog = ?1")
	BigDecimal getSaldoByNalog(Nalog nalog);
	
	@Query("SELECT sn FROM StavkaNaloga sn WHERE sn.konto.sifra between ? AND ? AND sn.datum between ? AND ? AND sn.nalog.preduzece = ?")
	List<StavkaNaloga> sintetika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece);
	@Query("SELECT sn FROM StavkaNaloga sn WHERE sn.konto.sifra between ? AND ? AND sn.datum between ? AND ? AND sn.nalog.preduzece = ? AND sn.komitent = ?")
	List<StavkaNaloga> sintetika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	
	@Query("SELECT sn FROM StavkaNaloga sn WHERE sn.konto.sifra between ? AND ? AND sn.datum between ? AND ? AND sn.nalog.preduzece = ? AND length(sn.konto.sifra) = 5")
	List<StavkaNaloga> analitika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece);
	@Query("SELECT sn FROM StavkaNaloga sn WHERE sn.konto.sifra between ? AND ? AND sn.datum between ? AND ? AND sn.nalog.preduzece = ? AND sn.komitent = ? AND length(sn.konto.sifra) = 5")
	List<StavkaNaloga> analitika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
}
