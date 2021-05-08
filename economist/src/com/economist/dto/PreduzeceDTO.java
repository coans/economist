package com.economist.dto;

import java.io.Serializable;
import java.util.Date;

import com.economist.db.entity.Preduzece;

public class PreduzeceDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date created;
	private String naziv;
	private String adresa;
	private String telefon;
	private String mobilni;
	private String ziroracun;
	private AgencijaDTO agencija;
	private String jib;
	private Integer iznos;
	private Integer redniBrojFakture;
	private Integer godinaFakture;
	private Boolean mjesecnaFaktura;
	
	public PreduzeceDTO() {
		super();
	}

	public PreduzeceDTO(Integer id, Date created, String naziv, String adresa, String jib, Integer redniBrojFakture, Boolean mjesecnaFaktura,
			String telefon, String mobilni, String ziroracun, AgencijaDTO agencija, Integer iznos, Integer godinaFakture) {
		super();
		this.id = id;
		this.created = created;
		this.naziv = naziv;
		this.adresa = adresa;
		this.telefon = telefon;
		this.mobilni = mobilni;
		this.ziroracun = ziroracun;
		this.agencija = agencija;
		this.jib = jib;
		this.iznos = iznos;
		this.redniBrojFakture = redniBrojFakture;
		this.godinaFakture = godinaFakture;
		this.mjesecnaFaktura = mjesecnaFaktura;
	}
	
	public PreduzeceDTO(Preduzece bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.created = bean.getCreated();
			this.naziv = bean.getNaziv();
			this.adresa = bean.getAdresa();
			this.telefon = bean.getTelefon();
			this.mobilni = bean.getMobilni();
			this.ziroracun = bean.getZiroracun();
			this.agencija = new AgencijaDTO(bean.getAgencija());
			this.jib = bean.getJib();
			this.iznos = bean.getIznos();
			this.redniBrojFakture = bean.getRedniBrojFakture();
			this.godinaFakture = bean.getGodinaFakture();
			this.mjesecnaFaktura = bean.getMjesecnaFaktura();
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

	public AgencijaDTO getAgencija() {
		return agencija;
	}

	public void setAgencija(AgencijaDTO agencija) {
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
