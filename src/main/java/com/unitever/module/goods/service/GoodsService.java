package com.unitever.module.goods.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.goods.dao.manual.GoodsDAO;
import com.unitever.module.goods.model.Goods;
import com.unitever.module.ordergoods.model.OrderGoods;
import com.unitever.module.ordergoods.service.OrderGoodsService;
import com.unitever.module.user.model.User;
import com.unitever.platform.component.attachment.model.Attachment;
import com.unitever.platform.component.attachment.service.AttachmentService;
import com.unitever.platform.component.attachment.util.AttachmentUtil;
import com.unitever.platform.core.common.helper.UserHelper;
import com.unitever.platform.core.dao.Page;

@Service
@Transactional
public class GoodsService {

	/**
	 * 根据用户查询商品列表
	 * @param user
	 * @return
	 */
	public List<Goods> getGoodsListWithUser(User user) {
		Goods goods = new Goods();
		goods.setUser(user);
		List<Goods> goodsList = goodsDAO.getGoodsListWithGoods(goods);
		for(int i=0;i<goodsList.size();i++) {
			goodsList.get(i).setPicUrls(getPicUrl(goodsList.get(i)));
		}
		return goodsList;
	}
	
	public List<Goods> getGoodsListWithUserCustomer(Customer customer) {
		Goods goods = new Goods();
		goods.setUser(customer.getUser());
		if(Customer.CUSTOMER_KIND_UNAGENT.equals(customer.getKind())) {
			goods.setKind(Goods.GOODS_KIND_FIRST);
		}
		List<Goods> goodsList = goodsDAO.getGoodsListWithGoods(goods);
		for(int i=0;i<goodsList.size();i++) {
			goodsList.get(i).setPicUrls(getPicUrl(goodsList.get(i)));
			int totalCount = 0;
			for(OrderGoods orderGoods : orderGoodsService.getOrderGoodsListWithGoods(goodsList.get(i))) {
				totalCount = totalCount + Integer.parseInt(orderGoods.getCount());
			}
			goodsList.get(i).setTotalCount(totalCount+"");
		}
		return goodsList;
	}
	
	public List<String> getPicUrl(Goods goods) {
		List<String> urlList = new ArrayList<String>();
		for(Attachment attachment : attachmentService.getAttachmentsWithOwnerId(goods.getId())) {
			urlList.add(attachment.getPath()+attachment.getServerFilename());
		}
		return urlList;
	}
	/**
	 * 添加
	 * @param goods
	 */
	public void save(Goods goods) {
		goods.setUser(UserHelper.getCurrUser());
		goodsDAO.save(goods);
		AttachmentUtil.bindAttachment(goods);
	}
	/**
	 * 修改
	 * @param goods
	 */
	public void update(Goods goods) {
		goodsDAO.update(goods);
		AttachmentUtil.bindAttachment(goods);
	}
	/**
	 * 根据id删除
	 * @param id
	 */
	public void deleteWithId(String id) {
		goodsDAO.delete(id);
	}
	/**
	 * 根据id获取商品信息
	 * @param id
	 * @return
	 */
	public Goods getGoodsWithId(String id) {
		Goods goods = goodsDAO.get(id);
		goods.setPicUrls(getPicUrl(goods));
		int totalCount = 0;
		for(OrderGoods orderGoods : orderGoodsService.getOrderGoodsListWithGoods(goods)) {
			totalCount = totalCount + Integer.parseInt(orderGoods.getCount());
		}
		goods.setTotalCount(totalCount+"");
		return goods;
	}
	/**
	 * 获取商品分页对象
	 * @param page
	 * @param goods
	 * @return
	 */
	public Page<Goods> getPage(Page<Goods> page, Goods goods) {
		goods.setUser(UserHelper.getCurrUser());
		List<Goods> goodsList = goodsDAO.getGoodsListWithGoods(goods);
		page.setTotalRecord(goodsList.size());
		if (goodsList.size() >= (page.getStartOfPage() + page.getPageSize())) {
			page.setResults(goodsList.subList(page.getStartOfPage(), page.getStartOfPage() + page.getPageSize()));
		} else {
			page.setResults(goodsList.subList(page.getStartOfPage(), goodsList.size()));
		}
		return page;
	}

	@Autowired
	private GoodsDAO goodsDAO;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private OrderGoodsService orderGoodsService;
}