package com.unitever.module.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.unitever.module.notice.service.NoticeService;
import com.unitever.module.user.model.User;
import com.unitever.module.user.service.UserService;
import com.unitever.platform.core.web.controller.SpringController;
import com.unitever.platform.util.JPushUtil;
import com.unitever.platform.util.PropertyUtil;

@Controller
@RequestMapping(value="/user")
public class UserController extends SpringController {
	
	private static final Log log = LogFactory.getLog(UserController.class);
	/**
	 * 添加修改用户
	 */
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	@ResponseBody
	public Object saveOrUpdate(String username,String password){
		Map<String, Object> map = new HashMap<String, Object>(1);
		User user=userService.getUserWithLoginName(username);
		if(user!=null&&user.getPassword().equals(password)){
			map.put("code", "1");
			map.put("user", user);
			map.put("token", user.getId());
			log.debug("<<登录成功！！");
		}else{
			map.put("code", "0");
			log.debug("<<登录失败！！");
		}
		return map;
	}
	
	/**
	 * 添加修改用户
	 */
	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	@ResponseBody
	public Object saveOrUpdate(){
		JPushClient jpushClient = new JPushClient("4b848e24e3333e71bab7a580", "488ff5a64b53dfa83c32346f", 3);
        PushPayload payload = JPushUtil.push("notice", 1000);
        try {
			PushResult result = jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return "jajjj";
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NoticeService noticeService;

}