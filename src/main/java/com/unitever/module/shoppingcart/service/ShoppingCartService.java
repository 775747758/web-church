package com.unitever.module.shoppingcart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.customer.service.CustomerService;
import com.unitever.module.goods.model.Goods;
import com.unitever.module.goods.service.GoodsService;
import com.unitever.module.shoppingcart.dao.manual.ShoppingCartDAO;
import com.unitever.module.shoppingcart.model.ShoppingCart;
import com.unitever.module.weChat.vo.ShoppingCartVo;

@Service
@Transactional
public class ShoppingCartService {
	/**
	 * 根据用户货物购物车集合
	 * @param customer
	 * @return
	 */
	public List<ShoppingCart> getShoppingCartListWithCustomer(Customer customer) {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setCustomer(customer);
		return shoppingCartDAO.getShoppingCartListWithShoppingCart(shoppingCart);
	}
	/**
	 * 根据购物车获取购物车集合
	 * @param shoppingCart
	 * @return
	 */
	public List<ShoppingCart> getShoppingCartListWithShoppingCart(ShoppingCart shoppingCart) {
		return shoppingCartDAO.getShoppingCartListWithShoppingCart(shoppingCart);
	}
	/**
	 * 根据用户Id获取购物车Vo
	 * @param customerId
	 * @return
	 */
	public ShoppingCartVo getShoppingCartVoWithCustomerId(String customerId) {
		ShoppingCartVo shoppingCartVo = new ShoppingCartVo();
		Customer customer = customerService.getCustomerWithId(customerId);
		shoppingCartVo.setCustomer(customer);
		List<ShoppingCart> shoppingCartList = getShoppingCartListWithCustomer(customer);
		shoppingCartVo.setShoppingCartList(shoppingCartList);
		int count = 0;
		float price = 0.00f;
		String shoppingCartIds = "";
		for(ShoppingCart shoppingCart : shoppingCartList) {
			shoppingCart.getGoods().setPicUrls(goodsService.getPicUrl(shoppingCart.getGoods()));
			count = count + Integer.parseInt(shoppingCart.getCount());
			price = price + (Float.parseFloat(shoppingCart.getGoods().getPrice())*Integer.parseInt(shoppingCart.getCount()));
			if(StringUtils.isBlank(shoppingCartIds)) {
				shoppingCartIds = shoppingCart.getId();
			} else {
				shoppingCartIds = shoppingCartIds + "," + shoppingCart.getId();
			}
		}
		shoppingCartVo.setCount(count+"");
		shoppingCartVo.setOriginalPrice(String.format("%.2f", price));
		if(Customer.CUSTOMER_KIND_AGENT.equals(customer.getKind())) {
			/*if(StringUtils.isNotBlank(customer.getUser().getDiscount())) {
				price = price * Integer.parseInt(customer.getUser().getDiscount())/100;
			}*/
		}
		shoppingCartVo.setPrice(String.format("%.2f", price));
		shoppingCartVo.setShoppingCartIds(shoppingCartIds);
		return shoppingCartVo;
	}
	/**
	 * 根据购物车数量修改购物车数量
	 * @param request
	 * @param shoppingCartIds
	 */
	public void updateWithGoodsIdsCustomerId(HttpServletRequest request, String shoppingCartIds) {
		if(StringUtils.isNotBlank(shoppingCartIds)) {
			for(String shoppingCartId : shoppingCartIds.split(",")) {
				ShoppingCart shoppingCart = shoppingCartDAO.get(shoppingCartId);
				if(shoppingCart!=null) {
					String count = request.getParameter(shoppingCart.getId()+"countName");
					if(StringUtils.isNotBlank(count)) {
						shoppingCart.setCount(count);
						shoppingCartDAO.update(shoppingCart);
					}
				}
			}
		}
	}
	/**
	 * 根据id删除购物车
	 * @param id
	 */
	public void doDelete(String id) {
		shoppingCartDAO.delete(id);
	}
	/**
	 * 添加
	 * @param goodsId
	 * @param customerId
	 */
	public void save(String goodsId, String customerId) {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setCustomer(new Customer(customerId));
		shoppingCart.setGoods(new Goods(goodsId));
		if(shoppingCartDAO.getShoppingCartListWithShoppingCart(shoppingCart).size()==0) {
			shoppingCart.setCount("1");
			shoppingCartDAO.save(shoppingCart);
		}
	}
	/**
	 * 根据用户删除购物车
	 * @param customer
	 */
	public void deleteWithCustomer(Customer customer) {
		for(ShoppingCart shoppingCart : getShoppingCartListWithCustomer(customer)) {
			shoppingCartDAO.delete(shoppingCart.getId());
		}
	}
	@Autowired
	private ShoppingCartDAO shoppingCartDAO;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private GoodsService goodsService;
}