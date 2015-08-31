package com.unitever.module.picture.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.alibaba.fastjson.JSON;
import com.unitever.module.notice.model.Notice;
import com.unitever.module.notice.service.NoticeService;
import com.unitever.module.picture.model.Picture;
import com.unitever.module.picture.service.PictureService;
import com.unitever.module.user.model.User;
import com.unitever.platform.core.web.argument.annotation.FormModel;
import com.unitever.platform.core.web.controller.SpringController;
import com.unitever.platform.util.DateUtil;
import com.unitever.platform.util.JPushUtil;
import com.unitever.platform.util.UUID;

@Controller
@RequestMapping(value = "/picture")
public class PictureController extends SpringController {

	private static final Log log = LogFactory.getLog(PictureController.class);

	/**
	 * 添加修改用户
	 */
	@RequestMapping(value = "/getByPageNo.do", method = RequestMethod.POST)
	@ResponseBody
	public Object getByPageNo(
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "count", defaultValue = "20") int count) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		List<Picture> noticeList = pictureService.getByPageNo(pageNo, count);
		int totalRecord = pictureService.getPictureCount();
		int currentIndex = pageNo * count;
		if (noticeList == null || noticeList.size() == 0) {
			map.put("code", "0");
		} else {
			map.put("code", "1");
			map.put("totalRecord", totalRecord);
			map.put("notice", noticeList);
			map.put("hasMore", currentIndex < totalRecord ? true : false);
		}
		return map;
	}

	@RequestMapping(value = "/save.do", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object save(String json) {
		Picture picture = JSON.parseObject(json, Picture.class);
		picture.setDate(new Date());
		pictureService.save(picture);
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("code", "1");

		PushPayload payload = JPushUtil.push("picture",
				pictureService.getPictureCount());
		try {
			pictureService.jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	@Autowired
	private PictureService pictureService;

}