package com.economist.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.Komitent;
import com.economist.dto.KomitentDTO;

public interface KomitentRepository extends JpaRepository<Komitent, Integer> {
	
	public List<KomitentDTO> findByAgencija(Agencija agencija);
}
