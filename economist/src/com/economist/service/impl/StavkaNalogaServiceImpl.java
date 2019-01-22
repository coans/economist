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
import com.economist.dto.KifKufDTO;
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
		StavkaNaloga stavka = new StavkaNaloga();
		if (dto.getId() != null) {
			stavka = stavkaNalogaRepository.getOne(dto.getId());
		}
		stavka.setDatum(dto.getDatum());
		stavka.setDuguje(dto.getDugujeStavka());
		stavka.setPotrazuje(dto.getPotrazujeStavka());
		stavka.setSaldo(dto.getSaldoStavka());
		stavka.setOpis(dto.getOpis());
		stavka.setIdentifikator(dto.getIdentifikator());
		if (dto.getKomitent() != null) {
			stavka.setKomitent(komitentService.find(dto.getKomitent().getId()));
		} else {
			stavka.setKomitent(null);
		}
		stavka.setKonto(kontoService.find(dto.getKontoStavka().getId()));
		stavka.setNalog(nalogService.find(dto.getNalog().getId()));		
		
		stavkaNalogaRepository.save(stavka);
		
		StavkaNaloga protivStavka = new StavkaNaloga();
		if (dto.getId() != null) {
			protivStavka = stavkaNalogaRepository.getOne(dto.getId());
		}
		protivStavka.setDatum(dto.getDatum());
		protivStavka.setDuguje(dto.getDugujeProtivStavka());
		protivStavka.setPotrazuje(dto.getPotrazujeProtivStavka());
		protivStavka.setSaldo(dto.getSaldoProtivStavka());
		protivStavka.setOpis(dto.getOpis());
		protivStavka.setIdentifikator(dto.getIdentifikator());
		if (dto.getKomitent() != null) {
			protivStavka.setKomitent(komitentService.find(dto.getKomitent().getId()));
		} else {
			protivStavka.setKomitent(null);
		}
		protivStavka.setKonto(kontoService.find(dto.getKontoProtivStavka().getId()));
		protivStavka.setNalog(nalogService.find(dto.getNalog().getId()));		
		
		stavkaNalogaRepository.save(protivStavka);
		
		if (dto.getKomitent() != null && dto.getKomitent().getUsistemupdv()) {
			StavkaNaloga pdv = new StavkaNaloga();
			if (dto.getId() != null) {
				pdv = stavkaNalogaRepository.getOne(dto.getId());
			}
			pdv.setDatum(dto.getDatum());
			pdv.setDuguje(dto.getDugujePDV());
			pdv.setPotrazuje(dto.getPotrazujePDV());
			pdv.setSaldo(dto.getSaldoPDV());
			pdv.setOpis(dto.getOpis());
			pdv.setIdentifikator(dto.getIdentifikator());
			if (dto.getKomitent() != null) {
				pdv.setKomitent(komitentService.find(dto.getKomitent().getId()));
			} else {
				pdv.setKomitent(null);
			}
			pdv.setKonto(kontoService.find(dto.getKontoPDV().getId()));
			pdv.setNalog(nalogService.find(dto.getNalog().getId()));		
			
			stavkaNalogaRepository.save(pdv);
		}
		
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
	public List<KifKufDTO> kif(Date datumOd, Date datumDo, Preduzece preduzece) {
		List<String> kifIdentifikators = stavkaNalogaRepository.getKifIdentifikators(datumOd, datumDo, preduzece);
		return mapToKifKuf(kifIdentifikators);
	}

	@Override
	public List<KifKufDTO> kif(Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent) {
		List<String> kifIdentifikators = stavkaNalogaRepository.getKifIdentifikators(datumOd, datumDo, preduzece, komitent);
		return mapToKifKuf(kifIdentifikators);
	}
	
	private List<KifKufDTO> mapToKifKuf(List<String> kifIdentifikators) {
		List<KifKufDTO> result = new ArrayList<>();
		for (String identifikator : kifIdentifikators) {
			KifKufDTO dto = new KifKufDTO();
			List<StavkaNaloga> stavkes = stavkaNalogaRepository.findByIdentifikator(identifikator);

			if (!stavkes.isEmpty()) {
				dto.setDatum(stavkes.get(0).getDatum());
				if (stavkes.get(0).getKomitent() != null) {
					dto.setKomitent(stavkes.get(0).getKomitent().getNaziv());
				}
				if (stavkes.get(2) != null) {
					if (stavkes.get(2).getDuguje() != null && !stavkes.get(2).getDuguje().equals(BigDecimal.ZERO)) {
						dto.setPdv(stavkes.get(2).getDuguje());
					} else if (stavkes.get(2).getPotrazuje() != null && !stavkes.get(2).getPotrazuje().equals(BigDecimal.ZERO)) {
						dto.setPdv(stavkes.get(2).getPotrazuje());
					}
				}
				if (stavkes.get(0) != null && stavkes.get(1) != null) {
					if (stavkes.get(0).getDuguje().compareTo(BigDecimal.ZERO) > 0  && stavkes.get(1).getPotrazuje().compareTo(BigDecimal.ZERO) > 0) {
						if (stavkes.get(0).getDuguje().compareTo(stavkes.get(1).getPotrazuje()) == -1) {
							dto.setIznos(stavkes.get(0).getDuguje());
							dto.setUkupno(stavkes.get(1).getPotrazuje());
						} else {
							dto.setUkupno(stavkes.get(0).getDuguje());
							dto.setIznos(stavkes.get(1).getPotrazuje());
						}
					} else {
						if (stavkes.get(0).getPotrazuje().compareTo(stavkes.get(1).getDuguje()) == -1) {
							dto.setIznos(stavkes.get(0).getPotrazuje());
							dto.setUkupno(stavkes.get(1).getDuguje());
						} else {
							dto.setUkupno(stavkes.get(0).getPotrazuje());
							dto.setIznos(stavkes.get(1).getDuguje());
						}
					}
				}			
				result.add(dto);
			}
		}
		return result;
	}

	@Override
	public List<KifKufDTO> kuf(Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent) {
//		return mapToDTO(stavkaNalogaRepository.kuf(datumOd, datumDo, preduzece, komitent));
		List<String> kufIdentifikators = stavkaNalogaRepository.getKufIdentifikators(datumOd, datumDo, preduzece, komitent);
		return mapToKifKuf(kufIdentifikators);
	}

	@Override
	public List<KifKufDTO> kuf(Date datumOd, Date datumDo,
			Preduzece preduzece) {
//		return mapToDTO(stavkaNalogaRepository.kuf(datumOd, datumDo, preduzece));
		List<String> kufIdentifikators = stavkaNalogaRepository.getKufIdentifikators(datumOd, datumDo, preduzece);
		return mapToKifKuf(kufIdentifikators);
	}
}
