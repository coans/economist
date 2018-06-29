package com.economist.model;

import java.util.Date;

import com.economist.db.entity.Konto;

public class AnalitikaSearchBean {
	private Konto kontoOd;
	private Konto kontoDo;
	private Date datumOd;
	private Date datumDo;
	
	public Konto getKontoOd() {
		return kontoOd;
	}
	public void setKontoOd(Konto kontoOd) {
		this.kontoOd = kontoOd;
	}
	public Konto getKontoDo() {
		return kontoDo;
	}
	public void setKontoDo(Konto kontoDo) {
		this.kontoDo = kontoDo;
	}
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
}
