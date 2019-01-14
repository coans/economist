package com.economist.model;

import java.util.Date;

import com.economist.dto.KomitentDTO;

public class KifKufSearchBean {
	private Date datumOd;
	private Date datumDo;
	private KomitentDTO komitent;
	
	public Date getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}
	public Date getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}

	public KomitentDTO getKomitent() {
		return komitent;
	}
	public void setKomitent(KomitentDTO komitent) {
		this.komitent = komitent;
	}
}
