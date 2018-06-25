package com.economist.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economist.db.entity.Preduzece;

public interface PreduzeceRepository extends JpaRepository<Preduzece, Integer> {
	
}
