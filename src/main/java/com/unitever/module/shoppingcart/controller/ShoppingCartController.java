package com.unitever.module.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitever.module.shoppingcart.service.ShoppingCartService;
import com.unitever.platform.core.web.controller.SpringController;

@Controller
@RequestMapping(value="/shoppingCart")
public class ShoppingCartController extends SpringController {

	/**
	 * 确认付款
	 */
	@RequestMapping(value="/doDelete",method=RequestMethod.POST)
	@ResponseBody
	public void doDelete(@RequestParam(value="id") String id){
		shoppingCartService.doDelete(id);
	}
	
	@Autowired
	private ShoppingCartService shoppingCartService;
}
