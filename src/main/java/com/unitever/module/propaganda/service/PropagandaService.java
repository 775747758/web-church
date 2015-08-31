package com.unitever.module.propaganda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitever.module.propaganda.dao.manual.PropagandaDAO;
import com.unitever.module.propaganda.model.Propaganda;
import com.unitever.module.user.model.User;
import com.unitever.platform.core.common.helper.UserHelper;
import com.unitever.platform.core.dao.Page;

@Service
@Transactional
public class PropagandaService {
	
	/**
	 * 根据用户获取销售话术集合
	 * @return
	 */
	public List<Propaganda> getPropagandaListWithUser(User user) {
		Propaganda propaganda = new Propaganda();
		propaganda.setUser(user);
		return propagandaDAO.getPropagandaListWithPropaganda(propaganda);
	}
	/**
	 * 根据id获取销售话术
	 * @param id
	 * @return
	 */
	public Propaganda getPropagandaWithId(String id) {
		return propagandaDAO.get(id);
	}
	/**
	 * 修改
	 * @param propaganda
	 */
	public void update(Propaganda propaganda) {
		propagandaDAO.update(propaganda);
	}
	/**
	 * 添加
	 * @param propaganda
	 */
	public void save(Propaganda propaganda) {
		propaganda.setUser(UserHelper.getCurrUser());
		propagandaDAO.save(propaganda);
	}
	/**
	 * 根据id删除
	 * @param id
	 */
	public void deleteWithId(String id) {
		propagandaDAO.delete(id);
	}
	/**
	 * 获取销售话术分页对象
	 * @param page
	 * @param propaganda
	 * @return
	 */
	public Page<Propaganda> getPage(Page<Propaganda> page, Propaganda propaganda) {
		propaganda.setUser(UserHelper.getCurrUser());
		List<Propaganda> propagandaList = propagandaDAO.getPropagandaListWithPropaganda(propaganda);
		page.setTotalRecord(propagandaList.size());
		if (propagandaList.size() >= (page.getStartOfPage() + page.getPageSize())) {
			page.setResults(propagandaList.subList(page.getStartOfPage(), page.getStartOfPage() + page.getPageSize()));
		} else {
			page.setResults(propagandaList.subList(page.getStartOfPage(), propagandaList.size()));
		}
		return page;
	}
	
	@Autowired
	private PropagandaDAO propagandaDAO;
}