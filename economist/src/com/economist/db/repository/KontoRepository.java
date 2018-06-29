package com.economist.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economist.db.entity.Konto;
import com.economist.db.entity.User;

public interface KontoRepository extends JpaRepository<Konto, Integer> {
	public List<Konto> findByUser(User user);
}
