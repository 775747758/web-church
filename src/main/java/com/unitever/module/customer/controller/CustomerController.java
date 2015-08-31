package com.unitever.module.customer.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.customer.service.CustomerService;
import com.unitever.platform.core.dao.Page;
import com.unitever.platform.core.web.argument.annotation.FormModel;
import com.unitever.platform.core.web.controller.SpringController;

@Controller
@RequestMapping(value="/customer")
public class CustomerController extends SpringController {

	/**
	 * 跳转至客户index页面
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(){
		return "module/customer/jsp/customer-index";
	}
	/**
	 * 跳转至客户列表页面
	 * @param page
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(@FormModel("page") Page<Customer> page, @FormModel("model") Customer customer){
		try {
			if(StringUtils.isNotBlank(customer.getName())){
				customer.setName(URLDecoder.decode(customer.getName(), "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setAttribute("pageObj", customerService.getPage(page, customer));
		setAttribute("model", customer);
		return "module/customer/jsp/customer-list";
	}
	/**
	 * 添加修改客户信息
	 */
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	@ResponseBody
	public void saveOrUpdate(@FormModel("model") Customer customer){
		String inputKind = getRequest().getParameter("inputKind");
		if("update".equals(inputKind)) {
			customerService.update(customer);
		} else {
			customerService.saveWithWeChatNumParentId("gh_a6aceccc4b97", "ogWKus1ilU4hsdZApEwOqJVFuzyI", "");
		}
	}
	/**
	 * 取消代理
	 * @param id
	 */
	@RequestMapping(value="/cancelAgent",method=RequestMethod.POST)
	@ResponseBody
	public void cancelAgent(@RequestParam(value="id") String id){
		customerService.cancelAgent(id);
	}
	/**
	 * 初始化用户
	 * @param id
	 */
	@RequestMapping(value="/initCustomer",method=RequestMethod.POST)
	@ResponseBody
	public void initCustomer(){
		customerService.initCustomer();
	}
	
	@Autowired
	private CustomerService customerService;
}