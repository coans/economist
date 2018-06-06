package com.economist.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.economist.db.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.email = ? AND u.status ='Active'")
	public User findActiveByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.email = ? AND u.status ='Inactive'")
	public User findInactiveByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.email = :email AND u.uuid = :uuid AND u.status = 'Active'")
	public User findActiveByEmailAndUuid(@Param("email") String email, @Param("uuid") String uuid);
	
	@Query("SELECT u FROM User u WHERE u.email = :email AND u.uuid = :uuid AND u.status = 'Inactive'")
	public User findInactiveByEmailAndUuid(@Param("email") String email, @Param("uuid") String uuid);
}
