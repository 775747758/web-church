package com.unitever.module.weChat.vo;

import java.util.List;

import com.unitever.module.customer.model.Customer;

public class PersonalCenterVo {

	private Customer customer;
	private String remainCash;
	private String myAgentNum;
	private List<Customer> myAgentList;
	private List<Customer> myFollowerList;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getRemainCash() {
		return remainCash;
	}
	public void setRemainCash(String remainCash) {
		this.remainCash = remainCash;
	}
	public String getMyAgentNum() {
		return myAgentNum;
	}
	public void setMyAgentNum(String myAgentNum) {
		this.myAgentNum = myAgentNum;
	}
	public List<Customer> getMyAgentList() {
		return myAgentList;
	}
	public void setMyAgentList(List<Customer> myAgentList) {
		this.myAgentList = myAgentList;
	}
	public List<Customer> getMyFollowerList() {
		return myFollowerList;
	}
	public void setMyFollowerList(List<Customer> myFollowerList) {
		this.myFollowerList = myFollowerList;
	}
}
