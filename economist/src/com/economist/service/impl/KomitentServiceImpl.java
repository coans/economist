package com.economist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.Komitent;
import com.economist.db.repository.KomitentRepository;
import com.economist.dto.KomitentDTO;
import com.economist.service.AgencijaService;
import com.economist.service.KomitentService;

@Service
public class KomitentServiceImpl implements KomitentService {

	@Autowired
	private KomitentRepository komitentRepository;
	@Autowired
	private AgencijaService agencijaService;
	
	@Override
	public KomitentDTO findOne(Integer id) {
		Komitent komitent = komitentRepository.findOne(id);
		if (komitent != null) {
			return new KomitentDTO(komitent);
		}
		return null;
	}

	@Override
	public List<KomitentDTO> findByAgencija(Agencija agencija) {
		return komitentRepository.findByAgencija(agencija);
	}

	@Override
	public void save(KomitentDTO komitentDTO) {
		Komitent bean = new Komitent();
		if (komitentDTO.getId() != null) {
			bean = komitentRepository.findOne(komitentDTO.getId());
		}
		bean.setAdresa(komitentDTO.getAdresa());
		bean.setAgencija(agencijaService.findOne(komitentDTO.getAgencijaId()));
		bean.setMesto(komitentDTO.getMesto());
		bean.setNapomena(komitentDTO.getNapomena());
		bean.setNaziv(komitentDTO.getNaziv());
		bean.setTelefon(komitentDTO.getTelefon());
		bean.setZiroracun(komitentDTO.getZiroracun());
		
		komitentRepository.save(bean);
	}

	@Override
	public Komitent find(Integer id) {
		return komitentRepository.findOne(id);
	}

}
