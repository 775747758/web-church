package com.unitever.module.propaganda.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitever.module.propaganda.model.Propaganda;
import com.unitever.module.propaganda.service.PropagandaService;
import com.unitever.platform.core.dao.Page;
import com.unitever.platform.core.web.argument.annotation.FormModel;
import com.unitever.platform.core.web.controller.SpringController;

@Controller
@RequestMapping(value="/propaganda")
public class PropagandaController extends SpringController {
	
	/**
	 * 跳转至销售话术index页面
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(){
		return "module/propaganda/jsp/propaganda-index";
	}
	/**
	 * 跳转至销售话术列表页面
	 * @param page
	 * @param propaganda
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(@FormModel("page") Page<Propaganda> page, @FormModel("model") Propaganda propaganda){
		try {
			if(StringUtils.isNotBlank(propaganda.getContent())){
				propaganda.setContent(URLDecoder.decode(propaganda.getContent(), "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setAttribute("pageObj", propagandaService.getPage(page, propaganda));
		setAttribute("model", propaganda);
		return "module/propaganda/jsp/propaganda-list";
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
			setAttribute("model", propagandaService.getPropagandaWithId(id));
		}
		setAttribute("inputKind", inputKind);
		return "module/propaganda/jsp/propaganda-input";
	}
	/**
	 * 添加、修改
	 */
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	@ResponseBody
	public void saveOrUpdate(@FormModel("model") Propaganda propaganda){
		String inputKind = getRequest().getParameter("inputKind");
		if("update".equals(inputKind)) {
			propagandaService.update(propaganda);
		} else {
			propagandaService.save(propaganda);
		}
	}
	/**
	 * 删除
	 */
	@RequestMapping(value="/doDelete",method=RequestMethod.POST)
	@ResponseBody
	public void doDelete(@RequestParam(value="id") String id){
		propagandaService.deleteWithId(id);
	}
	
	@Autowired
	private PropagandaService propagandaService;
}