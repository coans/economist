package com.economist.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.CreationTimestamp;


/**
 * The persistent class for the VrstaDokumenta database table.
 * 
 */
@Entity
@NamedQuery(name="VrstaDokumenta.findAll", query="SELECT vd FROM VrstaDokumenta vd")
public class VrstaDokumenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@CreationTimestamp
	private Date created;
	private Integer sifra;
	private String naziv;

	//bi-directional many-to-one association to User
	@ManyToOne
	private Agencija agencija;

	public VrstaDokumenta() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSifra() {
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

	public Agencija getAgencija() {
		return agencija;
	}

	public void setAgencija(Agencija agencija) {
		this.agencija = agencija;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}