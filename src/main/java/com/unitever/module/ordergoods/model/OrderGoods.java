package com.unitever.module.ordergoods.model;

import java.io.Serializable;

import com.unitever.module.goods.model.Goods;
import com.unitever.module.order.model.Order;

public class OrderGoods implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Order order;
	private Goods goods;
	private String price;
	private String count;
	private String state;
	private String ft;
	private String lt;
	private String fu;
	private String lu;
	
	private float totalPrice;
	/**未评价*/
	public static final String ORDERGOODS_STATE_UNEVALUATION = "0";
	/**已评价*/
	public static final String ORDERGOODS_STATE_EVALUATIONED = "1";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public float getTotalPrice() {
		return Integer.parseInt(count)*Float.parseFloat(price);
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
