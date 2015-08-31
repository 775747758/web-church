package com.unitever.module.order.dao.manual;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unitever.module.order.model.Order;
import com.unitever.platform.core.dao.BaseDAO;

@Repository
public class OrderDAO extends BaseDAO<Order, String> {

	/**
	 * 根据订单信息查询订单集合
	 * @param order
	 * @return
	 */
	public List<Order> getOrderListWithOrder(Order order) {
		return this.getList("getOrderListWithOrder", order);
	}
}
