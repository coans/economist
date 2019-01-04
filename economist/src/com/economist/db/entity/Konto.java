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
 * The persistent class for the konto database table.
 * 
 */
@Entity
@NamedQuery(name="Konto.findAll", query="SELECT k FROM Konto k")
public class Konto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@CreationTimestamp
	private Date created;
	private String sifra;
	private String naziv;
	private String napomena;

	@ManyToOne
	private Agencija agencija;

	public Konto() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Agencija getAgencija() {
		return agencija;
	}

	public void setAgencija(Agencija agencija) {
		this.agencija = agencija;
	}
	
	public String getSifraNaziv() {
		return this.sifra + " - " + this.naziv;
	}
}