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
	
	List<StavkaNaloga> findByNalogOrderByIdentifikatorAsc(Nalog nalog);
	List<StavkaNaloga> findByIdentifikator(String identifikator);
	
	@Query("SELECT sum(sn.duguje) FROM StavkaNaloga sn WHERE sn.nalog = ?1")
	BigDecimal getDugujeByNalog(Nalog nalog);
	
	@Query("SELECT sum(sn.potrazuje) FROM StavkaNaloga sn WHERE sn.nalog = ?1")
	BigDecimal getPotrazujeByNalog(Nalog nalog);
	
	@Query("SELECT sum(sn.saldo) FROM StavkaNaloga sn WHERE sn.nalog = ?1")
	BigDecimal getSaldoByNalog(Nalog nalog);
	
	@Query("SELECT sn FROM StavkaNaloga sn WHERE sn.konto.sifra between ?1 AND ?2 AND sn.datum between ?3 AND ?4 AND sn.nalog.preduzece = ?5")
	List<StavkaNaloga> sintetika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece);
	@Query("SELECT sn FROM StavkaNaloga sn WHERE sn.konto.sifra between ?1 AND ?2 AND sn.datum between ?3 AND ?4 AND sn.nalog.preduzece = ?5 AND sn.komitent = ?6")
	List<StavkaNaloga> sintetika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	
	@Query("SELECT sn FROM StavkaNaloga sn WHERE sn.konto.sifra between ?1 AND ?2 AND sn.datum between ?3 AND ?4 AND sn.nalog.preduzece = ?5 AND length(sn.konto.sifra) = 5")
	List<StavkaNaloga> analitika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece);
	@Query("SELECT sn FROM StavkaNaloga sn WHERE sn.konto.sifra between ?1 AND ?2 AND sn.datum between ?3 AND ?4 AND sn.nalog.preduzece = ?5 AND sn.komitent = ?6 AND length(sn.konto.sifra) = 5")
	List<StavkaNaloga> analitika(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	
	@Query("SELECT distinct(sn.identifikator) FROM StavkaNaloga sn WHERE sn.datum between ? AND ? AND sn.nalog.preduzece = ? AND sn.konto.sifra like '201%' AND sn.duguje != null AND sn.duguje != 0")
	List<String> getKifIdentifikators(Date datumOd, Date datumDo, Preduzece preduzece);
	
	@Query("SELECT distinct(sn.identifikator) FROM StavkaNaloga sn WHERE sn.datum between ? AND ? AND sn.nalog.preduzece = ? AND sn.konto.sifra like '201%' AND sn.komitent = ? AND sn.duguje != null AND sn.duguje != 0")
	List<String> getKifIdentifikators(Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	
	@Query("SELECT distinct(sn.identifikator) FROM StavkaNaloga sn WHERE sn.datum between ? AND ? AND sn.nalog.preduzece = ? AND (sn.konto.sifra like '432%' or sn.konto.sifra like '433%' or sn.konto.sifra like '434%' or sn.konto.sifra like '435%') AND sn.potrazuje != null AND sn.potrazuje != 0")
	List<String> getKufIdentifikators(Date datumOd, Date datumDo, Preduzece preduzece);
	
	@Query("SELECT distinct(sn.identifikator) FROM StavkaNaloga sn WHERE sn.datum between ? AND ? AND sn.nalog.preduzece = ? AND (sn.konto.sifra like '432%' or sn.konto.sifra like '433%' or sn.konto.sifra like '434%' or sn.konto.sifra like '435%') AND sn.komitent = ?  AND sn.potrazuje != null AND sn.potrazuje != 0")
	List<String> getKufIdentifikators(Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent);
	
	@Query("SELECT sn.identifikator FROM StavkaNaloga sn WHERE sn.id = ?")
	String getIdentifikatorById(Integer id);
	
}
