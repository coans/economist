package com.economist.dto;

import java.io.Serializable;
import java.util.Date;

import com.economist.db.entity.Konto;

public class KontoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date created;
	private String sifra;
	private String naziv;
	private String napomena;
	private Integer agencijaId;
	private String agencijaName;
	
	public KontoDTO(){};
	
	public KontoDTO(Integer id, Date created, String sifra, String naziv,
			String napomena, Integer agencijaId, String agencijaName) {
		super();
		this.id = id;
		this.created = created;
		this.sifra = sifra;
		this.naziv = naziv;
		this.napomena = napomena;
		this.agencijaId = agencijaId;
		this.agencijaName = agencijaName;
	}
	
	public KontoDTO(Konto bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.created = bean.getCreated();
			this.sifra = bean.getSifra();
			this.naziv = bean.getNaziv();
			this.napomena = bean.getNapomena();
			this.agencijaId = bean.getAgencija().getId();
			this.agencijaName = bean.getAgencija().getNaziv();
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

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	public Integer getAgencijaId() {
		return agencijaId;
	}

	public void setAgencijaId(Integer agencijaId) {
		this.agencijaId = agencijaId;
	}

	public String getAgencijaName() {
		return agencijaName;
	}

	public void setAgencijaName(String agencijaName) {
		this.agencijaName = agencijaName;
	}
	
	public String getSifraNaziv() {
		return this.sifra + " - " + this.naziv;
	}	
}
