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
	private String telefon;
	private String ziroRacun;
	private String jib;
	private String ulica;
	private String naselje;
	private String postanskiBroj;
	private String grad;
	private String odgovornoLice;
	
	public AgencijaDTO() {
		super();
	}
	
	public AgencijaDTO(Integer id, Date created, String naziv, String email, String telefon, String ziroRacun,
			String jib, String ulica, String naselje, String pb, String grad, String ol) {
		super();
		this.id = id;
		this.created = created;
		this.naziv = naziv;
		this.email = email;
		this.telefon = telefon;
		this.ziroRacun = ziroRacun;
		this.jib = jib;
		this.ulica = ulica;
		this.naselje = naselje;
		this.postanskiBroj = pb;
		this.grad = grad;
		this.odgovornoLice = ol;
	}
	
	public AgencijaDTO(Agencija bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.created = bean.getCreated();
			this.naziv = bean.getNaziv();
			this.email = bean.getEmail();
			this.telefon = bean.getTelefon();
			this.ziroRacun = bean.getZiroRacun();
			this.jib = bean.getJib();
			this.ulica = bean.getUlica();
			this.naselje = bean.getNaselje();
			this.postanskiBroj = bean.getPostanskiBroj();
			this.grad = bean.getGrad();
			this.odgovornoLice = bean.getOdgovornoLice();
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

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getZiroRacun() {
		return ziroRacun;
	}

	public void setZiroRacun(String ziroRacun) {
		this.ziroRacun = ziroRacun;
	}

	public String getJib() {
		return jib;
	}

	public void setJib(String jib) {
		this.jib = jib;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getNaselje() {
		return naselje;
	}

	public void setNaselje(String naselje) {
		this.naselje = naselje;
	}

	public String getPostanskiBroj() {
		return postanskiBroj;
	}

	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getOdgovornoLice() {
		return odgovornoLice;
	}

	public void setOdgovornoLice(String odgovornoLice) {
		this.odgovornoLice = odgovornoLice;
	}
}
