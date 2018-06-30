package com.economist.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.economist.db.entity.Nalog;
import com.economist.db.entity.Preduzece;

public interface NalogRepository extends JpaRepository<Nalog, Integer> {
	
	@Query("SELECT n FROM Nalog n WHERE n.preduzece = ? AND n.konto.sifra between ? AND ? AND n.datum between ? AND ? ")
	public List<Nalog> analitika(Preduzece p, String sifraOd, String sifraDo, Date datumOd, Date datumDo);
	/*
	@Query("SELECT n FROM Nalog n WHERE n.preduzece = ? AND substring(n.konto.sifra, 1, 1) = ? AND n.datum between ? AND ?")
	public List<Nalog> bilans(Preduzece p, String sifra, Date datumOd, Date datumDo);
	
	@Query("SELECT sum(n.duguje) FROM Nalog n WHERE n.preduzece = ? AND substring(n.konto.sifra, 1, 1) = ? AND n.datum between ? AND ?")
	public BigDecimal getDuguje(Preduzece p, String sifra, Date datumOd, Date datumDo);
	
	@Query("SELECT sum(n.potrazuje) FROM Nalog n WHERE n.preduzece = ? AND substring(n.konto.sifra, 1, 1) = ? AND n.datum between ? AND ?")
	public BigDecimal getPotrazuje(Preduzece p, String sifra, Date datumOd, Date datumDo);*/
}
