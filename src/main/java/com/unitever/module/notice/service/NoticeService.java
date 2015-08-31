package com.unitever.module.notice.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jpush.api.JPushClient;

import com.unitever.module.notice.dao.manual.NoticeDAO;
import com.unitever.module.notice.model.Notice;

@Service
@Transactional
public class NoticeService {
	
	private static final Log log = LogFactory.getLog(NoticeService.class);
	
	public static JPushClient jpushClient = new JPushClient("4b848e24e3333e71bab7a580", "488ff5a64b53dfa83c32346f", 3);
	
	/**
	 * 查询所有通知
	 */
	public List<Notice> getAllNotice() {
		return noticeDAO.getAll();
	}
	
	public void save(Notice notice){
		noticeDAO.save(notice);
	}
	
	/*
	 * pageNo 从1开始
	 */
	public List<Notice> getByPageNo(int pageNo,int count){
		if(pageNo<=0||count<=0){
			System.out.println("pageNo或者count小于0");
			return null;
		}else {
			return noticeDAO.getByLimit((pageNo-1)*count, count);
		}
	}
	
	public int getNoticeCount(){
		return noticeDAO.getNoticeCount();
	}
	
	@Autowired
	private NoticeDAO noticeDAO;
}