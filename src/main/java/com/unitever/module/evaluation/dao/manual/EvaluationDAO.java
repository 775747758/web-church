package com.unitever.module.evaluation.dao.manual;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unitever.module.evaluation.model.Evaluation;
import com.unitever.platform.core.dao.BaseDAO;

@Repository
public class EvaluationDAO extends BaseDAO<Evaluation, String> {
	public List<Evaluation> getEvaluationListWithEvaluation(Evaluation evaluation) {
		return this.getList("getEvaluationListWithEvaluation", evaluation);
	}
}
