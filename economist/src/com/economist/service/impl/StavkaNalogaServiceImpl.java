package com.economist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economist.db.entity.Komitent;
import com.economist.db.entity.Nalog;
import com.economist.db.entity.Preduzece;
import com.economist.db.entity.StavkaNaloga;
import com.economist.db.repository.StavkaNalogaRepository;
import com.economist.dto.StavkaNalogaDTO;
import com.economist.service.KomitentService;
import com.economist.service.KontoService;
import com.economist.service.NalogService;
import com.economist.service.StavkaNalogaService;
import com.economist.service.VrstaDokumentaService;

@Service
public class StavkaNalogaServiceImpl implements StavkaNalogaService {

	@Autowired
	private StavkaNalogaRepository stavkaNalogaRepository;
	@Autowired
	private NalogService nalogService;
	@Autowired
	private KontoService kontoService;
	@Autowired
	private KomitentService komitentService;
	@Autowired
	private VrstaDokumentaService vrstaDokumentaService;
	
	@Override
	public StavkaNalogaDTO findOne(Integer id) {
		StavkaNaloga stavkaNaloga = stavkaNalogaRepository.findOne(id);
		if (stavkaNaloga != null) {
			return new StavkaNalogaDTO(stavkaNaloga);
		}
		return null;
	}

	@Override
	public List<StavkaNalogaDTO> findByNalog(Nalog nalog) {
		return mapToDTO(stavkaNalogaRepository.findByNalogOrderByDatumAsc(nalog));
	}

	@Override
	public void save(StavkaNalogaDTO dto) {
		StavkaNaloga bean = new StavkaNaloga();
		if (dto.getId() != null) {
			bean = stavkaNalogaRepository.getOne(dto.getId());
		}
		bean.setDatum(dto.getDatum());
		bean.setDuguje(dto.getDuguje());
		bean.setPotrazuje(dto.getPotrazuje());
		bean.setSaldo(dto.getSaldo());
		bean.setOpis(dto.getOpis());
		if (dto.getKomitent() != null) {
			bean.setKomitent(komitentService.find(dto.getKomitent().getId()));
		} else {
			bean.setKomitent(null);
		}
		bean.setKonto(kontoService.find(dto.getKonto().getId()));
		bean.setNalog(nalogService.find(dto.getNalog().getId()));		
		
		stavkaNalogaRepository.save(bean);
	}
	
	private List<StavkaNalogaDTO> mapToDTO(List<StavkaNaloga> beans) {
		if (beans != null) {
			List<StavkaNalogaDTO> result = new ArrayList<StavkaNalogaDTO>();
			for (StavkaNaloga bean : beans) {
				result.add(new StavkaNalogaDTO(bean));
			}
			return result;
		}
		return null;
	}

	@Override
	public BigDecimal getDugujeByNalog(Nalog nalog) {
		return stavkaNalogaRepository.getDugujeByNalog(nalog);
	}

	@Override
	public BigDecimal getPotrazujeByNalog(Nalog nalog) {
		return stavkaNalogaRepository.getPotrazujeByNalog(nalog);
	}

	@Override
	public BigDecimal getSaldoByNalog(Nalog nalog) {
		return stavkaNalogaRepository.getSaldoByNalog(nalog);
	}

	@Override
	public List<StavkaNalogaDTO> sintetika(String kontoOd, String kontoDo,
			Date datumOd, Date datumDo, Preduzece preduzece) {
		return mapToDTO(stavkaNalogaRepository.sintetika(kontoOd, kontoDo, datumOd, datumDo, preduzece));
	}

	@Override
	public List<StavkaNalogaDTO> sintetika(String kontoOd, String kontoDo,
			Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent) {
		return mapToDTO(stavkaNalogaRepository.sintetika(kontoOd, kontoDo, datumOd, datumDo, preduzece, komitent));
	}

	@Override
	public List<StavkaNalogaDTO> analitika(String kontoOd, String kontoDo,
			Date datumOd, Date datumDo, Preduzece preduzece) {
		return mapToDTO(stavkaNalogaRepository.analitika(kontoOd, kontoDo, datumOd, datumDo, preduzece));
	}

	@Override
	public List<StavkaNalogaDTO> analitika(String kontoOd, String kontoDo,
			Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent) {
		return mapToDTO(stavkaNalogaRepository.analitika(kontoOd, kontoDo, datumOd, datumDo, preduzece, komitent));
	}

	@Override
	public List<StavkaNalogaDTO> kif(Date datumOd, Date datumDo,
			Preduzece preduzece) {
		return mapToDTO(stavkaNalogaRepository.kif(datumOd, datumDo, preduzece));
	}

	@Override
	public List<StavkaNalogaDTO> kuf(Date datumOd, Date datumDo,
			Preduzece preduzece, Komitent komitent) {
		return mapToDTO(stavkaNalogaRepository.kuf(datumOd, datumDo, preduzece, komitent));
	}

	@Override
	public List<StavkaNalogaDTO> kif(Date datumOd, Date datumDo,
			Preduzece preduzece, Komitent komitent) {
		return mapToDTO(stavkaNalogaRepository.kif(datumOd, datumDo, preduzece, komitent));
	}

	@Override
	public List<StavkaNalogaDTO> kuf(Date datumOd, Date datumDo,
			Preduzece preduzece) {
		return mapToDTO(stavkaNalogaRepository.kuf(datumOd, datumDo, preduzece));
	}
}
