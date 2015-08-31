package com.unitever.module.propaganda.dao.manual;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unitever.module.propaganda.model.Propaganda;
import com.unitever.platform.core.dao.BaseDAO;

@Repository
public class PropagandaDAO extends BaseDAO<Propaganda, String> {
	
	/**
	 * 根据销售话术信息查询销售话术集合
	 * @param propaganda
	 * @return
	 */
	public List<Propaganda> getPropagandaListWithPropaganda(Propaganda propaganda) {
		return this.getList("getPropagandaListWithPropaganda", propaganda);
	}
}