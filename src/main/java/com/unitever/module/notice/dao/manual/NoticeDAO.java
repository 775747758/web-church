package com.unitever.module.notice.dao.manual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.unitever.module.notice.model.Notice;
import com.unitever.module.user.model.User;
import com.unitever.platform.core.dao.BaseDAO;

@Repository
public class NoticeDAO extends BaseDAO<Notice, String>{

	public List<Notice> getByLimit(int start,int num){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("num", num);
		return this.getList("getByLimit",params);
	}
	
	public int getNoticeCount(){
		return getSqlSession().selectOne("com.unitever.module.notice.dao.manual.NoticeDAO.getNoticeCount");
	}
	
}