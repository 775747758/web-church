package com.unitever.module.shoppingcart.dao.manual;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unitever.module.shoppingcart.model.ShoppingCart;
import com.unitever.platform.core.dao.BaseDAO;

@Repository
public class ShoppingCartDAO extends BaseDAO<ShoppingCart, String> {
	/**
	 * 根据购物车获取购物车集合
	 * @param shoppingCart
	 * @return
	 */
	public List<ShoppingCart> getShoppingCartListWithShoppingCart(ShoppingCart shoppingCart) {
		return this.getList("getShoppingCartListWithShoppingCart", shoppingCart);
	}
}
