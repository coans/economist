package com.economist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economist.db.entity.Agencija;
import com.economist.db.entity.VrstaDokumenta;
import com.economist.db.repository.VrstaDokumentaRepository;
import com.economist.dto.VrstaDokumentaDTO;
import com.economist.service.AgencijaService;
import com.economist.service.VrstaDokumentaService;

@Service
public class VrstaDokumentaServiceImpl implements VrstaDokumentaService {

	@Autowired
	private VrstaDokumentaRepository vrstaDokumentaRepository;
	@Autowired
	private AgencijaService agencijaService;
	
	@Override
	public VrstaDokumentaDTO findOne(Integer id) {
		VrstaDokumenta vrstaDokumenta = vrstaDokumentaRepository.findOne(id);
		if (vrstaDokumenta != null) {
			return new VrstaDokumentaDTO(vrstaDokumenta);
		}
		return null;
	}

	@Override
	public List<VrstaDokumentaDTO> findByAgencija(Agencija agencija) {
		return mapToDTO(vrstaDokumentaRepository.findByAgencija(agencija));
	}

	@Override
	public void save(VrstaDokumentaDTO dto) {
		VrstaDokumenta bean = new VrstaDokumenta();
		if (dto.getId() != null) {
			bean = vrstaDokumentaRepository.findOne(dto.getId());
		}
		bean.setAgencija(agencijaService.findOne(dto.getAgencijaId()));
		bean.setNaziv(dto.getNaziv());
		bean.setSifra(dto.getSifra());
		
		vrstaDokumentaRepository.save(bean);
	}

	@Override
	public Integer findNextSifra(Agencija agencija) {
		return vrstaDokumentaRepository.findNextSifra(agencija);
	}
	
	private List<VrstaDokumentaDTO> mapToDTO(List<VrstaDokumenta> beans) {
		if (beans != null) {
			List<VrstaDokumentaDTO> result = new ArrayList<VrstaDokumentaDTO>();
			for (VrstaDokumenta vrstaDokumenta : beans) {
				result.add(new VrstaDokumentaDTO(vrstaDokumenta));
			}
			return result;
		}
		return null;
	}

	@Override
	public VrstaDokumenta find(Integer id) {
		return vrstaDokumentaRepository.findOne(id);
	}

}
