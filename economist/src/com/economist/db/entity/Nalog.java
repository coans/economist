package com.economist.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the nalog database table.
 * 
 */
@Entity
@NamedQuery(name="Nalog.findAll", query="SELECT n FROM Nalog n")
public class Nalog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String broj;
	@Column(name = "vrsta_dokumenta")
	private String vrstaDokumenta;
	private Date datum;
	private String opis;
	private String napomena;
	private BigDecimal duguje;
	private BigDecimal potrazuje;

	//bi-directional many-to-one association to Konto
	@ManyToOne
	private Konto konto;
	
	//bi-directional many-to-one association to Preduzece
	@ManyToOne
	private Preduzece preduzece;

	public Nalog() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public String getVrstaDokumenta() {
		return vrstaDokumenta;
	}

	public void setVrstaDokumenta(String vrstaDokumenta) {
		this.vrstaDokumenta = vrstaDokumenta;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	public BigDecimal getDuguje() {
		return duguje;
	}

	public void setDuguje(BigDecimal duguje) {
		this.duguje = duguje;
	}

	public BigDecimal getPotrazuje() {
		return potrazuje;
	}

	public void setPotrazuje(BigDecimal potrazuje) {
		this.potrazuje = potrazuje;
	}

	public Konto getKonto() {
		return konto;
	}

	public void setKonto(Konto konto) {
		this.konto = konto;
	}

	public Preduzece getPreduzece() {
		return preduzece;
	}

	public void setPreduzece(Preduzece preduzece) {
		this.preduzece = preduzece;
	}
}