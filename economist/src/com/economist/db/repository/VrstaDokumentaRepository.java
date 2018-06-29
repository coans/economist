package com.economist.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.economist.db.entity.User;
import com.economist.db.entity.VrstaDokumenta;

public interface VrstaDokumentaRepository extends JpaRepository<VrstaDokumenta, Integer> {
	
	@Query("SELECT vd FROM VrstaDokumenta vd WHERE vd.user = ? order by vd.sifra asc")
	public List<VrstaDokumenta> findByUser(User user);

	@Query("SELECT max(vd.sifra) FROM VrstaDokumenta vd WHERE vd.user = ?")
	public Integer findNextSifra(User user);
}
