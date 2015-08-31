//package com.unitever.module.weChat.util;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.ConnectException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.dom4j.Document;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.unitever.module.user.model.User;
//import com.unitever.module.user.service.UserService;
//import com.unitever.platform.core.spring.SpringContextHolder;
//import com.unitever.platform.core.web.context.ServletContextMonitor;
//import com.unitever.platform.util.DateUtil;
//import com.unitever.platform.util.JsonUtil;
//
//@Component
//public class WeChatUtil implements ServletContextMonitor {
//
//	
//	@Override
//	public void init(ServletContext context) {
//		initMenu(null);
//	}
//
//	public static void initMenu(String userId) {
//		final UserService userService = SpringContextHolder.getBean(UserService.class);
//		for(User user : userService.getUserList()){
//			if(StringUtils.isBlank(userId) || (StringUtils.isNotBlank(userId) && userId.equals(user.getId()))){
//				if(StringUtils.isNotBlank(user.getAppId())) {
//					initAccessToken(user);
//					JSONArray buttons = new JSONArray();
//					JSONObject shop = new JSONObject();
//					shop.put("type", "view");
//					shop.put("name", "商城");
//					shop.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+user.getAppId()+"&redirect_uri=http://www.nmbaidai.com/platform/weChat/wcGoodsList&response_type=code&scope=snsapi_userinfo&state="+user.getWeChatNum()+"#wechat_redirect");
//					buttons.add(shop);
//					
////					JSONArray agentButtons = new JSONArray();
//	
////					JSONObject pic = new JSONObject();
////					pic.put("type", "click");
////					pic.put("name", "分享红包");
////					pic.put("key", "getpic");
////					agentButtons.add(pic);
//					
////					JSONObject agentCenter = new JSONObject();
////					agentCenter.put("type", "view");
////					agentCenter.put("name", "推广中心");
////					agentCenter.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+user.getAppId()+"&redirect_uri=http://www.nmbaidai.com/platform/weChat/wcAgentCenter&response_type=code&scope=snsapi_userinfo&state="+user.getWeChatNum()+"#wechat_redirect");
////					agentButtons.add(agentCenter);
////					
////					JSONObject personalCenter = new JSONObject();
////					personalCenter.put("type", "view");
////					personalCenter.put("name", "分享红包");
////					personalCenter.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+user.getAppId()+"&redirect_uri=http://www.nmbaidai.com/platform/weChat/wcSpread&response_type=code&scope=snsapi_userinfo&state="+user.getWeChatNum()+"#wechat_redirect");
////					agentButtons.add(personalCenter);
//					
//					JSONObject agent = new JSONObject();
//					agent.put("type", "click");
//					agent.put("name", "分享红包");
//					agent.put("key", "getpic");
////					agent.put("sub_button", agentButtons);
//					buttons.add(agent);
//					
//					JSONArray companyButtons = new JSONArray();
//	
//					JSONObject customerService = new JSONObject();
//					customerService.put("type", "view");
//					customerService.put("name", "客服中心");
//					customerService.put("url", user.getCustomerServiceUrl());
//					companyButtons.add(customerService);
//					
////					JSONObject wealth = new JSONObject();
////					wealth.put("type", "view");
////					wealth.put("name", "财富模式");
////					wealth.put("url", user.getWealthModeUrl());
////					companyButtons.add(wealth);
//					
//					JSONObject companyCulture = new JSONObject();
//					companyCulture.put("type", "view");
//					companyCulture.put("name", "企业文化");
//					companyCulture.put("url", user.getBusinessCultureUrl());
//					companyButtons.add(companyCulture);
//					
//					JSONObject propaganda = new JSONObject();
//					propaganda.put("type", "view");
//					propaganda.put("name", "销售话术");
//					propaganda.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+user.getAppId()+"&redirect_uri=http://www.nmbaidai.com/platform/weChat/wcPropaganda&response_type=code&scope=snsapi_userinfo&state="+user.getWeChatNum()+"#wechat_redirect");
//					companyButtons.add(propaganda);
//					
//					JSONObject company = new JSONObject();
//					company.put("name", "企业服务");
//					company.put("sub_button", companyButtons);
//					buttons.add(company);
//					
//					JSONObject obj = new JSONObject();
//					obj.put("button", buttons);
//					
//					//创建自定义菜单
//					httpRequest(RequestUrlUtil.createMenuUrl(getAccessToken(user)), "POST", obj.toString());
//				}
//			}
//		}
////		Timer timer = new Timer();  
////        timer.schedule(new TimerTask() {  
////            public void run() {
////            	for(User user : userService.getUserList()){
////        			if(StringUtils.isNotBlank(user.getAppId())) {
////        				getAccessToken(user);
////        			}
////            	}
////            }  
////        }, 200000, 200000);// 设定指定的时间time,此处为200000毫秒  
//	}
//	/**
//	 * 初始化accessToken
//	 * @param user
//	 */
//	private static void initAccessToken(User user) {
//		UserService userService = SpringContextHolder.getBean(UserService.class);
//		String getAccessTokenStr = httpRequest(RequestUrlUtil.getAccessTokenUrl(user.getAppId(), user.getAppSecret()), "GET", null);
////		String getAccessTokenStr = "{\"access_token\":\"CU356me0X-M2D9WHIuB0_PIWDOHjQVX7vJWNNjliYGt_BZT4DMjehHKy9PkcRLPR0d8tCuDN7rklqG3_ek-7SLmNjA0ojygsdwaCpAa6mJQ\",\"expires_in\":7200}";
//		Map<String, String> map = JsonUtil.json2Map(getAccessTokenStr);
//		String getJSApiTicketStr = httpRequest(RequestUrlUtil.getJSApiTicketUrl(map.get("access_token")), "GET", null);
////		String getJSApiTicketStr = "{\"access_token\":\"sM4AOVdWfPE4DxkXGEs8VL9YncHXLipMhPbpfSwN3kWpjAG5aVNtoMQTmCP_-GMZgdjfj5-Idl1b8jJdGZWiHw\",\"expires_in\":7200}";
//		Map<String, String> map2 = JsonUtil.json2Map(getJSApiTicketStr);
//		user.setJsapiTicket(map2.get("ticket"));
//		user.setAccessToken(map.get("access_token"));
//		user.setExpiresTime(DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()+7000000+"");
//		userService.update(user);
//	}
//	/**
//	 * 获取accessToken
//	 * @param user
//	 * @return
//	 */
//	public static String getAccessToken(User user) {
//		if(DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()>Long.parseLong(user.getExpiresTime())) {
//			UserService userService = SpringContextHolder.getBean(UserService.class);
//			String getAccessTokenStr = httpRequest(RequestUrlUtil.getAccessTokenUrl(user.getAppId(), user.getAppSecret()), "GET", null);
////			String getAccessTokenStr = "{\"access_token\":\"CU356me0X-M2D9WHIuB0_PIWDOHjQVX7vJWNNjliYGt_BZT4DMjehHKy9PkcRLPR0d8tCuDN7rklqG3_ek-7SLmNjA0ojygsdwaCpAa6mJQ\",\"expires_in\":7200}";
//			Map<String, String> map = JsonUtil.json2Map(getAccessTokenStr);
//			String getJSApiTicketStr = httpRequest(RequestUrlUtil.getJSApiTicketUrl(map.get("access_token")), "GET", null);
////			String getJSApiTicketStr = "{\"access_token\":\"sM4AOVdWfPE4DxkXGEs8VL9YncHXLipMhPbpfSwN3kWpjAG5aVNtoMQTmCP_-GMZgdjfj5-Idl1b8jJdGZWiHw\",\"expires_in\":7200}";
//			Map<String, String> map2 = JsonUtil.json2Map(getJSApiTicketStr);
//			user.setJsapiTicket(map2.get("ticket"));
//			user.setAccessToken(map.get("access_token"));
//			user.setExpiresTime(DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()+7000000+"");
//			userService.update(user);
//		}
//		return user.getAccessToken();
//	}
//	/**
//	 * 获取accessToken
//	 * @param user
//	 * @return
//	 */
//	public static String getJsApiTicket(User user) {
//		if(DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()>Long.parseLong(user.getExpiresTime())) {
//			UserService userService = SpringContextHolder.getBean(UserService.class);
//			String getAccessTokenStr = httpRequest(RequestUrlUtil.getAccessTokenUrl(user.getAppId(), user.getAppSecret()), "GET", null);
////			String getAccessTokenStr = "{\"access_token\":\"CU356me0X-M2D9WHIuB0_PIWDOHjQVX7vJWNNjliYGt_BZT4DMjehHKy9PkcRLPR0d8tCuDN7rklqG3_ek-7SLmNjA0ojygsdwaCpAa6mJQ\",\"expires_in\":7200}";
//			Map<String, String> map = JsonUtil.json2Map(getAccessTokenStr);
//			String getJSApiTicketStr = httpRequest(RequestUrlUtil.getJSApiTicketUrl(map.get("access_token")), "GET", null);
////			String getJSApiTicketStr = "{\"access_token\":\"sM4AOVdWfPE4DxkXGEs8VL9YncHXLipMhPbpfSwN3kWpjAG5aVNtoMQTmCP_-GMZgdjfj5-Idl1b8jJdGZWiHw\",\"expires_in\":7200}";
//			Map<String, String> map2 = JsonUtil.json2Map(getJSApiTicketStr);
//			user.setJsapiTicket(map2.get("ticket"));
//			user.setAccessToken(map.get("access_token"));
//			user.setExpiresTime(DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()+7000000+"");
//			userService.update(user);
//		}
//		return user.getJsapiTicket();
//	}
//	/**
//	 * 发起https请求并获取结果
//	 * @param requestUrl 请求地址
//	 * @param requestMethod 请求方式（GET、POST）
//	 * @param outputStr 提交的数据
//	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
//	 */
//	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
//		String jsonObject = null;  //nmbaidai
//		StringBuffer buffer = new StringBuffer();
//		try {
//			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
//			TrustManager[] tm = { new MyX509TrustManager() };
//			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//			sslContext.init(null, tm, new java.security.SecureRandom());
//			// 从上述SSLContext对象中得到SSLSocketFactory对象
//			SSLSocketFactory ssf = sslContext.getSocketFactory();
//
//			URL url = new URL(requestUrl);
//			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
//			httpUrlConn.setSSLSocketFactory(ssf);
//
//			httpUrlConn.setDoOutput(true);
//			httpUrlConn.setDoInput(true);
//			httpUrlConn.setUseCaches(false);
//			// 设置请求方式（GET/POST）
//			httpUrlConn.setRequestMethod(requestMethod);
//
//			if ("GET".equalsIgnoreCase(requestMethod))
//				httpUrlConn.connect();
//
//			// 当有数据需要提交时
//			if (null != outputStr) {
//				OutputStream outputStream = httpUrlConn.getOutputStream();
//				// 注意编码格式，防止中文乱码
//				outputStream.write(outputStr.getBytes("UTF-8"));
//				outputStream.close();
//			}
//
//			// 将返回的输入流转换成字符串
//			InputStream inputStream = httpUrlConn.getInputStream();
//			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//			String str = null;
//			while ((str = bufferedReader.readLine()) != null) {
//				buffer.append(str);
//			}
//			bufferedReader.close();
//			inputStreamReader.close();
//			// 释放资源
//			inputStream.close();
//			inputStream = null;
//			httpUrlConn.disconnect();
//			jsonObject = buffer.toString();
//		} catch (ConnectException ce) {
//		} catch (Exception e) {
//		}
//		return jsonObject;
//	}
//	/**
//	 * 发起https请求并获取结果
//	 * 
//	 * @param requestUrl 请求地址
//	 * @param requestMethod 请求方式（GET、POST）
//	 * @param outputStr 提交的数据
//	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
//	 */
//	@SuppressWarnings("unchecked")
//	public static Map<String, String> httpRequestReturnMap(String requestUrl, String requestMethod, String outputStr) {
//		Map<String, String> map = new HashMap<String, String>();
//		try {
//			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
//			TrustManager[] tm = { new MyX509TrustManager() };
//			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//			sslContext.init(null, tm, new java.security.SecureRandom());
//			// 从上述SSLContext对象中得到SSLSocketFactory对象
//			SSLSocketFactory ssf = sslContext.getSocketFactory();
//
//			URL url = new URL(requestUrl);
//			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
//			httpUrlConn.setSSLSocketFactory(ssf);
//
//			httpUrlConn.setDoOutput(true);
//			httpUrlConn.setDoInput(true);
//			httpUrlConn.setUseCaches(false);
//			// 设置请求方式（GET/POST）
//			httpUrlConn.setRequestMethod(requestMethod);
//
//			if ("GET".equalsIgnoreCase(requestMethod))
//				httpUrlConn.connect();
//
//			// 当有数据需要提交时
//			if (null != outputStr) {
//				OutputStream outputStream = httpUrlConn.getOutputStream();
//				// 注意编码格式，防止中文乱码
//				outputStream.write(outputStr.getBytes("UTF-8"));
//				outputStream.close();
//			}
//			// 将返回的输入流转换成字符串
//			InputStream inputStream = httpUrlConn.getInputStream();
//			// 读取输入流
//			SAXReader reader = new SAXReader();
//			Document document = reader.read(inputStream);
//			// 得到xml根元素
//			Element root = document.getRootElement();
//			// 得到根元素的所有子节点
//			List<Element> elementList = root.elements();
//
//			// 遍历所有子节点
//			for (Element e : elementList)
//				map.put(e.getName(), e.getText());
//
//			// 释放资源
//			inputStream.close();
//			inputStream = null;
//			httpUrlConn.disconnect();
//		} catch (ConnectException ce) {
//		} catch (Exception e) {
//		}
//		return map;
//	}
//	/**
//	 * 获取ip地址
//	 * @param request
//	 * @return
//	 */
//	public static String getIp(HttpServletRequest request) {
//		if (request == null)
//			return "";
//		String ip = request.getHeader("X-Requested-For");
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("X-Forwarded-For");
//		}
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("HTTP_CLIENT_IP");
//		}
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//		}
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//		}
//		return ip;
//	}
//	@Override
//	public void destroyed(ServletContext context) {
//		// TODO Auto-generated method stub
//	}
//}
package com.unitever.module.weChat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unitever.module.user.model.User;
import com.unitever.module.user.service.UserService;
import com.unitever.platform.core.spring.SpringContextHolder;
import com.unitever.platform.core.web.context.ServletContextMonitor;
import com.unitever.platform.util.DateUtil;
import com.unitever.platform.util.JsonUtil;

@Component
public class WeChatUtil implements ServletContextMonitor {

	@Override
	public void init(ServletContext context) {
//		initMenu(null);
	}

	public static void initMenu(String userId) {/*
		final UserService userService = SpringContextHolder.getBean(UserService.class);
		for(User user : userService.getUserList()){
			if(StringUtils.isBlank(userId) || (StringUtils.isNotBlank(userId) && userId.equals(user.getId()))){
				if(StringUtils.isNotBlank(user.getAppId())) {
					initAccessToken(user);
					JSONArray buttons = new JSONArray();
					JSONObject shop = new JSONObject();
					shop.put("type", "view");
					shop.put("name", "商城");
					shop.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+user.getAppId()+"&redirect_uri=http://www.nmbaidai.com/platform/weChat/wcGoodsList&response_type=code&scope=snsapi_userinfo&state="+user.getWeChatNum()+"#wechat_redirect");
					buttons.add(shop);
					
					JSONObject agent = new JSONObject();
					agent.put("type", "click");
					agent.put("name", "分享红包");
					agent.put("key", "getpic");
					buttons.add(agent);
					
					JSONArray companyButtons = new JSONArray();
	
					JSONObject customerService = new JSONObject();
					customerService.put("type", "view");
					customerService.put("name", "客服中心");
					customerService.put("url", user.getCustomerServiceUrl());
					companyButtons.add(customerService);
					
					JSONObject companyCulture = new JSONObject();
					companyCulture.put("type", "view");
					companyCulture.put("name", "企业文化");
					companyCulture.put("url", user.getBusinessCultureUrl());
					companyButtons.add(companyCulture);
					
					JSONObject propaganda = new JSONObject();
					propaganda.put("type", "view");
					propaganda.put("name", "销售话术");
					propaganda.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+user.getAppId()+"&redirect_uri=http://www.nmbaidai.com/platform/weChat/wcPropaganda&response_type=code&scope=snsapi_userinfo&state="+user.getWeChatNum()+"#wechat_redirect");
					companyButtons.add(propaganda);
					
					JSONObject company = new JSONObject();
					company.put("name", "企业服务");
					company.put("sub_button", companyButtons);
					buttons.add(company);
					
					JSONObject obj = new JSONObject();
					obj.put("button", buttons);
					
					//创建自定义菜单
					httpRequest(RequestUrlUtil.createMenuUrl(getAccessToken(user)), "POST", obj.toString());
				}
			}
		}
	*/}
	/**
	 * 初始化accessToken
	 * @param user
	 */
	private static void initAccessToken(User user) {
		/*UserService userService = SpringContextHolder.getBean(UserService.class);
//		String getAccessTokenStr = httpRequest(RequestUrlUtil.getAccessTokenUrl(user.getAppId(), user.getAppSecret()), "GET", null);
		String getAccessTokenStr = "{\"access_token\":\"KisisBEXgXbSY6UMrAOJbNWXyrwSTwFZISqlEEZk0uD2nVO-M5Ak0_9P4k0FQCbZ789tM_CxscMoEUALie7GeKUjCV_SpJesljPMVxXSF-w\",\"expires_in\":7200}";
		Map<String, String> map = JsonUtil.json2Map(getAccessTokenStr);
//		String getJSApiTicketStr = httpRequest(RequestUrlUtil.getJSApiTicketUrl(map.get("access_token")), "GET", null);
		String getJSApiTicketStr = "{\"access_token\":\"sM4AOVdWfPE4DxkXGEs8VL9YncHXLipMhPbpfSwN3kUVl_oOC59Gra97pFbOG4lq3DS7sVLS5b3Cv0tUvjcyJw\",\"expires_in\":7200}";
		Map<String, String> map2 = JsonUtil.json2Map(getJSApiTicketStr);
		user.setJsapiTicket(map2.get("ticket"));
		user.setAccessToken(map.get("access_token"));
		user.setExpiresTime(DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()+7000000+"");
		userService.update(user);*/
	}
	/**
	 * 获取accessToken
	 * @param user
	 * @return
	 */
	public static String getAccessToken(User user) {
		/*if(DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()>Long.parseLong(user.getExpiresTime())) {
			UserService userService = SpringContextHolder.getBean(UserService.class);
//			String getAccessTokenStr = httpRequest(RequestUrlUtil.getAccessTokenUrl(user.getAppId(), user.getAppSecret()), "GET", null);
			String getAccessTokenStr = "{\"access_token\":\"KisisBEXgXbSY6UMrAOJbNWXyrwSTwFZISqlEEZk0uD2nVO-M5Ak0_9P4k0FQCbZ789tM_CxscMoEUALie7GeKUjCV_SpJesljPMVxXSF-w\",\"expires_in\":7200}";
			Map<String, String> map = JsonUtil.json2Map(getAccessTokenStr);
//			String getJSApiTicketStr = httpRequest(RequestUrlUtil.getJSApiTicketUrl(map.get("access_token")), "GET", null);
			String getJSApiTicketStr = "{\"access_token\":\"sM4AOVdWfPE4DxkXGEs8VL9YncHXLipMhPbpfSwN3kUVl_oOC59Gra97pFbOG4lq3DS7sVLS5b3Cv0tUvjcyJw\",\"expires_in\":7200}";
			Map<String, String> map2 = JsonUtil.json2Map(getJSApiTicketStr);
			user.setJsapiTicket(map2.get("ticket"));
			user.setAccessToken(map.get("access_token"));
			user.setExpiresTime(DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()+7000000+"");
			userService.update(user);
		}
		return user.getAccessToken();*/
		return null;
	}
	/**
	 * 获取accessToken
	 * @param user
	 * @return
	 */
	public static String getJsApiTicket(User user) {/*
		if(DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()>Long.parseLong(user.getExpiresTime())) {
			UserService userService = SpringContextHolder.getBean(UserService.class);
//			String getAccessTokenStr = httpRequest(RequestUrlUtil.getAccessTokenUrl(user.getAppId(), user.getAppSecret()), "GET", null);
			String getAccessTokenStr = "{\"access_token\":\"KisisBEXgXbSY6UMrAOJbNWXyrwSTwFZISqlEEZk0uD2nVO-M5Ak0_9P4k0FQCbZ789tM_CxscMoEUALie7GeKUjCV_SpJesljPMVxXSF-w\",\"expires_in\":7200}";
			Map<String, String> map = JsonUtil.json2Map(getAccessTokenStr);
//			String getJSApiTicketStr = httpRequest(RequestUrlUtil.getJSApiTicketUrl(map.get("access_token")), "GET", null);
			String getJSApiTicketStr = "{\"access_token\":\"sM4AOVdWfPE4DxkXGEs8VL9YncHXLipMhPbpfSwN3kUVl_oOC59Gra97pFbOG4lq3DS7sVLS5b3Cv0tUvjcyJw\",\"expires_in\":7200}";
			Map<String, String> map2 = JsonUtil.json2Map(getJSApiTicketStr);
			user.setJsapiTicket(map2.get("ticket"));
			user.setAccessToken(map.get("access_token"));
			user.setExpiresTime(DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()+7000000+"");
			userService.update(user);
		}
		return user.getJsapiTicket();
	*/
		return null;}
	/**
	 * 发起https请求并获取结果
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		String jsonObject = null;  //nmbaidai
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = buffer.toString();
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> httpRequestReturnMap(String requestUrl, String requestMethod, String outputStr) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return map;
	}
	/**
	 * 获取ip地址
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		if (request == null)
			return "";
		String ip = request.getHeader("X-Requested-For");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	@Override
	public void destroyed(ServletContext context) {
		// TODO Auto-generated method stub
	}
}