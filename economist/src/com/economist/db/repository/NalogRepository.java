package com.economist.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.economist.db.entity.Komitent;
import com.economist.db.entity.Nalog;
import com.economist.db.entity.Preduzece;

public interface NalogRepository extends JpaRepository<Nalog, Integer> {
	
	@Query("SELECT n FROM Nalog n WHERE n.preduzece = ? AND n.konto.sifra between ? AND ? AND n.datum between ? AND ? ")
	public List<Nalog> analitika(Preduzece p, String sifraOd, String sifraDo, Date datumOd, Date datumDo);

	@Query("SELECT n FROM Nalog n WHERE n.preduzece = ? AND n.parentId IS NULL")
	public List<Nalog> findParentNalogByPreduzece(Preduzece p);
	
	@Query("SELECT n FROM Nalog n WHERE n.preduzece = ? AND n.parentId = ?")
	public List<Nalog> findByPreduzeceAndParent(Preduzece p, Integer parentId);
	
	@Query("SELECT n FROM Nalog n WHERE n.preduzece = ? AND n.datum between ? AND ? AND n.vrstaDokumenta.prikaziukif = 1 AND n.komitent = ? order by n.datum asc")
	public List<Nalog> kif(Preduzece p, Date datumOd, Date datumDo, Komitent komitent);
	
	@Query("SELECT n FROM Nalog n WHERE n.preduzece = ? AND n.datum between ? AND ? AND n.vrstaDokumenta.prikaziukuf = 1 AND n.komitent = ? order by n.datum asc")
	public List<Nalog> kuf(Preduzece p, Date datumOd, Date datumDo, Komitent komitent);
}
