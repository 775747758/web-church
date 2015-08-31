package com.unitever.module.withdrawCash.dao.manual;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unitever.module.withdrawCash.model.WithdrawCash;
import com.unitever.platform.core.dao.BaseDAO;

@Repository
public class WithdrawCashDAO extends BaseDAO<WithdrawCash, String> {

	/**
	 * 根据提现单信息获取提现单集合
	 * @param withdrawCash
	 * @return
	 */
	public List<WithdrawCash> getWithdrawCashListWithWithdrawCash(WithdrawCash withdrawCash) {
		return this.getList("getWithdrawCashListWithWithdrawCash", withdrawCash);
	}
}