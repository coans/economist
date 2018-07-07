package com.economist.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.economist.db.entity.Komitent;
import com.economist.db.entity.User;

public interface KomitentRepository extends JpaRepository<Komitent, Integer> {
	
	@Query("SELECT k FROM Komitent k WHERE k.user = ?")
	public List<Komitent> findByUser(User user);
}
