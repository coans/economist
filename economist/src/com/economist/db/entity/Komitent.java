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
 * The persistent class for the komitent database table.
 * 
 */
@Entity
@NamedQuery(name="Komitent.findAll", query="SELECT k FROM Komitent k")
public class Komitent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@CreationTimestamp
	private Date created;
	private String naziv;
	private String mesto;
	private String ziroracun;
	private String adresa;
	private String napomena;
	private String telefon;
	private Boolean usistemupdv;
	private String lokacija;

	@ManyToOne
	private Agencija agencija;

	public Komitent() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
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