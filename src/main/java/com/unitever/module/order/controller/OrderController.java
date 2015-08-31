package com.unitever.module.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitever.module.order.model.Order;
import com.unitever.module.order.service.OrderService;
import com.unitever.platform.core.dao.Page;
import com.unitever.platform.core.web.argument.annotation.FormModel;
import com.unitever.platform.core.web.controller.SpringController;

@Controller
@RequestMapping(value="/order")
public class OrderController extends SpringController {
	
	/**
	 * 跳转至订单index页面
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(){
		return "module/order/jsp/order-index";
	}
	/**
	 * 跳转至订单列表页面
	 * @param page
	 * @param order
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(@FormModel("page") Page<Order> page, @FormModel("model") Order order){
		setAttribute("pageObj", orderService.getPage(page, order));
		setAttribute("model", order);
		return "module/order/jsp/order-list";
	}
	/**
	 * 跳转至订单添加、修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/input",method=RequestMethod.GET)
	public String input(@RequestParam(value="id", required = false) String id){
		String inputKind = getRequest().getParameter("inputKind");
		if("update".equals(inputKind)) {
			setAttribute("model", orderService.getOrderWithId(id));
		}
		setAttribute("inputKind", inputKind);
		return "module/order/jsp/order-input";
	}
	/**
	 * 跳转至订单发货页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deliver",method=RequestMethod.GET)
	public String deliver(@RequestParam(value="id") String id){
		setAttribute("model", orderService.getOrderWithId(id));
		return "module/order/jsp/order-deliver";
	}
	/**
	 * 跳转至订单查看页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/view",method=RequestMethod.GET)
	public String view(@RequestParam(value="id") String id){
		setAttribute("model", orderService.getOrderWithId(id));
		return "module/order/jsp/order-view";
	}
	/**
	 * 订单发货
	 */
	@RequestMapping(value="/doDeliver",method=RequestMethod.POST)
	@ResponseBody
	public void doDeliver(@FormModel("model") Order order){
		orderService.doDeliver(order);
	}
	/**
	 * 导出excel
	 * @param order
	 */
	@RequestMapping(value="/doExport")
	@ResponseBody
	public void doExport(@FormModel("model") Order order) {
		orderService.doExport(order);
	}
	@Autowired
	private OrderService orderService;
}