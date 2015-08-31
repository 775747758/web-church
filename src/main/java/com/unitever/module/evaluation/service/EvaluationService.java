package com.unitever.module.evaluation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.evaluation.dao.manual.EvaluationDAO;
import com.unitever.module.evaluation.model.Evaluation;
import com.unitever.module.goods.model.Goods;

@Service
@Transactional
public class EvaluationService {

	public void save(Evaluation evaluation) {
		evaluationDAO.save(evaluation);
	}
	public List<Evaluation> getEvaluationListWithCustomerId(String customerId) {
		Evaluation evaluation = new Evaluation();
		evaluation.setCustomer(new Customer(customerId));
		return evaluationDAO.getEvaluationListWithEvaluation(evaluation);
	}
	public List<Evaluation> getEvaluationListWithGoodsId(String goodsId) {
		Evaluation evaluation = new Evaluation();
		evaluation.setGoods(new Goods(goodsId));
		return evaluationDAO.getEvaluationListWithEvaluation(evaluation);
	}
	@Autowired
	private EvaluationDAO evaluationDAO;

}
