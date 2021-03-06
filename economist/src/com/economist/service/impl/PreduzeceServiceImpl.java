package com.economist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.Preduzece;
import com.economist.db.repository.PreduzeceRepository;
import com.economist.dto.PreduzeceDTO;
import com.economist.service.AgencijaService;
import com.economist.service.PreduzeceService;

@Service
public class PreduzeceServiceImpl implements PreduzeceService {

	@Autowired
	private PreduzeceRepository preduzeceRepository;
	@Autowired
	private AgencijaService agencijaService;
	
	@Override
	public PreduzeceDTO findOne(Integer id) {
		Preduzece preduzece = preduzeceRepository.findOne(id);
		if (preduzece != null) {
			return new PreduzeceDTO(preduzece);
		}
		return null;
	}

	@Override
	public List<PreduzeceDTO> findByAgencija(Agencija agencija) {
		return mapToDTO(preduzeceRepository.findByAgencija(agencija));
	}

	@Override
	public void save(PreduzeceDTO dto) {
		Preduzece bean = new Preduzece();
		if (dto.getId() != null) {
			bean = preduzeceRepository.findOne(dto.getId());
		}
		bean.setAdresa(dto.getAdresa());
		bean.setAgencija(agencijaService.findOne(dto.getAgencija().getId()));
		bean.setMobilni(dto.getMobilni());
		bean.setNaziv(dto.getNaziv());
		bean.setTelefon(dto.getTelefon());
		bean.setZiroracun(dto.getZiroracun());
		
		preduzeceRepository.save(bean);
	}
	
	private List<PreduzeceDTO> mapToDTO(List<Preduzece> preuzeces) {
		if (preuzeces != null) {
			List<PreduzeceDTO> result = new ArrayList<PreduzeceDTO>();
			for (Preduzece preduzece : preuzeces) {
				result.add(new PreduzeceDTO(preduzece));
			}
			return result;
		}
		return null;
	}

	@Override
	public List<PreduzeceDTO> findAll() {
		return mapToDTO(preduzeceRepository.findAll());
	}

	@Override
	public Preduzece find(Integer id) {
		return preduzeceRepository.findOne(id);
	}
}
