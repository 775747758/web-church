package com.unitever.module.picture.model;

import java.util.Date;

import com.unitever.module.user.model.User;

public class Picture {
	
	private String id;
	
	private String title;
	
	private User user;
	
	private Date date;

	public Picture() {
		super();
	}

	public Picture(String id, String title, User user, Date date) {
		super();
		this.id = id;
		this.title = title;
		this.user = user;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
}
