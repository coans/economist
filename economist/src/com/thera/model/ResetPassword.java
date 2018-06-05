package com.thera.model;

public class ResetPassword {
	
	private String email;
	private String id;
	private String password;
	private String repassword;
	
	public ResetPassword() {
	}
	
	public ResetPassword(String email, String id) {
		this.email = email;
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
}
