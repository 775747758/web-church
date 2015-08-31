package com.unitever.module.user.model;

import java.util.Date;

public class User {
	
	private String id;
	private String gender;
	private String name;
	private String age;
	private String username;
	private String password;
	private String id_church;
	private Date registerDate;
	public User(String id, String gender, String name, String age, String username, String password, String id_church,
			Date registerDate) {
		super();
		this.id = id;
		this.gender = gender;
		this.name = name;
		this.age = age;
		this.username = username;
		this.password = password;
		this.id_church = id_church;
		this.registerDate = registerDate;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", gender=" + gender + ", name=" + name + ", age=" + age + ", username=" + username
				+ ", password=" + password + ", id_church=" + id_church + ", registerDate=" + registerDate + "]";
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
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
	public String getId_church() {
		return id_church;
	}
	public void setId_church(String id_church) {
		this.id_church = id_church;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	
	
	

}
