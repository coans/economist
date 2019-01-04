package com.economist.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economist.db.entity.Agencija;

public interface AgencijaRepository extends JpaRepository<Agencija, Integer> {
	
}
