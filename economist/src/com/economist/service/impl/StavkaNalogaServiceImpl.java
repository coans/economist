package com.economist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economist.db.entity.Komitent;
import com.economist.db.entity.Konto;
import com.economist.db.entity.Nalog;
import com.economist.db.entity.Preduzece;
import com.economist.db.entity.StavkaNaloga;
import com.economist.db.repository.StavkaNalogaRepository;
import com.economist.dto.KifKufDTO;
import com.economist.dto.StavkaNalogaDTO;
import com.economist.model.BilansResultBean;
import com.economist.model.enums.EnumVrstaStavkaNaloga;
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
		return mapToDTO(stavkaNalogaRepository.findByNalogOrderByIdentifikatorAsc(nalog));
	}

	@Override
	public void save(StavkaNalogaDTO dto) {
		StavkaNaloga stavka = new StavkaNaloga();
		if (dto.getIdStavka() != null) {
			stavka = stavkaNalogaRepository.findOne(dto.getIdStavka());
		}
		stavka.setDatum(dto.getDatum());
		stavka.setDuguje(dto.getDugujeStavka());
		stavka.setPotrazuje(dto.getPotrazujeStavka());
		stavka.setSaldo(dto.getSaldoStavka());
		stavka.setOpis(dto.getOpis());
		stavka.setBrojFakture(dto.getBrojFakture());
		stavka.setIdentifikator(dto.getIdentifikator());
		if (dto.getKomitent() != null) {
			stavka.setKomitent(komitentService.find(dto.getKomitent().getId()));
		} else {
			stavka.setKomitent(null);
		}
		stavka.setKonto(kontoService.find(dto.getKontoStavka().getId()));
		stavka.setNalog(nalogService.find(dto.getNalog().getId()));
		stavka.setVrsta(EnumVrstaStavkaNaloga.STAVKA.toString());
		
		stavkaNalogaRepository.save(stavka);
		
		StavkaNaloga protivStavka = new StavkaNaloga();
		if (dto.getIdProtivStavka() != null) {
			protivStavka = stavkaNalogaRepository.findOne(dto.getIdProtivStavka());
		}
		protivStavka.setDatum(dto.getDatum());
		protivStavka.setDuguje(dto.getDugujeProtivStavka());
		protivStavka.setPotrazuje(dto.getPotrazujeProtivStavka());
		protivStavka.setSaldo(dto.getSaldoProtivStavka());
		protivStavka.setOpis(dto.getOpis());
		protivStavka.setBrojFakture(dto.getBrojFakture());
		protivStavka.setIdentifikator(dto.getIdentifikator());
		if (dto.getKomitent() != null) {
			protivStavka.setKomitent(komitentService.find(dto.getKomitent().getId()));
		} else {
			protivStavka.setKomitent(null);
		}
		protivStavka.setKonto(kontoService.find(dto.getKontoProtivStavka().getId()));
		protivStavka.setNalog(nalogService.find(dto.getNalog().getId()));		
		protivStavka.setVrsta(EnumVrstaStavkaNaloga.PROTIVSTAVKA.toString());
		
		stavkaNalogaRepository.save(protivStavka);
		
		if (dto.getKomitent() != null && dto.getKomitent().getUsistemupdv()) {
			StavkaNaloga pdv = new StavkaNaloga();
			if (dto.getIdPDV() != null) {
				pdv = stavkaNalogaRepository.findOne(dto.getIdPDV());
			}
			pdv.setDatum(dto.getDatum());
			pdv.setDuguje(dto.getDugujePDV());
			pdv.setPotrazuje(dto.getPotrazujePDV());
			pdv.setSaldo(dto.getSaldoPDV());
			pdv.setOpis(dto.getOpis());
			pdv.setBrojFakture(dto.getBrojFakture());
			pdv.setIdentifikator(dto.getIdentifikator());
			if (dto.getKomitent() != null) {
				pdv.setKomitent(komitentService.find(dto.getKomitent().getId()));
			} else {
				pdv.setKomitent(null);
			}
			pdv.setKonto(kontoService.find(dto.getKontoPDV().getId()));
			pdv.setNalog(nalogService.find(dto.getNalog().getId()));
			pdv.setVrsta(EnumVrstaStavkaNaloga.PDV.toString());
			
			stavkaNalogaRepository.save(pdv);
		} else { //izabran je komitent koji nije u sistemu pdv-a
			if (dto.getIdPDV() != null) {
				stavkaNalogaRepository.delete(dto.getIdPDV());
			}
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
/*
	@Override
	public List<StavkaNalogaDTO> sintetika(String kontoOd, String kontoDo,
			Date datumOd, Date datumDo, Preduzece preduzece) {
		return mapToDTO(stavkaNalogaRepository.analitika(kontoOd, kontoDo, datumOd, datumDo, preduzece));
	}

	@Override
	public List<StavkaNalogaDTO> sintetika(String kontoOd, String kontoDo,
			Date datumOd, Date datumDo, Preduzece preduzece, Komitent komitent) {
		return mapToDTO(stavkaNalogaRepository.analitika(kontoOd, kontoDo, datumOd, datumDo, preduzece, komitent));
	}
*/
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
			dto.setPdv(BigDecimal.ZERO);
			List<StavkaNaloga> stavkes = stavkaNalogaRepository.findByIdentifikator(identifikator);
			if (stavkes != null) {
				List<BigDecimal> iznosi = new ArrayList<>();
				for (StavkaNaloga stavkaNaloga : stavkes) {
					dto.setBrojFakture(stavkaNaloga.getBrojFakture());
					dto.setDatum(stavkaNaloga.getDatum());
					//ako imamo komitenta i on je u sistemu pdv onda setujemo pdv
					if (stavkaNaloga.getKomitent() != null) {
						dto.setKomitent(stavkaNaloga.getKomitent().getNaziv());
						if (stavkaNaloga.getKomitent().getUsistemupdv() && "PDV".equals(stavkaNaloga.getVrsta())) {
							if (stavkaNaloga.getDuguje().compareTo(BigDecimal.ZERO) != 0) {
								dto.setPdv(stavkaNaloga.getDuguje());
							} else if (stavkaNaloga.getPotrazuje().compareTo(BigDecimal.ZERO) != 0) {
								dto.setPdv(stavkaNaloga.getPotrazuje());
							}
						}
					}
					
					if (!"PDV".equals(stavkaNaloga.getVrsta())) {
						if (stavkaNaloga.getDuguje().compareTo(BigDecimal.ZERO) == 1) {
							iznosi.add(stavkaNaloga.getDuguje());
						} else if (stavkaNaloga.getPotrazuje().compareTo(BigDecimal.ZERO) == 1) {
							iznosi.add(stavkaNaloga.getPotrazuje());
						}
					}	
				}
				if (iznosi.size() > 1) {
					if (iznosi.get(0).compareTo(iznosi.get(1)) == 1) {
						dto.setUkupno(iznosi.get(0));
						dto.setIznos(iznosi.get(1));
					} else {
						dto.setUkupno(iznosi.get(1));
						dto.setIznos(iznosi.get(0));
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

	@Override
	public String getIdentifikatorById(Integer id) {
		return stavkaNalogaRepository.getIdentifikatorById(id);
	}

	@Override
	public List<StavkaNaloga> findByIdentifikator(String identifikator) {
		return stavkaNalogaRepository.findByIdentifikator(identifikator);
	}

	@Override
	public Map<Integer, List<BilansResultBean>> bilans(String kontoOd, String kontoDo, Date datumOd, Date datumDo, Preduzece preduzece) {
		Map<Integer, List<BilansResultBean>> result = new HashMap<>();
		
		List<Object> stavke = stavkaNalogaRepository.bilans(kontoOd, kontoDo, datumOd, datumDo, preduzece);
		for (Object object : stavke) {
			BilansResultBean bean = new BilansResultBean();
			Object[] objectArray = (Object[]) object;
			bean.setKonto((Konto)objectArray[0]);
			bean.setDuguje((BigDecimal)objectArray[1]);
			bean.setPotrazuje((BigDecimal) objectArray[2]);
			bean.setSaldo((BigDecimal) objectArray[3]);
			
			if (bean.getKonto().getSifra().startsWith("0")) { //konta klase 0
				if (result.get(0) == null) {
					result.put(0, new ArrayList<BilansResultBean>());
				}
				result.get(0).add(bean);
			} else if (bean.getKonto().getSifra().startsWith("1")) {
				if (result.get(1) == null) {
					result.put(1, new ArrayList<BilansResultBean>());
				}
				result.get(1).add(bean);
			} else if (bean.getKonto().getSifra().startsWith("2")) {
				if (result.get(2) == null) {
					result.put(2, new ArrayList<BilansResultBean>());
				}
				result.get(2).add(bean);
			} else if (bean.getKonto().getSifra().startsWith("3")) {
				if (result.get(3) == null) {
					result.put(3, new ArrayList<BilansResultBean>());
				}
				result.get(3).add(bean);
			} else if (bean.getKonto().getSifra().startsWith("4")) {
				if (result.get(4) == null) {
					result.put(4, new ArrayList<BilansResultBean>());
				}
				result.get(4).add(bean);
			} else if (bean.getKonto().getSifra().startsWith("5")) {
				if (result.get(5) == null) {
					result.put(5, new ArrayList<BilansResultBean>());
				}
				result.get(5).add(bean);
			} else if (bean.getKonto().getSifra().startsWith("6")) {
				if (result.get(6) == null) {
					result.put(6, new ArrayList<BilansResultBean>());
				}
				result.get(6).add(bean);
			} else if (bean.getKonto().getSifra().startsWith("7")) {
				if (result.get(7) == null) {
					result.put(7, new ArrayList<BilansResultBean>());
				}
				result.get(7).add(bean);
			} else if (bean.getKonto().getSifra().startsWith("8")) {
				if (result.get(8) == null) {
					result.put(8, new ArrayList<BilansResultBean>());
				}
				result.get(8).add(bean);
			} else if (bean.getKonto().getSifra().startsWith("9")) {
				if (result.get(9) == null) {
					result.put(9, new ArrayList<BilansResultBean>());
				}
				result.get(9).add(bean);
			}
		}
		return result;
	}
}
