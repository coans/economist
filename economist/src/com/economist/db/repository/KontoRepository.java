package com.economist.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economist.db.entity.Konto;

public interface KontoRepository extends JpaRepository<Konto, Integer> {
	
}
