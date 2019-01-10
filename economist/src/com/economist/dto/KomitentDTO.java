package com.economist.dto;

import java.io.Serializable;
import java.util.Date;

import com.economist.db.entity.Komitent;

public class KomitentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date created;
	private String naziv;
	private String mesto;
	private String ziroracun;
	private String adresa;
	private String napomena;
	private String telefon;
	private Integer agencijaId;
	private Boolean usistemupdv;
	private String lokacija;
	
	
	public KomitentDTO() {
		super();
	}
	
	public KomitentDTO(Integer id, Date created, String naziv, String mesto,
			String ziroracun, String adresa, String napomena, String telefon,
			Integer agencijaId, Boolean usistemupdv, String lokacija) {
		super();
		this.id = id;
		this.created = created;
		this.naziv = naziv;
		this.mesto = mesto;
		this.ziroracun = ziroracun;
		this.adresa = adresa;
		this.napomena = napomena;
		this.telefon = telefon;
		this.agencijaId = agencijaId;
		this.usistemupdv = usistemupdv;
		this.lokacija = lokacija;
	}

	public KomitentDTO(Komitent bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.created = bean.getCreated();
			this.naziv = bean.getNaziv();
			this.mesto = bean.getMesto();
			this.ziroracun = bean.getZiroracun();
			this.adresa = bean.getAdresa();
			this.napomena = bean.getNapomena();
			this.telefon = bean.getTelefon();
			this.agencijaId = bean.getAgencija().getId();
			this.usistemupdv = bean.getUsistemupdv();
			this.lokacija = bean.getLokacija();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getZiroracun() {
		return ziroracun;
	}

	public void setZiroracun(String ziroracun) {
		this.ziroracun = ziroracun;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public Integer getAgencijaId() {
		return agencijaId;
	}

	public void setAgencijaId(Integer agencijaId) {
		this.agencijaId = agencijaId;
	}

	public Boolean getUsistemupdv() {
		return usistemupdv;
	}

	public void setUsistemupdv(Boolean usistemupdv) {
		this.usistemupdv = usistemupdv;
	}

	public String getLokacija() {
		return lokacija;
	}

	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}
}
