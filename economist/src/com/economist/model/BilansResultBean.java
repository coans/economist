package com.economist.model;

import java.math.BigDecimal;

import com.economist.db.entity.Konto;

public class BilansResultBean {
	private Konto konto;
	private BigDecimal duguje;
	private BigDecimal potrazuje;
	
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
}
