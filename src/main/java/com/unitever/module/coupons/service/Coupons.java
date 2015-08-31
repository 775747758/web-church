package com.unitever.module.coupons.service;

import java.io.Serializable;

import com.unitever.module.customer.model.Customer;

public class Coupons implements Serializable {

	private String id;
	private Customer customer;
	private String code;
	private String price;
	private String status;
	private String ft;
	private String lt;
	private String fu;
	private String lu;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
}
