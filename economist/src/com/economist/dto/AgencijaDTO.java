package com.economist.dto;

import java.io.Serializable;
import java.util.Date;

import com.economist.db.entity.Agencija;

public class AgencijaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date created;
	private String naziv;
	
	public AgencijaDTO() {
		super();
	}
	
	public AgencijaDTO(Integer id, Date created, String naziv) {
		super();
		this.id = id;
		this.created = created;
		this.naziv = naziv;
	}
	
	public AgencijaDTO(Agencija bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.created = bean.getCreated();
			this.naziv = bean.getNaziv();
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
}
