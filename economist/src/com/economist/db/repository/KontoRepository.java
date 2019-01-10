package com.economist.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.Komitent;
import com.economist.db.entity.Konto;
import com.economist.db.entity.Preduzece;

public interface KontoRepository extends JpaRepository<Konto, Integer> {
//	public List<Konto> findByUser(User user);
	public List<Konto> findByAgencija(Agencija agencija);
	public Konto findBySifraAndAgencija(String sifra, Agencija agencija);
	@Query("SELECT k FROM Konto k WHERE length(k.sifra) = 3 AND k.agencija = ?")
	public List<Konto> findSintetickaKonta(Agencija agencija);
	
	
	/**
	 * 
	 * SELECT k.sifra as Konto, k.naziv as 'Naziv konta', sum(n.duguje) as Duguje, sum(n.potrazuje) as Potrazuje FROM economist.konto k, economist.nalog n 
where k.sifra like '1%' and n.konto_id = k.id group by k.sifra;
	 */
//	@Query("SELECT k, sum(n.duguje), sum(n.potrazuje), sum(n.saldo) FROM Konto k, Nalog n WHERE n.preduzece = ? AND substring(k.sifra, 1, 1) = ? AND n.konto.id = k.id AND n.datum between ? AND ? group by k.sifra")
	@Query("SELECT k FROM Konto k")
	public List<Object> bilans(Preduzece p, String sifra, Date datumOd, Date datumDo);
	
	/**
	 * SELECT k.sifra as Konto, ko.naziv as 'Naziv komitenta', sum(n.duguje) as Duguje, sum(n.potrazuje) as Potrazuje,
sum(n.saldo) as Saldo FROM economist.konto k, economist.nalog n, economist.komitent ko 
where k.sifra like '1%' and n.konto_id = k.id and n.komitent_id = ko.id group by ko.naziv
	 */
//	@Query("SELECT k, ko, sum(n.duguje), sum(n.potrazuje), sum(n.saldo) FROM Konto k, Nalog n, Komitent ko WHERE n.preduzece = ? AND substring(k.sifra, 1, 1) = ? AND n.konto.id = k.id AND n.datum between ? AND ? AND n.komitent = ko.id AND ko = ? group by ko.naziv")
	@Query("SELECT k FROM Konto k")
	public List<Object> analitikaKontoKomitent(Preduzece p, String sifra, Date datumOd, Date datumDo, Komitent komitent);
}
