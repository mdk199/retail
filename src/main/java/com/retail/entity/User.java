package com.retail.entity;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "user")
public class User {

	@Id
	private String id;
	private String username;
	private String password;
	
	@DBRef(lazy=false)
	private List<Role> roles;

	/**
	 * Default constructor (no args).
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor
	 * 
	 * @param id
	 * @param username
	 * @param password
	 */
	public User(String id, String username, String password, List<Role> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
