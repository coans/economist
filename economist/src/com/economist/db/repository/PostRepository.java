package com.economist.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economist.db.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
}
