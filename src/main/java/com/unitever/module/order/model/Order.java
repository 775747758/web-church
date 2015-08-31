package com.unitever.module.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.ordergoods.model.OrderGoods;
import com.unitever.module.user.model.User;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private String kind;
	private Customer customer;
	private String receiver;
	private String receiverPhoneNum;
	private String receiveAddress;
	private String date;
	private String state;
	private String logisticName;
	private String logisticNum;
	private User user;
	private String ft;
	private String lt;
	private String fu;
	private String lu;
	private List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
	
	private String kindValue;
	private String stateValue;
	private String startDate;
	private String endDate;
	
	private float totalPrice;
	private int totalCount;
	
	/**首次消费*/
	public static final String ORDER_KIND_FIRST = "1";
	/**返单消费*/
	public static final String ORDER_KIND_SENCOND = "2";
	
	/**未支付*/
	public static final String ORDER_STATE_UNPAYMENT = "0";
	/**未发货*/
	public static final String ORDER_STATE_UNSEND = "1";
	/**已发货*/
	public static final String ORDER_STATE_UNRECEIVE = "2";
	/**已收货*/
	public static final String ORDER_STATE_RECEIVE = "3";
	/**完成*/
	public static final String ORDER_STATE_FINSHED = "4";
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(String id, String code, String kind, Customer customer,
			String receiver, String receiverPhoneNum, String receiveAddress,
			String date, String state, String logisticNum) {
		super();
		this.id = id;
		this.code = code;
		this.kind = kind;
		this.customer = customer;
		this.receiver = receiver;
		this.receiverPhoneNum = receiverPhoneNum;
		this.receiveAddress = receiveAddress;
		this.date = date;
		this.state = state;
		this.logisticNum = logisticNum;
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
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverPhoneNum() {
		return receiverPhoneNum;
	}
	public void setReceiverPhoneNum(String receiverPhoneNum) {
		this.receiverPhoneNum = receiverPhoneNum;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
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
	public String getLogisticName() {
		return logisticName;
	}
	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}
	public String getLogisticNum() {
		return logisticNum;
	}
	public void setLogisticNum(String logisticNum) {
		this.logisticNum = logisticNum;
	}
	
	public String getKindValue() {
		if(ORDER_KIND_FIRST.equals(kind)) {
			return "首单消费";
		} else if(ORDER_KIND_SENCOND.equals(kind)) {
			return "返单消费";
		}
		return kindValue;
	}
	public String getStateValue() {
		if(ORDER_STATE_UNPAYMENT.equals(state)) {
			return "未支付";
		} else if(ORDER_STATE_UNSEND.equals(state)) {
			return "未发货";
		} else if(ORDER_STATE_UNRECEIVE.equals(state)) {
			return "已发货";
		} else if(ORDER_STATE_RECEIVE.equals(state)) {
			return "已收货";
		} else if(ORDER_STATE_FINSHED.equals(state)) {
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
	public List<OrderGoods> getOrderGoodsList() {
		return orderGoodsList;
	}
	public void setOrderGoodsList(List<OrderGoods> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
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
	public float getTotalPrice() {
		float total = 0.00f;
		for(OrderGoods orderGoods : orderGoodsList) {
			total = total + orderGoods.getTotalPrice();
		}
		return total;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getTotalCount() {
		int count = 0;
		for(OrderGoods orderGoods : orderGoodsList) {
			count = count + Integer.parseInt(orderGoods.getCount());
		}
		return count;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}