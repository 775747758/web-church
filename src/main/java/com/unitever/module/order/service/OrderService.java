package com.unitever.module.order.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.customer.service.CustomerService;
import com.unitever.module.goods.service.GoodsService;
import com.unitever.module.level.model.Level;
import com.unitever.module.level.service.LevelService;
import com.unitever.module.order.dao.manual.OrderDAO;
import com.unitever.module.order.model.Order;
import com.unitever.module.ordergoods.model.OrderGoods;
import com.unitever.module.ordergoods.service.OrderGoodsService;
import com.unitever.module.shoppingcart.service.ShoppingCartService;
import com.unitever.module.user.model.User;
import com.unitever.module.weChat.util.RequestUrlUtil;
import com.unitever.module.weChat.util.WeChatUtil;
import com.unitever.platform.component.attachment.util.AttachmentUtil;
import com.unitever.platform.core.common.helper.UserHelper;
import com.unitever.platform.core.dao.Page;
import com.unitever.platform.util.DateUtil;
import com.unitever.platform.util.FileUtil;
import com.unitever.platform.util.UUID;
import com.unitever.platform.util.excel.ExcelSheetVO;
import com.unitever.platform.util.excel.ExcelUtil;

@Service
@Transactional
public class OrderService {

	/**
	 * 根据用户获取订单集合
	 * @return
	 */
	public List<Order> getOrderListWithUser(User user) {
		Order order = new Order();
		order.setUser(user);
		return orderDAO.getOrderListWithOrder(order);
	}
	/**
	 * 根据客户id获取订单集合
	 * @param customerId
	 * @return
	 */
	public List<Order> getOrderListWithCustomerId(String customerId) {
		Order order = new Order();
		order.setCustomer(new Customer(customerId));
		List<Order> orderList = orderDAO.getOrderListWithOrder(order); 
		for(Order newOrder : orderList) {
			for(OrderGoods orderGoods : newOrder.getOrderGoodsList()) {
				orderGoods.getGoods().setPicUrls(goodsService.getPicUrl(orderGoods.getGoods()));
			}
		}
		return orderList;
	}
	/**
	 * 根据参数添加订单
	 * @param customerId
	 * @param receiver
	 * @param receiverPhoneNum
	 * @param receiveAddress
	 * @return
	 */
	public Order saveWithParm(String customerId, String receiver, String receiverPhoneNum, String receiveAddress) {
		Order order = new Order();
		try {
			Customer customer = customerService.getCustomerWithId(customerId);
			order.setCustomer(customer);
			if(Customer.CUSTOMER_KIND_UNAGENT.equals(customer.getKind())) {
				order.setKind(Order.ORDER_KIND_FIRST);
			} else {
				order.setKind(Order.ORDER_KIND_SENCOND);
			}
			order.setReceiver(URLDecoder.decode(receiver, "UTF-8"));
			order.setReceiverPhoneNum(URLDecoder.decode(receiverPhoneNum, "UTF-8"));
			order.setReceiveAddress(URLDecoder.decode(receiveAddress, "UTF-8"));
			order.setCode(UUID.getUUID());
			order.setDate(DateUtil.getCurrDateString());
			order.setState(Order.ORDER_STATE_UNPAYMENT);
			order.setUser(customer.getUser());
			orderDAO.save(order);
			orderGoodsService.saveWithOrder(order);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return order;
		
	}
	/**
	 * 根据订单id确认收款
	 * @param orderId
	 */
	public void payment(String orderId) {
		//根据分层执行返利操作
		Order order = orderDAO.get(orderId);
		List<Level> levelList = new ArrayList<Level>();
		if(Order.ORDER_KIND_FIRST.equals(order.getKind())) {
			levelList = levelService.getLevelListWithUserKind(order.getUser(), Level.LEVEL_KIND_FIRST);
		} else {
			levelList = levelService.getLevelListWithUserKind(order.getUser(), Level.LEVEL_KIND_SECOND);
		}
		Customer customer = order.getCustomer();
		for(Level level : levelList) {
			if(customer != null) {
				customer = customer.getParent();
				if(customer != null) {
					String cash = String.format("%.2f", order.getTotalPrice()*Integer.parseInt(level.getProportion())/100);
					String str = WeChatUtil.httpRequest(RequestUrlUtil.getSendMessageWithOpenIdUrl(WeChatUtil.getAccessToken(customer.getUser())), "POST", 
							"{\"touser\":\""+customer.getWeChatNum()+"\",\"text\":{\"content\":\"恭喜您获得"+cash+"元佣金\"},\"msgtype\":\"text\"}");
					System.out.println(str);
					customer.setCommission(String.format("%.2f", (order.getTotalPrice()*Integer.parseInt(level.getProportion())/100)+Float.parseFloat(customer.getCommission())));
					customerService.update(customer);
				}
			}
		}
		//修改订单状态为未发货
		order.setState(Order.ORDER_STATE_UNSEND);
		orderDAO.update(order);
		//如果当前用户是非代理将用户设置为代理
		if(Customer.CUSTOMER_KIND_UNAGENT.equals(order.getCustomer().getKind())) {
			order.getCustomer().setKind(Customer.CUSTOMER_KIND_AGENT);
			customerService.update(order.getCustomer());
		}
		//清空该用户的购物车
		shoppingCartServive.deleteWithCustomer(order.getCustomer());
	}
	/**
	 * 根据id获取订单信息
	 * @param id
	 * @return
	 */
	public Order getOrderWithId(String id) {
		return orderDAO.get(id);
	}
	/**
	 * 获取订单分页对象
	 * @param page
	 * @param order
	 * @return
	 */
	public Page<Order> getPage(Page<Order> page, Order order) {
		order.setUser(UserHelper.getCurrUser());
		List<Order> orderList = orderDAO.getOrderListWithOrder(order);
		page.setTotalRecord(orderList.size());
		if (orderList.size() >= (page.getStartOfPage() + page.getPageSize())) {
			page.setResults(orderList.subList(page.getStartOfPage(), page.getStartOfPage() + page.getPageSize()));
		} else {
			page.setResults(orderList.subList(page.getStartOfPage(), orderList.size()));
		}
		return page;
	}
	/**
	 * 导出
	 * @param order
	 */
	public void doExport(Order order) {
		order.setUser(UserHelper.getCurrUser());
		List<Order> orderList = orderDAO.getOrderListWithOrder(order);
		List<ExcelSheetVO> excelSheetVOList = new ArrayList<ExcelSheetVO>();
		ExcelSheetVO sheetVo = new ExcelSheetVO();
		sheetVo.setName("订单");
		List<List<String>> dataList = new ArrayList<List<String>>();
		//第一行
		List<String> firstLineList = new ArrayList<String>();
		firstLineList.add("序号");
		firstLineList.add("订单号");
		firstLineList.add("类型");
		firstLineList.add("客户");
		firstLineList.add("收件人");
		firstLineList.add("收件人电话");
		firstLineList.add("收件人地址");
		firstLineList.add("日期");
		firstLineList.add("状态");
		dataList.add(firstLineList);
		//内容
		for(int i=0;i<orderList.size();i++) {
			List<String> contentList = new ArrayList<String>();
			contentList.add(i+1+"");
			contentList.add(orderList.get(i).getCode());
			contentList.add(orderList.get(i).getKindValue());
			contentList.add(orderList.get(i).getCustomer().getName());
			contentList.add(orderList.get(i).getReceiver());
			contentList.add(orderList.get(i).getReceiverPhoneNum());
			contentList.add(orderList.get(i).getReceiveAddress());
			contentList.add(orderList.get(i).getDate());
			contentList.add(orderList.get(i).getStateValue());
			dataList.add(contentList);
		}
		sheetVo.setDatas(dataList);
		excelSheetVOList.add(sheetVo);
		File dir = new File(AttachmentUtil.getTempDir());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir.getAbsolutePath() + "/" + UUID.getUUID());
		try {
			OutputStream out = new FileOutputStream(file);
			ExcelUtil.exportExcel(excelSheetVOList, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileUtil.download(file, "订单信息.xls");
	}
	/**
	 * 确认发货
	 * @param order
	 */
	public void doDeliver(Order order) {
		orderDAO.update(order);
		Customer customer = customerService.getCustomerWithId(order.getCustomer().getId()); 
		String str = WeChatUtil.httpRequest(RequestUrlUtil.getSendMessageWithOpenIdUrl(WeChatUtil.getAccessToken(customer.getUser())), "POST", 
				"{\"touser\":\""+customer.getWeChatNum()+"\",\"text\":{\"content\":\"您的货物已经发出，请耐心等待。\n物流名称："+order.getLogisticName()+"。\n物流单号："+order.getLogisticNum()+"\"},\"msgtype\":\"text\"}");
		System.out.println(str);
	}
	/**
	 * 确认收货
	 * @param orderId
	 */
	public void confirmReceipt(String orderId) {
		Order order = orderDAO.get(orderId);
		order.setState(Order.ORDER_STATE_RECEIVE);
		orderDAO.update(order);
	}
	
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderGoodsService orderGoodsService;
	@Autowired
	private LevelService levelService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ShoppingCartService shoppingCartServive;
}