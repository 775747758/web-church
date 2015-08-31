package com.unitever.module.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitever.module.goods.model.Goods;
import com.unitever.module.goods.service.GoodsService;
import com.unitever.platform.core.dao.Page;
import com.unitever.platform.core.web.argument.annotation.FormModel;
import com.unitever.platform.core.web.controller.SpringController;

@Controller
@RequestMapping(value="/goods")
public class GoodsController extends SpringController {
	
	/**
	 * 跳转至商品index页面
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(){
		return "module/goods/jsp/goods-index";
	}
	/**
	 * 跳转至商品列表页面
	 * @param page
	 * @param goods
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(@FormModel("page") Page<Goods> page, @FormModel("model") Goods goods){
		setAttribute("pageObj", goodsService.getPage(page, goods));
		setAttribute("model", goods);
		return "module/goods/jsp/goods-list";
	}
	/**
	 * 跳转至添加修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/input",method=RequestMethod.GET)
	public String input(@RequestParam(value="id", required = false) String id){
		String inputKind = getRequest().getParameter("inputKind");
		if("update".equals(inputKind)) {
			setAttribute("model", goodsService.getGoodsWithId(id));
		} else {
			setAttribute("model", new Goods());
		}
		setAttribute("inputKind", inputKind);
		return "module/goods/jsp/goods-input";
	}
	/**
	 * 添加修改商品
	 */
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	@ResponseBody
	public void saveOrUpdate(@FormModel("model") Goods goods){
		String inputKind = getRequest().getParameter("inputKind");
		if("update".equals(inputKind)) {
			goodsService.update(goods);
		} else {
			goodsService.save(goods);
		}
	}
	/**
	 * 删除商品
	 */
	@RequestMapping(value="/doDelete",method=RequestMethod.POST)
	@ResponseBody
	public void doDelete(@RequestParam(value="id") String id){
		goodsService.deleteWithId(id);
	}
	
	@Autowired
	private GoodsService goodsService;
}