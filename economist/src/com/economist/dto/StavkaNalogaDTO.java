package com.economist.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.economist.db.entity.StavkaNaloga;

public class StavkaNalogaDTO  implements Serializable {
	private static final long serialVersionUID = 5803943535971375490L;

	private Integer idStavka;
	private Integer idProtivStavka;
	private Integer idPDV;
	private Date datum;
	private String opis;
	private KomitentDTO komitent;
	private String identifikator;
	private NalogDTO nalog;
	private BigDecimal dugujeStavka;
	private BigDecimal potrazujeStavka;
	private BigDecimal saldoStavka;
	private KontoDTO kontoStavka;
	private BigDecimal dugujeProtivStavka;
	private BigDecimal potrazujeProtivStavka;
	private BigDecimal saldoProtivStavka;
	private KontoDTO kontoProtivStavka;
	private BigDecimal dugujePDV;
	private BigDecimal potrazujePDV;
	private BigDecimal saldoPDV;
	private KontoDTO kontoPDV;
	
	public StavkaNalogaDTO() {
		super();
	}
/*
	public StavkaNalogaDTO(Integer id, Date datum, String opis, KomitentDTO komitent, String identifikator, NalogDTO nalog,
			BigDecimal dugujeStavka, BigDecimal potrazujeStavka, BigDecimal saldoStavka, KontoDTO kontoStavka,
			BigDecimal dugujeProtivStavka, BigDecimal potrazujeProtivStavka, BigDecimal saldoProtivStavka, KontoDTO kontoProtivStavka,
			BigDecimal dugujePDV, BigDecimal potrazujePDV, BigDecimal saldoPDV, KontoDTO kontoPDV) {
		super();
		this.id = id;
		this.datum = datum;
		this.opis = opis;
		this.nalog = nalog;
		this.identifikator = identifikator;
		this.komitent = komitent;
		this.dugujeStavka = dugujeStavka;
		this.potrazujeStavka = potrazujeStavka;
		this.saldoStavka = saldoStavka;
		this.kontoStavka = kontoStavka;
		this.dugujeProtivStavka = dugujeProtivStavka;
		this.potrazujeProtivStavka = potrazujeProtivStavka;
		this.saldoProtivStavka = saldoProtivStavka;
		this.kontoProtivStavka = kontoProtivStavka;
		this.dugujePDV = dugujePDV;
		this.potrazujePDV = potrazujePDV;
		this.saldoPDV = saldoPDV;
		this.kontoPDV = kontoPDV;
	}
	*/
	public StavkaNalogaDTO(StavkaNaloga bean) {
		if (bean != null) {
			this.idStavka = bean.getId();
			this.datum = bean.getDatum();
			this.opis = bean.getOpis();
			this.nalog = new NalogDTO(bean.getNalog(), null, null, null);
			this.komitent = new KomitentDTO(bean.getKomitent());
			this.identifikator = bean.getIdentifikator();
			this.dugujeStavka = bean.getDuguje();
			this.potrazujeStavka = bean.getPotrazuje();
			this.saldoStavka = bean.getSaldo();
			this.kontoStavka = new KontoDTO(bean.getKonto());
		}
	}

	public Integer getIdStavka() {
		return idStavka;
	}
	public void setIdStavka(Integer idStavka) {
		this.idStavka = idStavka;
	}
	public Integer getIdProtivStavka() {
		return idProtivStavka;
	}
	public void setIdProtivStavka(Integer idProtivStavka) {
		this.idProtivStavka = idProtivStavka;
	}
	public Integer getIdPDV() {
		return idPDV;
	}
	public void setIdPDV(Integer idPDV) {
		this.idPDV = idPDV;
	}
	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public KomitentDTO getKomitent() {
		return komitent;
	}

	public void setKomitent(KomitentDTO komitent) {
		this.komitent = komitent;
	}

	public String getIdentifikator() {
		return identifikator;
	}

	public void setIdentifikator(String identifikator) {
		this.identifikator = identifikator;
	}

	public NalogDTO getNalog() {
		return nalog;
	}

	public void setNalog(NalogDTO nalog) {
		this.nalog = nalog;
	}

	public BigDecimal getDugujeStavka() {
		return dugujeStavka;
	}

	public void setDugujeStavka(BigDecimal dugujeStavka) {
		this.dugujeStavka = dugujeStavka;
	}

	public BigDecimal getPotrazujeStavka() {
		return potrazujeStavka;
	}

	public void setPotrazujeStavka(BigDecimal potrazujeStavka) {
		this.potrazujeStavka = potrazujeStavka;
	}

	public BigDecimal getSaldoStavka() {
		return saldoStavka;
	}

	public void setSaldoStavka(BigDecimal saldoStavka) {
		this.saldoStavka = saldoStavka;
	}

	public KontoDTO getKontoStavka() {
		return kontoStavka;
	}

	public void setKontoStavka(KontoDTO kontoStavka) {
		this.kontoStavka = kontoStavka;
	}

	public BigDecimal getDugujeProtivStavka() {
		return dugujeProtivStavka;
	}

	public void setDugujeProtivStavka(BigDecimal dugujeProtivStavka) {
		this.dugujeProtivStavka = dugujeProtivStavka;
	}

	public BigDecimal getPotrazujeProtivStavka() {
		return potrazujeProtivStavka;
	}

	public void setPotrazujeProtivStavka(BigDecimal potrazujeProtivStavka) {
		this.potrazujeProtivStavka = potrazujeProtivStavka;
	}

	public BigDecimal getSaldoProtivStavka() {
		return saldoProtivStavka;
	}

	public void setSaldoProtivStavka(BigDecimal saldoProtivStavka) {
		this.saldoProtivStavka = saldoProtivStavka;
	}

	public KontoDTO getKontoProtivStavka() {
		return kontoProtivStavka;
	}

	public void setKontoProtivStavka(KontoDTO kontoProtivStavka) {
		this.kontoProtivStavka = kontoProtivStavka;
	}

	public BigDecimal getDugujePDV() {
		return dugujePDV;
	}

	public void setDugujePDV(BigDecimal dugujePDV) {
		this.dugujePDV = dugujePDV;
	}

	public BigDecimal getPotrazujePDV() {
		return potrazujePDV;
	}

	public void setPotrazujePDV(BigDecimal potrazujePDV) {
		this.potrazujePDV = potrazujePDV;
	}

	public BigDecimal getSaldoPDV() {
		return saldoPDV;
	}

	public void setSaldoPDV(BigDecimal saldoPDV) {
		this.saldoPDV = saldoPDV;
	}

	public KontoDTO getKontoPDV() {
		return kontoPDV;
	}

	public void setKontoPDV(KontoDTO kontoPDV) {
		this.kontoPDV = kontoPDV;
	}
}
