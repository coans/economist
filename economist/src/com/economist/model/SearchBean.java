package com.economist.model;

import java.util.Date;

import com.economist.dto.KomitentDTO;
import com.economist.dto.KontoDTO;

public class SearchBean {
	private KontoDTO kontoOd;
	private KontoDTO kontoDo;
	private Date datumOd;
	private Date datumDo;
//	private String kontoOdBilans;
//	private String kontoDoBilans;
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
	}/*
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
	}*/
	public KontoDTO getKontoOd() {
		return kontoOd;
	}
	public void setKontoOd(KontoDTO kontoOd) {
		this.kontoOd = kontoOd;
	}
	public KontoDTO getKontoDo() {
		return kontoDo;
	}
	public void setKontoDo(KontoDTO kontoDo) {
		this.kontoDo = kontoDo;
	}
	public KomitentDTO getKomitent() {
		return komitent;
	}
	public void setKomitent(KomitentDTO komitent) {
		this.komitent = komitent;
	}
}
