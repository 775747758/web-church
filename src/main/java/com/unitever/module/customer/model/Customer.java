package com.unitever.module.customer.model;

import java.io.Serializable;

import com.unitever.module.user.model.User;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String weChatNum;
	private Customer parent;
	private String kind;
	private String commission;
	private String cash;
	private String mediaId;
	private User user;
	private String ft;
	private String lt;
	private String fu;
	private String lu;
	
	private String headimgurl;
	private String kindValue;
	private String remainMoney;
	
	/**非代理*/
	public static final String CUSTOMER_KIND_UNAGENT = "0";
	/**代理*/
	public static final String CUSTOMER_KIND_AGENT = "1";
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(String id) {
		super();
		this.id = id;
	}
	public Customer(String id, String name, String weChatNum, Customer parent,
			String kind, String commission, String cash) {
		super();
		this.id = id;
		this.name = name;
		this.weChatNum = weChatNum;
		this.parent = parent;
		this.kind = kind;
		this.commission = commission;
		this.cash = cash;
	}
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
	public String getWeChatNum() {
		return weChatNum;
	}
	public void setWeChatNum(String weChatNum) {
		this.weChatNum = weChatNum;
	}
	public Customer getParent() {
		return parent;
	}
	public void setParent(Customer parent) {
		this.parent = parent;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public String getKindValue() {
		if(CUSTOMER_KIND_UNAGENT.equals(kind)) {
			return "非代理";
		} else if(CUSTOMER_KIND_AGENT.endsWith(kind)) {
			return "代理";
		}
		return kindValue;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
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
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getRemainMoney() {
		return String.format("%.2f", Float.parseFloat(commission)-Float.parseFloat(cash));
	}
	public void setRemainMoney(String remainMoney) {
		this.remainMoney = remainMoney;
	}
}