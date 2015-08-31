package com.unitever.module.ordergoods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitever.module.goods.model.Goods;
import com.unitever.module.goods.service.GoodsService;
import com.unitever.module.order.model.Order;
import com.unitever.module.ordergoods.dao.manual.OrderGoodsDAO;
import com.unitever.module.ordergoods.model.OrderGoods;
import com.unitever.module.shoppingcart.model.ShoppingCart;
import com.unitever.module.shoppingcart.service.ShoppingCartService;
import com.unitever.module.user.model.User;
import com.unitever.module.user.service.UserService;

@Service
@Transactional
public class OrderGoodsService {

	/**
	 * 添加
	 * @param orderGoods
	 */
	public void save(OrderGoods orderGoods) {
		orderGoodsDAO.save(orderGoods);
	}
	
	public List<OrderGoods> getOrderGoodsListWithGoods(Goods goods) {
		return orderGoodsDAO.getOrderGoodsListWithGoodsId(goods.getId());
	}
	
	public void saveWithOrder(Order order) {
		for(ShoppingCart shoppingCart : shoppingCartService.getShoppingCartListWithCustomer(order.getCustomer())) {
			OrderGoods orderGoods = new OrderGoods();
			orderGoods.setOrder(order);
			orderGoods.setCount(shoppingCart.getCount());
			orderGoods.setGoods(shoppingCart.getGoods());
			orderGoods.setState(OrderGoods.ORDERGOODS_STATE_UNEVALUATION);
			if(Order.ORDER_KIND_FIRST.equals(order.getKind())) {
				orderGoods.setPrice(shoppingCart.getGoods().getPrice());
			} else {
				User user = userService.getUserWithId(order.getUser().getId());
				//orderGoods.setPrice(String.format("%.2f", (Float.parseFloat(shoppingCart.getGoods().getPrice())/100)*Integer.parseInt(user.getDiscount())));
			}
			orderGoodsDAO.save(orderGoods);
		}
		
	}
	public OrderGoods getOrderGoodsWithId(String orderGoodsId) {
		OrderGoods orderGoods = orderGoodsDAO.get(orderGoodsId);
		orderGoods.getGoods().setPicUrls(goodsService.getPicUrl(orderGoods.getGoods()));
		return orderGoods;
	}
	public void evaluationed(String orderGoodsId) {
		OrderGoods orderGoods = orderGoodsDAO.get(orderGoodsId);
		orderGoods.setState(OrderGoods.ORDERGOODS_STATE_EVALUATIONED);
		orderGoodsDAO.update(orderGoods);
	}
	
	public List<OrderGoods> getOrderGoodsListWithOrderId(Order order) {
		return orderGoodsDAO.getOrderGoodsListWithOrderId(order.getId());
	}
	@Autowired
	private OrderGoodsDAO orderGoodsDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private GoodsService goodsService;
}