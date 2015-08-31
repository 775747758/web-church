package com.unitever.module.customer.vo;

import java.util.List;
import java.util.Map;

public class CustomerListVo {
	private String total;
	private String count;
	private Map<String, List<String>> data;
	private String next_openid;
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public Map<String, List<String>> getData() {
		return data;
	}
	public void setData(Map<String, List<String>> data) {
		this.data = data;
	}
	public String getNext_openid() {
		return next_openid;
	}
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}
	public CustomerListVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerListVo(String total, String count,
			Map<String, List<String>> data, String next_openid) {
		super();
		this.total = total;
		this.count = count;
		this.data = data;
		this.next_openid = next_openid;
	}
}