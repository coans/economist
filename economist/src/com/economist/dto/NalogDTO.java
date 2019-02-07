package com.economist.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.economist.db.entity.Nalog;

public class NalogDTO  implements Serializable {
	private static final long serialVersionUID = 5803943535971375490L;

	private Integer id;
	private String broj;
	private Date created;
	private Date modified;
	private String opis;
	private String napomena;
	private Integer zakljucan;
	private PreduzeceDTO preduzece;
	private VrstaDokumentaDTO vrstaDokumenta;
	private BigDecimal duguje;
	private BigDecimal potrazuje;
	private BigDecimal saldo;
	
	public NalogDTO() {
		super();
	}

	public NalogDTO(Integer id, String broj, Date created, String opis,
			String napomena, VrstaDokumentaDTO vrstaDokumenta, PreduzeceDTO preduzece, Integer zakljucan, Date modified) {
		super();
		this.id = id;
		this.broj = broj;
		this.created = created;
		this.opis = opis;
		this.napomena = napomena;
		this.preduzece = preduzece;
		this.zakljucan = zakljucan;
		this.modified = modified;
		this.vrstaDokumenta = vrstaDokumenta;
	}
	
	public NalogDTO(Nalog bean, BigDecimal duguje, BigDecimal potrazuje, BigDecimal saldo) {
		if (bean != null) {
			this.id = bean.getId();
			this.broj = bean.getBroj();
			this.created = bean.getCreated();
			this.opis = bean.getOpis();
			this.napomena = bean.getNapomena();
			this.preduzece = new PreduzeceDTO(bean.getPreduzece());
			this.zakljucan = bean.getZakljucan();
			this.modified = bean.getModified();
			this.vrstaDokumenta = new VrstaDokumentaDTO(bean.getVrstaDokumenta());
			this.duguje = duguje == null ? BigDecimal.ZERO : duguje;
			this.potrazuje = potrazuje == null ? BigDecimal.ZERO : potrazuje;
			this.saldo = saldo == null ? BigDecimal.ZERO : saldo;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
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

	public PreduzeceDTO getPreduzece() {
		return preduzece;
	}

	public void setPreduzece(PreduzeceDTO preduzece) {
		this.preduzece = preduzece;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public VrstaDokumentaDTO getVrstaDokumenta() {
		return vrstaDokumenta;
	}

	public void setVrstaDokumenta(VrstaDokumentaDTO vrstaDokumenta) {
		this.vrstaDokumenta = vrstaDokumenta;
	}

	public Integer getZakljucan() {
		return zakljucan;
	}

	public void setZakljucan(Integer zakljucan) {
		this.zakljucan = zakljucan;
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
}
