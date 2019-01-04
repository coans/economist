package com.economist.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.economist.db.entity.StavkaNaloga;

public class StavkaNalogaDTO  implements Serializable {
	private static final long serialVersionUID = 5803943535971375490L;

	private Integer id;
	private Date datum;
	private String opis;
	private BigDecimal duguje;
	private BigDecimal potrazuje;
	private BigDecimal saldo;
	private KontoDTO konto;
	private NalogDTO nalog;
	private KomitentDTO komitent;
	
	public StavkaNalogaDTO() {
		super();
	}

	public StavkaNalogaDTO(Integer id, Date datum, String opis,
			BigDecimal duguje, BigDecimal potrazuje, BigDecimal saldo, KontoDTO konto,
			NalogDTO nalog, KomitentDTO komitent) {
		super();
		this.id = id;
		this.datum = datum;
		this.opis = opis;
		this.duguje = duguje;
		this.potrazuje = potrazuje;
		this.saldo = saldo;
		this.konto = konto;
		this.nalog = nalog;
		
		this.komitent = komitent;
	}
	
	public StavkaNalogaDTO(StavkaNaloga bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.datum = bean.getDatum();
			this.opis = bean.getOpis();
			this.duguje = bean.getDuguje();
			this.potrazuje = bean.getPotrazuje();
			this.saldo = bean.getSaldo();
			this.konto = new KontoDTO(bean.getKonto());
			this.nalog = new NalogDTO(bean.getNalog(), null, null, null);
			this.komitent = new KomitentDTO(bean.getKomitent());
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public BigDecimal getDuguje() {
		return duguje;
	}

	public void setDuguje(BigDecimal duguje) {
		this.duguje = duguje;
	}

	public BigDecimal getPotrazuje() {
		return potrazuje;
	}

	public void setPotrazuje(BigDecimal potrazuje) {
		this.potrazuje = potrazuje;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public KontoDTO getKonto() {
		return konto;
	}

	public void setKonto(KontoDTO konto) {
		this.konto = konto;
	}

	public NalogDTO getNalog() {
		return nalog;
	}

	public void setNalog(NalogDTO nalog) {
		this.nalog = nalog;
	}

	public KomitentDTO getKomitent() {
		return komitent;
	}

	public void setKomitent(KomitentDTO komitent) {
		this.komitent = komitent;
	}
}
