package com.unitever.module.goods.dao.manual;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unitever.module.goods.model.Goods;
import com.unitever.platform.core.dao.BaseDAO;

@Repository
public class GoodsDAO extends BaseDAO<Goods, String> {
	
	/**
	 * 根据商品信息查询商品列表
	 * @param goods
	 * @return
	 */
	public List<Goods> getGoodsListWithGoods(Goods goods) {
		return this.getList("getGoodsListWithGoods", goods);
	}
}
