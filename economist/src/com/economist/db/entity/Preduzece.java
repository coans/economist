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
 * The persistent class for the preduzece database table.
 * 
 */
@Entity
@NamedQuery(name="Preduzece.findAll", query="SELECT p FROM Preduzece p")
public class Preduzece implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@CreationTimestamp
	private Date created;
	private String naziv;
	private String adresa;
	private String telefon;
	private String mobilni;
	private String ziroracun;
	private String jib;
	private Integer iznos;
	private Integer redniBrojFakture;
	private Integer godinaFakture;
	private Boolean mjesecnaFaktura;

	@ManyToOne
	private Agencija agencija;

	public Preduzece() {
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

	public String getJib() {
		return jib;
	}

	public void setJib(String jib) {
		this.jib = jib;
	}

	public Integer getIznos() {
		return iznos;
	}

	public void setIznos(Integer iznos) {
		this.iznos = iznos;
	}

	public Integer getRedniBrojFakture() {
		return redniBrojFakture;
	}

	public void setRedniBrojFakture(Integer redniBrojFakture) {
		this.redniBrojFakture = redniBrojFakture;
	}

	public Integer getGodinaFakture() {
		return godinaFakture;
	}

	public void setGodinaFakture(Integer godinaFakture) {
		this.godinaFakture = godinaFakture;
	}

	public Boolean getMjesecnaFaktura() {
		return mjesecnaFaktura;
	}

	public void setMjesecnaFaktura(Boolean mjesecnaFaktura) {
		this.mjesecnaFaktura = mjesecnaFaktura;
	}	
}