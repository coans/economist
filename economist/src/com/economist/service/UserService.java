package com.economist.service;

import java.util.List;

import com.economist.db.entity.Preduzece;
import com.economist.db.entity.User;
import com.economist.dto.UserDTO;

public interface UserService {

	UserDTO findOne(Integer id);
	User find(Integer id);
	List<UserDTO> findByPreduzece(Preduzece preduzece);
	List<UserDTO> findAll();
	void save(UserDTO user);
}
