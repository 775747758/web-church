package com.unitever.module.withdrawCash.model;

import java.io.Serializable;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.user.model.User;

public class WithdrawCash implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private Customer customer;
	private String accountName;
	private String accountNum;
	private String accountBank;
	private String money;
	private String date;
	private String state;
	private User user;
	private String ft;
	private String lt;
	private String fu;
	private String lu;
	
	private String stateValue;
	private String startDate;
	private String endDate;
	
	/**未完成*/
	public static final String WITHDRAWCASH_STATE_UNFINSHED = "0";
	/**已完成*/
	public static final String WITHDRAWCASH_STATE_FINSHED = "1";
	
	public WithdrawCash() {
		super();
	}
	public WithdrawCash(String id, String code, Customer customer,
			String money, String date, String state) {
		super();
		this.id = id;
		this.code = code;
		this.customer = customer;
		this.money = money;
		this.date = date;
		this.state = state;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getAccountBank() {
		return accountBank;
	}
	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateValue() {
		if(WITHDRAWCASH_STATE_UNFINSHED.equals(state)) {
			return "未完成";
		} else if(WITHDRAWCASH_STATE_FINSHED.equals(state)) {
			return "已完成";
		}
		return stateValue;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
}