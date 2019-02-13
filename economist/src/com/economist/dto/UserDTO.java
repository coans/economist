package com.economist.dto;

import java.io.Serializable;
import java.util.Date;

import com.economist.db.entity.User;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date created;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String status;
	private String uuid;
	private PreduzeceDTO preduzece;
	
	public UserDTO() {
		super();
	}

	public UserDTO(Integer id, Date created, String email, String firstName,
			String lastName, String password, String status, String uuid,
			PreduzeceDTO preduzece) {
		super();
		this.id = id;
		this.created = created;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.status = status;
		this.uuid = uuid;
		this.preduzece = preduzece;
	}
	
	public UserDTO(User bean) {
		if (bean != null) {
			this.id = bean.getId();
			this.created = bean.getCreated();
			this.email = bean.getEmail();
			this.firstName = bean.getFirstName();
			this.lastName = bean.getLastName();
			this.password = bean.getPassword();
			this.status = bean.getStatus();
			this.uuid = bean.getUuid();
			this.preduzece = new PreduzeceDTO(bean.getPreduzece());
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public PreduzeceDTO getPreduzece() {
		return preduzece;
	}

	public void setPreduzece(PreduzeceDTO preduzece) {
		this.preduzece = preduzece;
	}	
}
