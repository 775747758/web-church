package com.unitever.module.withdrawCash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitever.module.withdrawCash.model.WithdrawCash;
import com.unitever.module.withdrawCash.service.WithdrawCashService;
import com.unitever.platform.core.dao.Page;
import com.unitever.platform.core.web.argument.annotation.FormModel;
import com.unitever.platform.core.web.controller.SpringController;

@Controller
@RequestMapping(value="/withdrawCash")
public class WithdrawCashController extends SpringController {
	
	/**
	 * 跳转至提现单index页面
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(){
		return "module/withdrawCash/jsp/withdrawCash-index";
	}
	/**
	 * 跳转至提现单列表页面
	 * @param page
	 * @param withdrawCash
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(@FormModel("page") Page<WithdrawCash> page, @FormModel("model") WithdrawCash withdrawCash){
		setAttribute("pageObj", withdrawCashService.getPage(page, withdrawCash));
		setAttribute("model", withdrawCash);
		return "module/withdrawCash/jsp/withdrawCash-list";
	}
	/**
	 * 跳转至添加、修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/input",method=RequestMethod.GET)
	public String input(@RequestParam(value="id", required = false) String id){
		String inputKind = getRequest().getParameter("inputKind");
		if("update".equals(inputKind)) {
			setAttribute("model", withdrawCashService.getWithdrawCashWithId(id));
		}
		setAttribute("inputKind", inputKind);
		return "module/withdrawCash/jsp/withdrawCash-input";
	}
	/**
	 * 跳转至确认打款页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/comfirm",method=RequestMethod.GET)
	public String comfirm(@RequestParam(value="id", required = false) String id){
		setAttribute("model", withdrawCashService.getWithdrawCashWithId(id));
		return "module/withdrawCash/jsp/withdrawCash-comfirm";
	}
	/**
	 * 跳转至查看页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/view",method=RequestMethod.GET)
	public String view(@RequestParam(value="id", required = false) String id){
		setAttribute("model", withdrawCashService.getWithdrawCashWithId(id));
		return "module/withdrawCash/jsp/withdrawCash-view";
	}
	/**
	 * 添加、修改
	 */
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	@ResponseBody
	public void saveOrUpdate(@FormModel("model") WithdrawCash withdrawCash){
		String inputKind = getRequest().getParameter("inputKind");
		System.out.println(inputKind);
		if("update".equals(inputKind)) {
			withdrawCashService.update(withdrawCash);
		} else {
			withdrawCashService.save(withdrawCash);
		}
	}
	/**
	 * 添加、修改
	 */
	@RequestMapping(value="/doComfirm",method=RequestMethod.POST)
	@ResponseBody
	public void doComfirm(@FormModel("model") WithdrawCash withdrawCash){
		withdrawCashService.doComfirm(withdrawCash);
	}
	/**
	 * 导出excel
	 * @param withdrawCash
	 */
	@RequestMapping(value="/doExport")
	@ResponseBody
	public void doExport(@FormModel("model") WithdrawCash withdrawCash) {
		withdrawCashService.doExport(withdrawCash);
	}
	
	@Autowired
	private WithdrawCashService withdrawCashService;
}