package com.economist.dto;

import java.io.Serializable;
import java.util.Date;

import com.economist.db.entity.Preduzece;

public class PreduzeceDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date created;
	private String naziv;
	private String adresa;
	private String telefon;
	private String mobilni;
	private String ziroracun;
	private Integer agencijaId;
	
	public PreduzeceDTO() {
		super();
	}

	public PreduzeceDTO(Integer id, Date created, String naziv, String adresa,
			String telefon, String mobilni, String ziroracun, Integer agencijaId) {
		super();
		this.id = id;
		this.created = created;
		this.naziv = naziv;
		this.adresa = adresa;
		this.telefon = telefon;
		this.mobilni = mobilni;
		this.ziroracun = ziroracun;
		this.agencijaId = agencijaId;
	}
	
	public PreduzeceDTO(Preduzece bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.created = bean.getCreated();
			this.naziv = bean.getNaziv();
			this.adresa = bean.getAdresa();
			this.telefon = bean.getTelefon();
			this.mobilni = bean.getMobilni();
			this.ziroracun = bean.getZiroracun();
			this.agencijaId = bean.getAgencija().getId();
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getMobilni() {
		return mobilni;
	}

	public void setMobilni(String mobilni) {
		this.mobilni = mobilni;
	}

	public String getZiroracun() {
		return ziroracun;
	}

	public void setZiroracun(String ziroracun) {
		this.ziroracun = ziroracun;
	}

	public Integer getAgencijaId() {
		return agencijaId;
	}

	public void setAgencijaId(Integer agencijaId) {
		this.agencijaId = agencijaId;
	}
}
