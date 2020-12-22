package com.tejas.authentication.model;

import java.io.Serializable;

import com.tejas.person.Person;

public class JwtResponse implements Serializable{
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final int uid;

	public int getUid() {
		return this.uid;
	}

	public JwtResponse(String jwttoken, int uid) {
		this.jwttoken = jwttoken;
		this.uid = uid;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
