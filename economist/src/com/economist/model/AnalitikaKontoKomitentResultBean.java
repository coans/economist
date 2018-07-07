package com.economist.model;

import java.math.BigDecimal;

import com.economist.db.entity.Komitent;
import com.economist.db.entity.Konto;

public class AnalitikaKontoKomitentResultBean {
	private Konto konto;
	private Komitent komitent;
	private BigDecimal duguje;
	private BigDecimal potrazuje;
	private BigDecimal saldo;
	
	public Konto getKonto() {
		return konto;
	}
	public void setKonto(Konto konto) {
		this.konto = konto;
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
	public Komitent getKomitent() {
		return komitent;
	}
	public void setKomitent(Komitent komitent) {
		this.komitent = komitent;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
}
