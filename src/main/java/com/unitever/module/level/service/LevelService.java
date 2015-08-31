package com.unitever.module.level.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitever.module.level.dao.manual.LevelDAO;
import com.unitever.module.level.model.Level;
import com.unitever.module.user.model.User;
import com.unitever.platform.core.common.helper.UserHelper;
import com.unitever.platform.core.dao.Page;

@Service
@Transactional
public class LevelService {

	/**
	 * 根据用户获取层级集合
	 * @param user
	 * @return
	 */
	public List<Level> getLevelListWithUserKind(User user, String kind) {
		return levelDAO.getLevelListWithUserIdKind(user.getId(), kind);
	}
	/**
	 * 获取分页对象
	 * @param page
	 * @param level
	 * @return
	 */
	public Page<Level> getPage(Page<Level> page, Level level) {
		List<Level> levelList = levelDAO.getLevelListWithUserIdKind(UserHelper.getCurrUser().getId(), null);
		page.setTotalRecord(levelList.size());
		if (levelList.size() >= (page.getStartOfPage() + page.getPageSize())) {
			page.setResults(levelList.subList(page.getStartOfPage(), page.getStartOfPage() + page.getPageSize()));
		} else {
			page.setResults(levelList.subList(page.getStartOfPage(), levelList.size()));
		}
		return page;
	}
	
	public Level getLevelWithId(String id) {
		return levelDAO.get(id);
	}
	
	public void update(Level level) {
		levelDAO.update(level);
	}
	
	public void save(Level level) {
		levelDAO.save(level);
	}
	
	public void deleteWithId(String id) {
		levelDAO.delete(id);
	}
	
	@Autowired
	private LevelDAO levelDAO;
}