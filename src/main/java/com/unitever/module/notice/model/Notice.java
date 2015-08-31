package com.unitever.module.notice.model;

import java.util.Date;

import com.unitever.module.user.model.User;

public class Notice {
	
	private String id;
	
	private String title;
	
	private String content;
	
	private User publisher;
	
	private Date publishTime;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Notice(String id, String title, String content, User publisher,
			Date publishTime) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.publisher = publisher;
		this.publishTime = publishTime;
	}

	public Notice() {
		super();
	}
	
}
