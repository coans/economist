package com.economist.dto;

import java.io.Serializable;
import java.util.Date;

import com.economist.db.entity.VrstaDokumenta;

public class VrstaDokumentaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date created;
	private int sifra;
	private String naziv;
	private boolean prikaziukif;
	private boolean prikaziukuf;
	private Integer agencijaId;
	
	public VrstaDokumentaDTO() {
		super();
	}
	
	public VrstaDokumentaDTO(Integer id, Date created, int sifra, String naziv,
			boolean prikaziukif, boolean prikaziukuf, Integer agencijaId) {
		super();
		this.id = id;
		this.created = created;
		this.sifra = sifra;
		this.naziv = naziv;
		this.prikaziukif = prikaziukif;
		this.prikaziukuf = prikaziukuf;
		this.agencijaId = agencijaId;
	}
	
	public VrstaDokumentaDTO(VrstaDokumenta bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.created = bean.getCreated();
			this.sifra = bean.getSifra();
			this.naziv = bean.getNaziv();
			this.prikaziukif = bean.isPrikaziukif();
			this.prikaziukuf = bean.isPrikaziukuf();
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

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public boolean isPrikaziukif() {
		return prikaziukif;
	}

	public void setPrikaziukif(boolean prikaziukif) {
		this.prikaziukif = prikaziukif;
	}

	public boolean isPrikaziukuf() {
		return prikaziukuf;
	}

	public void setPrikaziukuf(boolean prikaziukuf) {
		this.prikaziukuf = prikaziukuf;
	}

	public Integer getAgencijaId() {
		return agencijaId;
	}

	public void setAgencijaId(Integer agencijaId) {
		this.agencijaId = agencijaId;
	}
}
