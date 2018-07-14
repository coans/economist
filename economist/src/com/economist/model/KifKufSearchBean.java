package com.economist.model;

import java.util.Date;

import com.economist.db.entity.Komitent;

public class KifKufSearchBean {
	private Date datumOd;
	private Date datumDo;
	private Komitent komitent;
	
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

	public Komitent getKomitent() {
		return komitent;
	}
	public void setKomitent(Komitent komitent) {
		this.komitent = komitent;
	}
}
