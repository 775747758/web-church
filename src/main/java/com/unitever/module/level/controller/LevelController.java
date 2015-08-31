package com.unitever.module.level.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitever.module.level.model.Level;
import com.unitever.module.level.service.LevelService;
import com.unitever.platform.core.dao.Page;
import com.unitever.platform.core.web.argument.annotation.FormModel;
import com.unitever.platform.core.web.controller.SpringController;

@Controller
@RequestMapping(value="/level")
public class LevelController extends SpringController {
	/**
	 * 跳转至销售话术index页面
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(){
		return "module/level/jsp/level-index";
	}
	/**
	 * 跳转至销售话术列表页面
	 * @param page
	 * @param propaganda
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(@FormModel("page") Page<Level> page, @FormModel("model") Level level){
		setAttribute("pageObj", levelService.getPage(page, level));
		setAttribute("model", level);
		return "module/level/jsp/level-list";
	}
	/**
	 * 跳转至销售话术添加、修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/input",method=RequestMethod.GET)
	public String input(@RequestParam(value="id", required = false) String id){
		String inputKind = getRequest().getParameter("inputKind");
		if("update".equals(inputKind)) {
			setAttribute("model", levelService.getLevelWithId(id));
		}
		setAttribute("inputKind", inputKind);
		return "module/level/jsp/level-input";
	}
	/**
	 * 添加、修改
	 */
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	@ResponseBody
	public void saveOrUpdate(@FormModel("model") Level level){
		String inputKind = getRequest().getParameter("inputKind");
		if("update".equals(inputKind)) {
			levelService.update(level);
		} else {
			levelService.save(level);
		}
	}
	/**
	 * 删除
	 */
	@RequestMapping(value="/doDelete",method=RequestMethod.POST)
	@ResponseBody
	public void doDelete(@RequestParam(value="id") String id){
		levelService.deleteWithId(id);
	}
	
	@Autowired
	private LevelService levelService;
}