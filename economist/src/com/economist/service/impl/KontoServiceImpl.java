package com.economist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.Konto;
import com.economist.db.repository.KontoRepository;
import com.economist.dto.KontoDTO;
import com.economist.service.AgencijaService;
import com.economist.service.KontoService;

@Service
public class KontoServiceImpl implements KontoService {

	@Autowired
	private KontoRepository kontoRepository;
	@Autowired
	private AgencijaService agencijaService;
	
	@Override
	public List<KontoDTO> findByAgencija(Agencija agencija) {
		return mapToDTO(kontoRepository.findByAgencija(agencija));
	}

	@Override
	public void save(KontoDTO kontoDTO) {
		Konto bean = new Konto();
		if (kontoDTO.getId() != null) {
			bean = kontoRepository.findOne(kontoDTO.getId());
		}
		bean.setNapomena(kontoDTO.getNapomena());
		bean.setNaziv(kontoDTO.getNaziv());
		bean.setSifra(kontoDTO.getSifra());
		bean.setAgencija(agencijaService.findOne(kontoDTO.getAgencijaId()));
		
		kontoRepository.save(bean);
	}

	@Override
	public KontoDTO findOne(Integer id) {
		Konto konto = kontoRepository.findOne(id);
		if (konto != null) {
			return new KontoDTO(konto);
		}
		return null;
	}

	@Override
	public KontoDTO findBySifraAndAgencija(String sifra, Agencija agencija) {
		Konto konto = kontoRepository.findBySifraAndAgencija(sifra, agencija);
		if (konto != null) {
			return new KontoDTO(konto);
		}
		return null;
	}

	@Override
	public Konto find(Integer id) {
		return kontoRepository.findOne(id);
	}

	@Override
	public List<KontoDTO> findSintetickaKonta(Agencija agencija) {
		return mapToDTO(kontoRepository.findSintetickaKonta(agencija));
	}
	
	private List<KontoDTO> mapToDTO(List<Konto> kontos) {
		if (kontos != null) {
			List<KontoDTO> result = new ArrayList<KontoDTO>();
			for (Konto konto : kontos) {
				result.add(new KontoDTO(konto));
			}
			return result;
		}
		return null;
	}

	@Override
	public List<KontoDTO> findAnalitickaKonta(Agencija agencija) {
		return mapToDTO(kontoRepository.findAnalitickaKonta(agencija));
	}
}
