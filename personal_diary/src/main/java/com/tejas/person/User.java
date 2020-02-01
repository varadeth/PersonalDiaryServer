package com.tejas.person;

public class User {
	private String username;
	private String name;
	private String password;
	private String contactNo;
	private String email;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", password=" + password + ", contactNo=" + contactNo
				+ ", email=" + email + "]";
	}
	public User(String username, String name, String password, String contactNo, String email) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.contactNo = contactNo;
		this.email = email;
	}
	
	
}
