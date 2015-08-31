package com.unitever.module.weChat.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.unitever.module.customer.model.Customer;
import com.unitever.module.customer.service.CustomerService;
import com.unitever.module.level.model.Level;
import com.unitever.module.level.service.LevelService;
import com.unitever.module.order.model.Order;
import com.unitever.module.order.service.OrderService;
import com.unitever.module.ordergoods.model.OrderGoods;
import com.unitever.module.ordergoods.service.OrderGoodsService;
import com.unitever.module.user.model.User;
import com.unitever.module.user.service.UserService;
import com.unitever.module.weChat.model.Article;
import com.unitever.module.weChat.model.NewsMessage;
import com.unitever.module.weChat.model.TextMessage;
import com.unitever.module.weChat.util.MessageUtil;
import com.unitever.module.weChat.util.RequestUrlUtil;
import com.unitever.module.weChat.util.WeChatUtil;
import com.unitever.module.weChat.vo.AgentCenterVo;
import com.unitever.module.weChat.vo.PersonalCenterVo;
import com.unitever.platform.util.DateUtil;
import com.unitever.platform.util.JsonUtil;
import com.unitever.platform.util.UUID;

@Service
@Transactional
public class WeChatService {
	
	/**
	 * 根据客户id,用户查询代理中心vo
	 * @param customerId
	 * @param user
	 * @return
	 */
	public AgentCenterVo getAgentCenterVoWithCustomerUser(Customer currCustomer, User user) {
		AgentCenterVo agentCenterVo = new AgentCenterVo();
		List<Customer> customerList = customerService.getCustomerListWithUser(user);
		List<Level> levelList = levelService.getLevelListWithUserKind(user, Level.LEVEL_KIND_FIRST);
		agentCenterVo.setCustomer(currCustomer);
		List<Customer> myAllAgentList = new ArrayList<Customer>();
		List<Customer> lastAgentList = new ArrayList<Customer>();
		for(Level level : levelList) {
			lastAgentList = getNextAgentList(customerList, lastAgentList, currCustomer.getId());
			myAllAgentList.addAll(lastAgentList);
			level.setSubCustomerList(lastAgentList);
		}
		agentCenterVo.setLevelList(levelList);
		agentCenterVo.setMyAllAgentNum(myAllAgentList.size()+"");
		List<Customer> myFollowerList = new ArrayList<Customer>();
		List<Customer> myAgentList = new ArrayList<Customer>();
		for(Customer customer : customerList) {
			if(customer.getParent()!=null&&customer.getParent().getId().equals(currCustomer.getId())) {
				if(Customer.CUSTOMER_KIND_AGENT.equals(customer.getKind())) {
					myAgentList.add(customer);
				}
				myFollowerList.add(customer);
			}
		}
		agentCenterVo.setMyFollowerNum(myFollowerList.size()+"");
		agentCenterVo.setMyAgentNum(myAgentList.size()+"");
		agentCenterVo.setUnAgentNum(myFollowerList.size()-myAgentList.size()+"");
		int unPaymentNum = 0;
		int unSendNum = 0;
		int unReceiveNum = 0;
		int finshedNum = 0;
		List<Order> orderList = orderService.getOrderListWithUser(user);
		for(Order order : orderList) {
			for(Customer myAllAgent : myAllAgentList) {
				if(order.getCustomer().getId().equals(myAllAgent.getId())) {
					if(Order.ORDER_STATE_UNPAYMENT.equals(order.getState())) {
						unPaymentNum++;
					} else if(Order.ORDER_STATE_UNSEND.equals(order.getState())) {
						unSendNum++;
					} else if(Order.ORDER_STATE_UNRECEIVE.equals(order.getState())) {
						unReceiveNum++;
					} else if(Order.ORDER_STATE_FINSHED.equals(order.getState())) {
						finshedNum++;
					}
				}
			}
		}
		agentCenterVo.setUnPaymentNum(unPaymentNum+"");
		agentCenterVo.setUnSendNum(unSendNum+"");
		agentCenterVo.setUnReceiveNum(unReceiveNum+"");
		agentCenterVo.setFinshedNum(finshedNum+"");
		agentCenterVo.setRemainCash(String.format("%.2f", Float.parseFloat(currCustomer.getCommission())-Float.parseFloat(currCustomer.getCash())));
		return agentCenterVo;
	}
	/**
	 * 根据客户集合，上级客户集合查询下级客户集合
	 * @param customerList
	 * @param lastAgentList
	 * @return
	 */
	private List<Customer> getNextAgentList(List<Customer> customerList, List<Customer> lastAgentList, String customerId) {
		List<Customer> nexAgentList = new ArrayList<Customer>();
		for(Customer customer : customerList) {
			if(lastAgentList.size()>0) {
				for(Customer lastAgent : lastAgentList) {
					if(customer.getParent()!=null&&customer.getParent().getId().equals(lastAgent.getId())&&Customer.CUSTOMER_KIND_AGENT.equals(customer.getKind())) {
						nexAgentList.add(customer);
					}
				}
			} else {
				if(customer.getParent()!=null&&customer.getParent().getId().equals(customerId)&&Customer.CUSTOMER_KIND_AGENT.equals(customer.getKind())) {
					nexAgentList.add(customer);
				}
			}
		}
		return nexAgentList;
	}
	/**
	 * 根据客户id,用户获取个人中心vo
	 * @param customerId
	 * @param user
	 * @return
	 */
	public PersonalCenterVo getPersonalCenterVoWithCustomerUser(Customer currCustomer) {
		PersonalCenterVo personalCenterVo = new PersonalCenterVo();
		personalCenterVo.setCustomer(currCustomer);
		personalCenterVo.setRemainCash(String.format("%.2f", Float.parseFloat(currCustomer.getCommission())-Float.parseFloat(currCustomer.getCash())));
		int myAgentNum = 0;
		if(Customer.CUSTOMER_KIND_AGENT.equals(currCustomer.getKind())) {
			List<Customer> customerList = customerService.getCustomerListWithUser(currCustomer.getUser());
			for(Customer customer : customerList) {
				if(customer.getParent()!=null&&customer.getParent().getId().equals(currCustomer.getId())) {
					if(Customer.CUSTOMER_KIND_AGENT.equals(customer.getKind())) {
						myAgentNum++;
					}
				}
			}
		}
		personalCenterVo.setMyAgentNum(myAgentNum+"");
		return personalCenterVo;
	}
	
	
	
	public List<Customer> getAgentListWithCustomerId(String customerId) {
		Customer currCustomer = customerService.getCustomerWithId(customerId);
		List<Customer> agentList = new ArrayList<Customer>();
		if(Customer.CUSTOMER_KIND_AGENT.equals(currCustomer.getKind())) {
			List<Customer> customerList = customerService.getCustomerListWithUser(currCustomer.getUser());
			for(Customer customer : customerList) {
				if(customer.getParent()!=null&&customer.getParent().getId().equals(currCustomer.getId())) {
					if(Customer.CUSTOMER_KIND_AGENT.equals(customer.getKind())) {
						String str = WeChatUtil.httpRequest(RequestUrlUtil.getUserInfoUrl(WeChatUtil.getAccessToken(customer.getUser()), customer.getWeChatNum()), "GET", null);
						Map<String, String> customerMap = JsonUtil.json2Map(str);
						customer.setHeadimgurl(customerMap.get("headimgurl"));
						agentList.add(customer);
					}
				}
			}
		}
		return agentList;
	}
	
	public List<Customer> getPromotionListWithCustomerId(String customerId) {
		Customer currCustomer = customerService.getCustomerWithId(customerId);
		List<Customer> promotionList = new ArrayList<Customer>();
		if(Customer.CUSTOMER_KIND_AGENT.equals(currCustomer.getKind())) {
			List<Customer> customerList = customerService.getCustomerListWithUser(currCustomer.getUser());
			for(Customer customer : customerList) {
				if(customer.getParent()!=null&&customer.getParent().getId().equals(currCustomer.getId())) {
					String str = WeChatUtil.httpRequest(RequestUrlUtil.getUserInfoUrl(WeChatUtil.getAccessToken(customer.getUser()), customer.getWeChatNum()), "GET", null);
					Map<String, String> customerMap = JsonUtil.json2Map(str);
					customer.setHeadimgurl(customerMap.get("headimgurl"));
					promotionList.add(customer);
				}
			}
		}
		return promotionList;
	}
	
	
	
	/**
	 * 处理微信响应请求
	 * @param request
	 * @return
	 */
	public String weChatHandler(HttpServletRequest request) {
		String respMessage = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {// 文本消息
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");
				System.out.println("content:"+content);
				// 默认回复此文本消息
				User user = userService.getUserWithWeChatNum(toUserName);
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setFuncFlag(0);
				textMessage.setContent("欢迎光临"+user.getName()+"商城！");
				// 将文本消息对象转换成xml字符串
				respMessage = MessageUtil.textMessageToXml(textMessage);
			} else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件
				// 接收用户发送的事件请求内容
				String event = requestMap.get("Event");
				System.out.println(event);
				if(MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(event)) {//关注
					String eventKey = requestMap.get("EventKey");
					System.out.println("-----------subscribe------------"+eventKey);
					if(StringUtils.isNotBlank(eventKey)) {
						customerService.saveWithWeChatNumParentId(toUserName, fromUserName, eventKey.split("_")[1]);
					} else {
						customerService.saveWithWeChatNumParentId(toUserName, fromUserName, null);
					}
//					User user = userService.getUserWithWeChatNum(toUserName);
//					TextMessage textMessage = new TextMessage();
//					textMessage.setToUserName(fromUserName);
//					textMessage.setFromUserName(toUserName);
//					textMessage.setCreateTime(new Date().getTime());
//					textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
//					textMessage.setFuncFlag(0);
//					textMessage.setContent("欢迎光临"+user.getName()+"商城！");
//					// 将文本消息对象转换成xml字符串
//					respMessage = MessageUtil.textMessageToXml(textMessage);
					
					// 创建图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setFuncFlag(0);
					List<Article> articleList = new ArrayList<Article>();
					Article article = new Article();
					article.setTitle("品牌故事");
					article.setDescription("经过数百年的传承，蒙医后人将蒙文化与现代高科技结合使这一秘方重现于世，为纪念蒙古民族的贡献，尊称为“蒙格勒”。");
					article.setPicUrl("http://mmbiz.qpic.cn/mmbiz/esmiaZlkicxfphjrOWsy4agvuics8KjEf6poaywLtRpZAniclDLjWdmFS7ibkpJCPvyKduhGmbEmCaASub4ibYPutyGw/0?wxfrom=5");
					article.setUrl("http://mp.weixin.qq.com/s?__biz=MzA4Mjg0MjcxOQ==&mid=200494224&idx=1&sn=7577d14373dc5093d26df8738fff295f&scene=18&key=af154fdc40fed003c1752d2efd56a601854e96d2ea46b247e41d5c96f7651296a86f20dc4d80f0fd373706b54605b821&ascene=1&uin=MTEyNzA3NTcwMA%3D%3D&devicetype=Windows+7&version=61010029&pass_ticket=2h34oEI8HF8luzL8%2FLIieH2fKCQqN7jj%2Bqx0ZK5iShH9YeVHnzEN0hO%2B35OPrwbN");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				} else if(MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(event)) {//取消关注
					String eventKey = requestMap.get("EventKey");
					System.out.println("-----------unsubscribe------------"+eventKey);
//					customerService.cancelAgent(eventKey.split("_")[1]);
				} else if(MessageUtil.EVENT_TYPE_CLICK.equals(event)) {//点击自定义菜单
					String eventKey = requestMap.get("EventKey");
					if("getpic".equals(eventKey)) {//点击生成推广图片
						User user = userService.getUserWithWeChatNum(toUserName);
						Customer customer = customerService.getCustomerWithWeChatNum(fromUserName, user);
						if(Customer.CUSTOMER_KIND_AGENT.equals(customer.getKind())) {
							// 创建图文消息
							NewsMessage newsMessage = new NewsMessage();
							newsMessage.setToUserName(fromUserName);
							newsMessage.setFromUserName(toUserName);
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
							newsMessage.setFuncFlag(0);
							List<Article> articleList = new ArrayList<Article>();
							Article article = new Article();
							article.setTitle("扫描二维码，赢取代金券！");
							article.setDescription("凡是扫描二维码加关注，百分之百送代金券！");
							article.setPicUrl("http://m2.quanjing.com/2m/chineseview054/gfs-img_8384.jpg");
							article.setUrl("http://www.nmbaidai.com/platform/weChat/wcSpread?customerId="+customer.getId());
							articleList.add(article);
							// 设置图文消息个数
							newsMessage.setArticleCount(articleList.size());
							// 设置图文消息包含的图文集合
							newsMessage.setArticles(articleList);
							// 将图文消息对象转换成xml字符串
							respMessage = MessageUtil.newsMessageToXml(newsMessage);
						} else {
							TextMessage textMessage = new TextMessage();
							textMessage.setToUserName(fromUserName);
							textMessage.setFromUserName(toUserName);
							textMessage.setCreateTime(new Date().getTime());
							textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
							textMessage.setFuncFlag(0);
							textMessage.setContent("您还不是代理，无法生成推广图片。");
							respMessage = MessageUtil.textMessageToXml(textMessage);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}
	
	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	/**
	 * 获取推广二维码图片地址
	 * @param customerId
	 * @param accessToken
	 * @return
	 */
	public String getQrcodeUrlWithCustomerIdAccessToken(Customer customer) {
		String str = WeChatUtil.httpRequest(RequestUrlUtil.getQRCodeUrl(WeChatUtil.getAccessToken(customer.getUser())), "POST", "{\"expire_seconds\": 604800, \"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+customer.getId()+"\"}}}");
		Map<String, String> customerMap = JsonUtil.json2Map(str);
		String ticket = customerMap.get("ticket");
		return RequestUrlUtil.getExchangeQRCodeUrl(ticket);
	}
	/**
	 * 测试
	 * @return
	 */
	public String test(HttpServletRequest request) {
//		String str = WeChatUtil.httpRequest(RequestUrlUtil.getQRCodeUrl(WeChatUtil.getAccessToken(UserHelper.getCurrUserID())), "POST", "{\"expire_seconds\": 604800, \"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"20150521182855367979028155583269\"}}}");
//		String str = WeChatUtil.httpRequest(RequestUrlUtil.getUserInfoUrl("ogWKus1ilU4hsdZApEwOqJVFuzyI"), "GET", null);
//		String str = WeChatUtil.httpRequest(RequestUrlUtil.getExchangeQRCode("gQH67zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL1FraUJJcGJsc2luTG8xaHN5R1pGAAIEFqldVQMEgDoJAA=="), "GET", null);
//		String str = "";
//		customerService.saveWithWeChatNumParentId("ogWKus1ilU4hsdZApEwOqJVFuzyI", null);
//		getExtensionPic();
//		try {
//			String toUserName = "gh_a6aceccc4b97";
//			getExtensionPic(request);
//			String str = send(RequestUrlUtil.getUploadUrl("nY0_Z2Bey0AJ6Z3T4Ofpg7souygkgQETgHerbIEgMT8KmGoqN6IgEWl6YKyejiHC8hNnnhSJFpjyURUJs_fXn1Xu25HDXteC5GBK5iKOyD4", "image"), "d://abc.png");
//			System.out.println(str);
//			String str2 = WeChatUtil.httpRequest(requestUrl, requestMethod, outputStr)
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		User user = userService.getUserWithId("00000000000000000000000000000001");
		System.out.println(1);
//		GetPic getPic = new GetPic(request, user, "ogWKus1ilU4hsdZApEwOqJVFuzyI");
//		GetPic getPic1 = new GetPic(request, user, "ogWKus1ilU4hsdZApEwOqJVFuzyI");
//		getPic.start();
//		getPic1.start();
		System.out.println(2);
		return null;
	}
	
	public String getWeChatPaymentJsonWithOrder(Order order, HttpServletRequest httpServletRequest) {
		/*User user = userService.getUserWithId(order.getUser().getId());

		float price = 0;
		for(OrderGoods orderGoods : orderGoodsService.getOrderGoodsListWithOrderId(order)) {
			price = price + (Float.parseFloat(orderGoods.getPrice())*Integer.parseInt(orderGoods.getCount()));
		}
		int totalPrice = (int)(price*100); 
		String nonceStr = UUID.getUUID();
		String ip = WeChatUtil.getIp(httpServletRequest);
		String url = "http://www.nmbaidai.com/platform/weChat/payment?orderId="+order.getId();
		String str = "appid="+user.getAppId()+"&attach=微信支付&body="+user.getName()+"&mch_id="+user.getMchId()
				+"&nonce_str="+nonceStr+"&notify_url="+url+"&openid="+order.getCustomer().getWeChatNum()
				+"&out_trade_no="+order.getId()+"&spbill_create_ip="+ ip
				+"&total_fee="+totalPrice+"&trade_type=JSAPI&key="+user.getApiSecret();
		String sign = new Md5PasswordEncoder().encodePassword(str, null).toUpperCase();
		Map<String, String> map = WeChatUtil.httpRequestReturnMap("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", "<xml>"
		   +"<appid>"+user.getAppId()+"</appid>"
		   +"<attach>微信支付</attach>"
		   +"<body>"+user.getName()+"</body>"
		   +"<mch_id>"+user.getMchId()+"</mch_id>"
		   +"<nonce_str>"+nonceStr+"</nonce_str>"
		   +"<notify_url>"+url+"</notify_url>"
		   +"<openid>"+order.getCustomer().getWeChatNum()+"</openid>"
		   +"<out_trade_no>"+order.getId()+"</out_trade_no>"
		   +"<spbill_create_ip>"+ip+"</spbill_create_ip>"
		   +"<total_fee>"+totalPrice+"</total_fee>"
		   +"<trade_type>JSAPI</trade_type>"
		   +"<sign>"+sign+"</sign>"
		+"</xml>");
		
		String timeStamp = DateUtil.getDateSecond(DateUtil.getCurrDateSecondString()).getTime()+"";
		String str1 = "appId="+map.get("appid")+"&nonceStr="+map.get("nonce_str")
				+"&package=prepay_id="+map.get("prepay_id")+"&signType=MD5"
				+"&timeStamp="+timeStamp+"&key="+user.getApiSecret();
		String paySign = new Md5PasswordEncoder().encodePassword(str1, null).toUpperCase();
		JSONObject json = new JSONObject();
		json.put("appId", map.get("appid"));
		json.put("timeStamp", timeStamp);
		json.put("nonceStr", map.get("nonce_str"));
		json.put("prepayId", map.get("prepay_id"));
		json.put("paySign", paySign);
		json.put("orderId", order.getId());*/
		//return json.toString();
		return null;
	}
	
	public String getJsApiData(Customer customer) {
		User user = userService.getUserWithId(customer.getUser().getId());
//		String str = "appid="+user.getAppId()+"&attach=微信支付&body="+user.getName()+"&mch_id="+user.getMchId()
//				+"&nonce_str="+nonceStr+"&notify_url="+url+"&openid="+order.getCustomer().getWeChatNum()
//				+"&out_trade_no="+order.getId()+"&spbill_create_ip="+ ip
//				+"&total_fee="+totalPrice+"&trade_type=JSAPI&key="+user.getApiSecret();
//		
		 String noncestr = "Wm3WZYTPz0wzccnW";
	     String timestamp = "1414587457";
	     String ticket = WeChatUtil.getJsApiTicket(user);
	     String url = "http://www.nmbaidai.com/platform/weChat/wcSpread?customerId="+customer.getId();
	     String string = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
	     System.out.println(string);
	     MessageDigest md = null;
	     String signature = null;
	     try {
	    	 md = MessageDigest.getInstance("SHA-1");
	    	 // 将三个参数字符串拼接成一个字符串进行sha1加密
	    	 byte[] digest = md.digest(string.getBytes());
	    	 signature = byteToStr(digest);
	     } catch (NoSuchAlgorithmException e) {
	    	 e.printStackTrace();
	     }
	     System.out.println(signature);
	     JSONObject json = new JSONObject();
	     //json.put("appId", user.getAppId());
	     json.put("timeStamp", timestamp);
	     json.put("nonceStr", noncestr);
	     json.put("signature", signature);
	     return json.toString();
	}
	
	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}
	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private LevelService levelService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderGoodsService orderGoodsService;
	
}