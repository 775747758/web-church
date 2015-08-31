package com.unitever.module.weChat.vo;

import java.util.List;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.level.model.Level;

public class AgentCenterVo {

	private Customer customer;
	
	private String myAllAgentNum;
	
	private List<Level> levelList; 
	
	private String myFollowerNum;
	private String myAgentNum;
	private String unAgentNum;
	
	private String unPaymentNum;
	private String unSendNum;
	private String unReceiveNum;
	private String finshedNum;
	private String remainCash;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getMyAllAgentNum() {
		return myAllAgentNum;
	}
	public void setMyAllAgentNum(String myAllAgentNum) {
		this.myAllAgentNum = myAllAgentNum;
	}
	public String getMyFollowerNum() {
		return myFollowerNum;
	}
	public void setMyFollowerNum(String myFollowerNum) {
		this.myFollowerNum = myFollowerNum;
	}
	public List<Level> getLevelList() {
		return levelList;
	}
	public void setLevelList(List<Level> levelList) {
		this.levelList = levelList;
	}
	public String getUnPaymentNum() {
		return unPaymentNum;
	}
	public void setUnPaymentNum(String unPaymentNum) {
		this.unPaymentNum = unPaymentNum;
	}
	public String getUnSendNum() {
		return unSendNum;
	}
	public void setUnSendNum(String unSendNum) {
		this.unSendNum = unSendNum;
	}
	public String getUnReceiveNum() {
		return unReceiveNum;
	}
	public void setUnReceiveNum(String unReceiveNum) {
		this.unReceiveNum = unReceiveNum;
	}
	public String getFinshedNum() {
		return finshedNum;
	}
	public void setFinshedNum(String finshedNum) {
		this.finshedNum = finshedNum;
	}
	public String getMyAgentNum() {
		return myAgentNum;
	}
	public void setMyAgentNum(String myAgentNum) {
		this.myAgentNum = myAgentNum;
	}
	public String getUnAgentNum() {
		return unAgentNum;
	}
	public void setUnAgentNum(String unAgentNum) {
		this.unAgentNum = unAgentNum;
	}
	public String getRemainCash() {
		return remainCash;
	}
	public void setRemainCash(String remainCash) {
		this.remainCash = remainCash;
	}
}