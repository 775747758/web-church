package com.unitever.module.weChat.vo;

import java.util.List;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.shoppingcart.model.ShoppingCart;

public class ShoppingCartVo {
	
	private Customer customer;
	private List<ShoppingCart> shoppingCartList;
	private String count;
	private String originalPrice;
	private String price;
	private String shoppingCartIds;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<ShoppingCart> getShoppingCartList() {
		return shoppingCartList;
	}
	public void setShoppingCartList(List<ShoppingCart> shoppingCartList) {
		this.shoppingCartList = shoppingCartList;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getShoppingCartIds() {
		return shoppingCartIds;
	}
	public void setShoppingCartIds(String shoppingCartIds) {
		this.shoppingCartIds = shoppingCartIds;
	}
}