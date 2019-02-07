package com.economist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economist.db.entity.Nalog;
import com.economist.db.entity.Preduzece;
import com.economist.db.repository.NalogRepository;
import com.economist.db.repository.PreduzeceRepository;
import com.economist.dto.NalogDTO;
import com.economist.service.NalogService;
import com.economist.service.StavkaNalogaService;
import com.economist.service.VrstaDokumentaService;

@Service
public class NalogServiceImpl implements NalogService {

	@Autowired
	private NalogRepository nalogRepository;
	@Autowired
	private PreduzeceRepository preduzeceRepository;
	@Autowired
	private VrstaDokumentaService vrstaDokumentaService;
	@Autowired
	private StavkaNalogaService snService;
	
	@Override
	public NalogDTO findOne(Integer id) {
		Nalog nalog = nalogRepository.findOne(id);
		if (nalog != null) {
			BigDecimal duguje = snService.getDugujeByNalog(nalog);
			BigDecimal potrazuje = snService.getPotrazujeByNalog(nalog);
			BigDecimal saldo = snService.getSaldoByNalog(nalog);
			
			return new NalogDTO(nalog, duguje, potrazuje,saldo);
		}
		return null;
	}

	@Override
	public List<NalogDTO> findByPreduzece(Preduzece preduzece) {
		return mapToDTO(nalogRepository.findByPreduzece(preduzece));
	}

	@Override
	public void save(NalogDTO dto) {
		Nalog nalog = new Nalog();
		if (dto.getId() != null) {
			nalog = nalogRepository.findOne(dto.getId());
		}
		nalog.setBroj(dto.getBroj());
		nalog.setCreated(dto.getCreated());
		nalog.setModified(dto.getModified());
		nalog.setZakljucan(dto.getZakljucan());
		nalog.setNapomena(dto.getNapomena());
		nalog.setOpis(dto.getOpis());
		nalog.setPreduzece(preduzeceRepository.findOne(dto.getPreduzece().getId()));
		if (dto.getVrstaDokumenta().getId() != null) {
			nalog.setVrstaDokumenta(vrstaDokumentaService.find(dto.getVrstaDokumenta().getId()));
		}
		nalogRepository.save(nalog);
	}

	@Override
	public Nalog find(Integer id) {
		return nalogRepository.findOne(id);
	}

//	@Override
//	public List<NalogDTO> analitika(Preduzece p, String sifraOd,
//			String sifraDo, Date datumOd, Date datumDo) {
//		return mapToDTO(nalogRepository.analitika(p, sifraOd, sifraDo, datumOd, datumDo));
//	}
//
//	@Override
//	public List<NalogDTO> findByPreduzeceAndParent(Preduzece p, Integer parentId) {
//		return mapToDTO(nalogRepository.findByPreduzeceAndParent(p, parentId));
//	}
//
//	@Override
//	public List<NalogDTO> kif(Preduzece p, Date datumOd, Date datumDo,
//			Komitent komitent) {
//		return mapToDTO(nalogRepository.kif(p, datumOd, datumDo, komitent));
//	}
//
//	@Override
//	public List<NalogDTO> kuf(Preduzece p, Date datumOd, Date datumDo,
//			Komitent komitent) {
//		return mapToDTO(nalogRepository.kuf(p, datumOd, datumDo, komitent));
//	}
	
	private List<NalogDTO> mapToDTO(List<Nalog> nalogs) {
		if (nalogs != null) {
			List<NalogDTO> result = new ArrayList<NalogDTO>();
			for (Nalog nalog : nalogs) {
				BigDecimal duguje = snService.getDugujeByNalog(nalog);
				BigDecimal potrazuje = snService.getPotrazujeByNalog(nalog);
				BigDecimal saldo = snService.getSaldoByNalog(nalog);
				
				result.add(new NalogDTO(nalog, duguje, potrazuje, saldo));
			}
			return result;
		}
		return null;
	}

	@Override
	public List<NalogDTO> findByPreduzeceAndStatus(Preduzece preduzece, Integer status) {
		return mapToDTO(nalogRepository.findByPreduzeceAndStatus(preduzece, status));
	}
}
