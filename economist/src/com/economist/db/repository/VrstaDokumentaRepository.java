package com.economist.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.VrstaDokumenta;

public interface VrstaDokumentaRepository extends JpaRepository<VrstaDokumenta, Integer> {
	
	@Query("SELECT vd FROM VrstaDokumenta vd WHERE vd.agencija = ? order by vd.sifra asc")
	public List<VrstaDokumenta> findByAgencija(Agencija agencija);

	@Query("SELECT max(vd.sifra) FROM VrstaDokumenta vd WHERE vd.agencija = ?")
	public Integer findNextSifra(Agencija agencija);
}
