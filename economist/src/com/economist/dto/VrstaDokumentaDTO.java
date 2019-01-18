package com.economist.dto;

import java.io.Serializable;
import java.util.Date;

import com.economist.db.entity.VrstaDokumenta;

public class VrstaDokumentaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date created;
	private Integer sifra;
	private String naziv;
	private Integer agencijaId;
	
	public VrstaDokumentaDTO() {
		super();
	}
	
	public VrstaDokumentaDTO(Integer id, Date created, Integer sifra, String naziv, Integer agencijaId) {
		super();
		this.id = id;
		this.created = created;
		this.sifra = sifra;
		this.naziv = naziv;
		this.agencijaId = agencijaId;
	}
	
	public VrstaDokumentaDTO(VrstaDokumenta bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.created = bean.getCreated();
			this.sifra = bean.getSifra();
			this.naziv = bean.getNaziv();
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

	public Integer getSifra() {
		return sifra;
	}

	public void setSifra(Integer sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Integer getAgencijaId() {
		return agencijaId;
	}

	public void setAgencijaId(Integer agencijaId) {
		this.agencijaId = agencijaId;
	}
}
