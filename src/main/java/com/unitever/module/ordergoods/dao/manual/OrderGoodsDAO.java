package com.unitever.module.ordergoods.dao.manual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.unitever.module.goods.model.Goods;
import com.unitever.module.ordergoods.model.OrderGoods;
import com.unitever.platform.core.dao.BaseDAO;

@Repository
public class OrderGoodsDAO extends BaseDAO<OrderGoods, String> {

	public List<OrderGoods> getOrderGoodsListWithOrderId(String orderId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		return this.getList("getOrderGoodsListWithOrderId", params);
	}

	public List<OrderGoods> getOrderGoodsListWithGoodsId(String goodsId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("goodsId", goodsId);
		return this.getList("getOrderGoodsListWithGoodsId", params);
	}
}
