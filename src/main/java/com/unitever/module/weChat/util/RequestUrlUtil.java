package com.unitever.module.weChat.util;

public class RequestUrlUtil {

	/**获取access_token*/
	private static final String get_access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	/**获取用户详细信息*/
	private static final String get_user_info = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	/**获取二维码*/
	private static final String get_qr_code = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	/**换取二维码*/
	private static final String exchange_qr_code = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	/**获取授权的access_token*/
	private static final String get_authorization_access_token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	/**创建自定义菜单*/
	private static final String create_menu = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	private static final String upload = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";
	
	private static final String sendMessageWithOpenId = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
	
	private static final String get_user_list = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	
	private static final String get_jsapi_ticket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	public static String getAccessTokenUrl(String appId, String secret) {
		return get_access_token.replace("APPID", appId).replace("APPSECRET", secret);
	}
	
	public static String getJSApiTicketUrl(String accessToken) {
		return get_jsapi_ticket.replace("ACCESS_TOKEN", accessToken);
	}
	
	public static String getUserInfoUrl(String accessToken, String openId) {
		return get_user_info.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
	}
	
	public static String getQRCodeUrl(String accessToken) {
		return get_qr_code.replace("ACCESS_TOKEN", accessToken);
	}
	
	public static String getExchangeQRCodeUrl(String ticket) {
		return exchange_qr_code.replace("TICKET", ticket);
	}
	
	public static String getAuthorizationAccessTokenUrl(String appId, String secret, String code) {
		return get_authorization_access_token.replace("APPID", appId).replace("SECRET", secret).replace("CODE", code);
	}
	
	public static String createMenuUrl(String accessToken) {
		return create_menu.replace("ACCESS_TOKEN", accessToken);
	}
	
	public static String getUploadUrl(String accessToken, String kind) {
		return upload.replace("ACCESS_TOKEN", accessToken).replace("TYPE", kind);
	}
	
	public static String getSendMessageWithOpenIdUrl(String accessToken) {
		return sendMessageWithOpenId.replace("ACCESS_TOKEN", accessToken);
	}
	
	public static String getUserListUrl(String accessToken, String nextOpenId) {
		return get_user_list.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenId);
	}
}
