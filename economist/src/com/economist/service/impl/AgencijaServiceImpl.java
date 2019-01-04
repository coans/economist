package com.economist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economist.db.entity.Agencija;
import com.economist.db.repository.AgencijaRepository;
import com.economist.dto.AgencijaDTO;
import com.economist.service.AgencijaService;

@Service
public class AgencijaServiceImpl implements AgencijaService {

	@Autowired
	private AgencijaRepository agencijaRepository;
	
	@Override
	public Agencija findOne(int id) {
		return agencijaRepository.findOne(id);
	}

	@Override
	public AgencijaDTO findOneDTO(int id) {
		//TODO check null pointer
		return new AgencijaDTO(agencijaRepository.findOne(id));
	}

	@Override
	public List<Agencija> findAll() {
		return agencijaRepository.findAll();
	}

	@Override
	public List<AgencijaDTO> findAllDTO() {
		return null;
	}

	@Override
	public void save(AgencijaDTO agencijaDTO) {
		Agencija bean = new Agencija();
		bean.setNaziv(agencijaDTO.getNaziv());
		bean.setId(agencijaDTO.getId());
		
		agencijaRepository.save(bean);
	}
}
