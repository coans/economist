package com.economist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.economist.db.entity.Preduzece;
import com.economist.db.entity.User;
import com.economist.db.repository.UserRepository;
import com.economist.dto.UserDTO;
import com.economist.model.enums.EnumStatus;
import com.economist.service.PreduzeceService;
import com.economist.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PreduzeceService preduzeceService;
	
	@Override
	public UserDTO findOne(Integer id) {
		User user = userRepository.findOne(id);
		if (user != null) {
			return new UserDTO(user);
		}
		return null;
	}

	@Override
	public List<UserDTO> findByPreduzece(Preduzece preduzece) {
		return mapToDTO(userRepository.findByPreduzece(preduzece));
	}

	@Override
	public void save(UserDTO userDTO) {
		User bean = new User();
		if (userDTO.getId() != null) {
			bean = userRepository.findOne(userDTO.getId());
		}
//		bean.setCreated(userDTO.getCreated());
		bean.setEmail(userDTO.getEmail());
		bean.setFirstName(userDTO.getFirstName());
		bean.setLastName(userDTO.getLastName());
		bean.setPreduzece(preduzeceService.find(userDTO.getPreduzece().getId()));
		bean.setStatus(userDTO.getStatus());
		bean.setUuid(userDTO.getUuid());
		bean.setStatus(EnumStatus.ACTIVE.toString());
		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        String encodedPassword = encoder.encode(userDTO.getPassword());
        bean.setPassword(encodedPassword);
		
		userRepository.save(bean);
	}

	@Override
	public User find(Integer id) {
		return userRepository.findOne(id);
	}

	private List<UserDTO> mapToDTO(List<User> users) {
		if (users != null) {
			List<UserDTO> result = new ArrayList<UserDTO>();
			for (User user : users) {
				result.add(new UserDTO(user));
			}
			return result;
		}
		return null;
	}

	@Override
	public List<UserDTO> findAll() {
		return mapToDTO(userRepository.findAll());
	}
}
