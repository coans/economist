package com.economist.service.impl;

import java.util.ArrayList;
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
		Agencija agencija = agencijaRepository.findOne(id);
		if (agencija != null) {
			return new AgencijaDTO(agencija);
		}
		return null;
	}

	@Override
	public List<Agencija> findAll() {
		return agencijaRepository.findAll();
	}

	@Override
	public List<AgencijaDTO> findAllDTO() {
		return mapToDTO(agencijaRepository.findAll());
	}

	@Override
	public void save(AgencijaDTO agencijaDTO) {
		Agencija bean = new Agencija();
		if (agencijaDTO.getId() != null) {
			bean = agencijaRepository.findOne(agencijaDTO.getId());
		}
		bean.setNaziv(agencijaDTO.getNaziv());
		bean.setEmail(agencijaDTO.getEmail());
		
		agencijaRepository.save(bean);
	}
	
	private List<AgencijaDTO> mapToDTO(List<Agencija> agencijas) {
		if (agencijas != null) {
			List<AgencijaDTO> result = new ArrayList<AgencijaDTO>();
			for (Agencija agencija : agencijas) {
				result.add(new AgencijaDTO(agencija));
			}
			return result;
		}
		return null;
	}
}
