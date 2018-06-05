package com.thera.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thera.db.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
}
