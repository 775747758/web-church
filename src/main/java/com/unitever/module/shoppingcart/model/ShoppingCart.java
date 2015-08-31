package com.unitever.module.shoppingcart.model;

import java.io.Serializable;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.goods.model.Goods;

public class ShoppingCart implements Serializable {

	private String id;
	private Goods goods;
	private Customer customer;
	private String count;
	private String ft;
	private String lt;
	private String fu;
	private String lu;
	
	public ShoppingCart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ShoppingCart(String id, Goods goods, Customer customer,
			String count, String ft, String lt, String fu, String lu) {
		super();
		this.id = id;
		this.goods = goods;
		this.customer = customer;
		this.count = count;
		this.ft = ft;
		this.lt = lt;
		this.fu = fu;
		this.lu = lu;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
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
