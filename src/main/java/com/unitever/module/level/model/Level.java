package com.unitever.module.level.model;

import java.io.Serializable;
import java.util.List;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.user.model.User;

public class Level implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String proportion;
	private String level;
	private String kind;
	private User user;
	private String ft;
	private String lt;
	private String fu;
	private String lu;
	
	private String kindValue;
	
	private List<Customer> subCustomerList;
	
	/**首次消费*/
	public static final String LEVEL_KIND_FIRST = "1";
	/**返单消费*/
	public static final String LEVEL_KIND_SECOND = "2";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getFt() {
		return ft;
	}
	public void setFt(String ft) {
		this.ft = ft;
	}
	public String getLt() {
		return lt;
	}
	public void setLt(String lt) {
		this.lt = lt;
	}
	public String getFu() {
		return fu;
	}
	public void setFu(String fu) {
		this.fu = fu;
	}
	public String getLu() {
		return lu;
	}
	public void setLu(String lu) {
		this.lu = lu;
	}
	public String getKindValue() {
		if(LEVEL_KIND_FIRST.equals(kind)) {
			return "首次消费";
		} else if(LEVEL_KIND_SECOND.equals(kind)) {
			return "返单消费";
		}
		return kindValue;
	}
	public void setKindValue(String kindValue) {
		this.kindValue = kindValue;
	}
	public List<Customer> getSubCustomerList() {
		return subCustomerList;
	}
	public void setSubCustomerList(List<Customer> subCustomerList) {
		this.subCustomerList = subCustomerList;
	}
}
