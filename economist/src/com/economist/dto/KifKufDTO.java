package com.economist.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class KifKufDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String brojFakture;
	private Date  datum;
	private String komitent;
	private BigDecimal iznos;
	private BigDecimal pdv;
	private BigDecimal ukupno;
	public String getBrojFakture() {
		return brojFakture;
	}
	public void setBrojFakture(String brojFakture) {
		this.brojFakture = brojFakture;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public String getKomitent() {
		return komitent;
	}
	public void setKomitent(String komitent) {
		this.komitent = komitent;
	}
	public BigDecimal getIznos() {
		return iznos;
	}
	public void setIznos(BigDecimal iznos) {
		this.iznos = iznos;
	}
	public BigDecimal getPdv() {
		return pdv;
	}
	public void setPdv(BigDecimal pdv) {
		this.pdv = pdv;
	}
	public BigDecimal getUkupno() {
		return ukupno;
	}
	public void setUkupno(BigDecimal ukupno) {
		this.ukupno = ukupno;
	}
}
