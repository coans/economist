package com.economist.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.economist.db.entity.Konto;
import com.economist.db.entity.Preduzece;
import com.economist.db.entity.User;

public interface KontoRepository extends JpaRepository<Konto, Integer> {
	public List<Konto> findByUser(User user);
	
	/**
	 * 
	 * SELECT k.sifra as Konto, k.naziv as 'Naziv konta', sum(n.duguje) as Duguje, sum(n.potrazuje) as Potrazuje FROM economist.konto k, economist.nalog n 
where k.sifra like '1%' and n.konto_id = k.id group by k.sifra;
	 */
//	@Query("SELECT n FROM Nalog n WHERE n.preduzece = ? AND substring(n.konto.sifra, 1, 1) = ? AND n.datum between ? AND ?")
	@Query("SELECT k, sum(n.duguje), sum(n.potrazuje) FROM Konto k, Nalog n WHERE n.preduzece = ? AND substring(k.sifra, 1, 1) = ? AND n.konto.id = k.id AND n.datum between ? AND ? group by k.sifra")
	public List<Object> bilans(Preduzece p, String sifra, Date datumOd, Date datumDo);
}
