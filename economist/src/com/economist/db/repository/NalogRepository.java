package com.economist.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economist.db.entity.Nalog;

public interface NalogRepository extends JpaRepository<Nalog, Integer> {
	
}
