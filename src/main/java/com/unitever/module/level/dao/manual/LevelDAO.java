package com.unitever.module.level.dao.manual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.unitever.module.level.model.Level;
import com.unitever.platform.core.dao.BaseDAO;

@Repository
public class LevelDAO extends BaseDAO<Level, String> {


	public List<Level> getLevelListWithUserIdKind(String userId, String kind) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("kind", kind);
		return this.getList("getLevelWithUserIdKind", params);
	}
}
