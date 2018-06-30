package com.economist.model;

import java.util.Date;

import com.economist.db.entity.Konto;

public class AnalitikaSearchBean {
	private Konto kontoOd;
	private Konto kontoDo;
	private Date datumOd;
	private Date datumDo;
	private String kontoOdBilans;
	private String kontoDoBilans;
	
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
	public String getKontoOdBilans() {
		return kontoOdBilans;
	}
	public void setKontoOdBilans(String kontoOdBilans) {
		this.kontoOdBilans = kontoOdBilans;
	}
	public String getKontoDoBilans() {
		return kontoDoBilans;
	}
	public void setKontoDoBilans(String kontoDoBilans) {
		this.kontoDoBilans = kontoDoBilans;
	}
}
