package com.economist.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.Preduzece;

public interface PreduzeceRepository extends JpaRepository<Preduzece, Integer> {
	
	List<Preduzece> findByAgencija(Agencija agencija);
}
