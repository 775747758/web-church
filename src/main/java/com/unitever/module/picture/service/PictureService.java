package com.unitever.module.picture.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jpush.api.JPushClient;

import com.unitever.module.picture.dao.manual.PictureDAO;
import com.unitever.module.picture.model.Picture;

@Service
@Transactional
public class PictureService {
	
	private static final Log log = LogFactory.getLog(PictureService.class);
	
	public static JPushClient jpushClient = new JPushClient("4b848e24e3333e71bab7a580", "488ff5a64b53dfa83c32346f", 3);
	
	/*
	 * pageNo 从1开始
	 */
	public List<Picture> getByPageNo(int pageNo,int count){
		if(pageNo<=0||count<=0){
			System.out.println("pageNo或者count小于0");
			return null;
		}else {
			return pictureDAO.getByLimit((pageNo-1)*count, count);
		}
	}
	
	public int getPictureCount(){
		return pictureDAO.getPictureCount();
	}
	
	public void save(Picture picture){
		pictureDAO.save(picture);
	}
	
	@Autowired
	private PictureDAO pictureDAO;
}