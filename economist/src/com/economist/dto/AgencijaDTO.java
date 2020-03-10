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
	private String email;
	
	public AgencijaDTO() {
		super();
	}
	
	public AgencijaDTO(Integer id, Date created, String naziv, String email) {
		super();
		this.id = id;
		this.created = created;
		this.naziv = naziv;
		this.email = email;
	}
	
	public AgencijaDTO(Agencija bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.created = bean.getCreated();
			this.naziv = bean.getNaziv();
			this.email = bean.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
