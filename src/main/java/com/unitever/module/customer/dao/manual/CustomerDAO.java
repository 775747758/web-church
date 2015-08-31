package com.unitever.module.customer.dao.manual;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unitever.module.customer.model.Customer;
import com.unitever.platform.core.dao.BaseDAO;

@Repository
public class CustomerDAO extends BaseDAO<Customer, String> {
	/**
	 * 根据客户信息条件查询客户列表
	 * @param customer
	 * @return
	 */
	public List<Customer> getCustomerListWithCustomer(Customer customer){
		return this.getList("getCustomerListWithCustomer", customer);
	}
}
