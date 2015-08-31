package com.unitever.module.weChat.util;

import gui.ava.html.image.generator.HtmlImageGenerator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.customer.service.CustomerService;
import com.unitever.module.user.model.User;
import com.unitever.platform.core.spring.SpringContextHolder;
import com.unitever.platform.util.JsonUtil;

public class GetPic extends Thread {
	
	private User user;
	private Customer customer;
	
	public GetPic(User user, Customer customer) {
		this.user = user;
		this.customer = customer;
	}
	
	@Override
	public void run() {
		try {
			if(StringUtils.isBlank(customer.getMediaId())) {
				HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
				imageGenerator.loadUrl("http://localhost/platform/weChat/wcSpread?customerId="+customer.getId()+"&accessToken="+WeChatUtil.getAccessToken(user));
				imageGenerator.getBufferedImage();
				imageGenerator.saveAsImage("c://temp/"+customer.getId()+".png");
				String mediaId = send(RequestUrlUtil.getUploadUrl(WeChatUtil.getAccessToken(user), "image"), "c://temp/"+customer.getId()+".png");
				System.out.println(mediaId);
				CustomerService customerService = SpringContextHolder.getBean(CustomerService.class);
				customer.setMediaId(mediaId);
				customerService.update(customer);
				String str = WeChatUtil.httpRequest(RequestUrlUtil.getSendMessageWithOpenIdUrl(WeChatUtil.getAccessToken(user)), "POST", 
						"{\"touser\":\""+customer.getWeChatNum()+"\",\"image\":{\"media_id\":\""+mediaId+"\"},\"msgtype\":\"image\"}");
				System.out.println(str);
			}else {
				String str = WeChatUtil.httpRequest(RequestUrlUtil.getSendMessageWithOpenIdUrl(WeChatUtil.getAccessToken(user)), "POST", 
						"{\"touser\":\""+customer.getWeChatNum()+"\",\"image\":{\"media_id\":\""+customer.getMediaId()+"\"},\"msgtype\":\"image\"}");
				System.out.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String send(String url, String filePath) throws IOException {  
	    String result = null;  
	    File file = new File(filePath);  
	    if (!file.exists() || !file.isFile()) {  
	    	throw new IOException("文件不存在");  
	    }  
	    /** 
	    * 第一部分 
	    */  
	    URL urlObj = new URL(url);  
	    // 连接  
	    HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();  
	    /** 
	    * 设置关键值 
	    */  
	    con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式  
	    con.setDoInput(true);  
	    con.setDoOutput(true);  
	    con.setUseCaches(false); // post方式不能使用缓存  
	    // 设置请求头信息  
	    con.setRequestProperty("Connection", "Keep-Alive");  
	    con.setRequestProperty("Charset", "UTF-8");  
	    // 设置边界  
	    String BOUNDARY = "----------" + System.currentTimeMillis();  
	    con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);  
	    // 请求正文信息  
	    // 第一部分：  
	    StringBuilder sb = new StringBuilder();  
	    sb.append("--"); // 必须多两道线  
	    sb.append(BOUNDARY);  
	    sb.append("\r\n");  
	    sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");  
	    sb.append("Content-Type:application/octet-stream\r\n\r\n");  
	    byte[] head = sb.toString().getBytes("utf-8");  
	    // 获得输出流  
	    OutputStream out = new DataOutputStream(con.getOutputStream());  
	    // 输出表头  
	    out.write(head);  
	    // 文件正文部分  
	    // 把文件已流文件的方式 推入到url中  
	    DataInputStream in = new DataInputStream(new FileInputStream(file));  
	    int bytes = 0;  
	    byte[] bufferOut = new byte[1024];  
	    while ((bytes = in.read(bufferOut)) != -1) {  
	    	out.write(bufferOut, 0, bytes);  
	    }  
	    in.close();  
	    // 结尾部分  
	    byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线  
	    out.write(foot);  
	    out.flush();  
	    out.close();  
	    StringBuffer buffer = new StringBuffer();  
	    BufferedReader reader = null;  
	    try {
		    // 定义BufferedReader输入流来读取URL的响应  
		    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));  
		    String line = null;  
		    while ((line = reader.readLine()) != null) {  
			    //System.out.println(line);  
			    buffer.append(line);  
		    }  
		    if(result==null){  
		    	result = buffer.toString();  
		    }  
	    } catch (IOException e) {  
		    System.out.println("发送POST请求出现异常！" + e);  
		    e.printStackTrace();  
		    throw new IOException("数据读取异常");  
	    } finally {  
		    if(reader!=null){  
		    	reader.close();
		    }  
	    } 
	    System.out.println(result);
	    Map<String, String> customerMap = JsonUtil.json2Map(result);
	    String mediaId = customerMap.get("media_id");
	    return mediaId;  
    }
}