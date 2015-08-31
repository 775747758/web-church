package com.unitever.module.withdrawCash.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitever.module.customer.model.Customer;
import com.unitever.module.customer.service.CustomerService;
import com.unitever.module.user.model.User;
import com.unitever.module.weChat.util.RequestUrlUtil;
import com.unitever.module.weChat.util.WeChatUtil;
import com.unitever.module.withdrawCash.dao.manual.WithdrawCashDAO;
import com.unitever.module.withdrawCash.model.WithdrawCash;
import com.unitever.platform.component.attachment.util.AttachmentUtil;
import com.unitever.platform.core.common.helper.UserHelper;
import com.unitever.platform.core.dao.Page;
import com.unitever.platform.util.DateUtil;
import com.unitever.platform.util.FileUtil;
import com.unitever.platform.util.UUID;
import com.unitever.platform.util.excel.ExcelSheetVO;
import com.unitever.platform.util.excel.ExcelUtil;

@Service
@Transactional
public class WithdrawCashService {

	/**
	 * 根据用户获取提现单集合
	 * @param user
	 * @return
	 */
	public List<WithdrawCash> getWithdrawCashListWithUser(User user) {
		WithdrawCash withdrawCash = new WithdrawCash();
		withdrawCash.setUser(user);
		return withdrawCashDAO.getWithdrawCashListWithWithdrawCash(withdrawCash);
	}
	/**
	 * 根据id获取提现单
	 * @param id
	 * @return
	 */
	public WithdrawCash getWithdrawCashWithId(String id) {
		return withdrawCashDAO.get(id);
	}
	/**
	 * 修改
	 * @param withdrawCash
	 */
	public void update(WithdrawCash withdrawCash) {
		withdrawCashDAO.update(withdrawCash);
	}
	/**
	 * 添加
	 * @param withdrawCash
	 */
	public void save(WithdrawCash withdrawCash) {
		withdrawCash.setCode(getWithdrawCashCodeWithUser(withdrawCash.getUser()));
		withdrawCash.setDate(DateUtil.getCurrDateString());
		withdrawCash.setState(WithdrawCash.WITHDRAWCASH_STATE_UNFINSHED);
		withdrawCashDAO.save(withdrawCash);
		Customer customer = customerService.getCustomerWithId(withdrawCash.getCustomer().getId());
		customer.setCash(Float.parseFloat(withdrawCash.getMoney())+Float.parseFloat(customer.getCash())+"");
		customerService.update(customer);
	}
	/**
	 * 获取提现单code
	 * @param user
	 * @return
	 */
	private String getWithdrawCashCodeWithUser(User user) {
		String code = DateUtil.getCurrDateString().replace("-", "")+"001";
		WithdrawCash withdrawCash = new WithdrawCash();
		withdrawCash.setUser(user);
		for(int i=0;i<999;i++) {
			boolean flag = true;
			String newCode = Long.parseLong(code)+i+"";
			for(WithdrawCash newWithdrawCash : withdrawCashDAO.getWithdrawCashListWithWithdrawCash(withdrawCash)) {
				if(newCode.equals(newWithdrawCash.getCode())) {
					flag = false;
				}
			}
			if(flag) {
				return newCode;
			}
		}
		return code;
	}
	/**
	 * 获取提现单分页对象
	 * @param page
	 * @param withdrawCash
	 * @return
	 */
	public Page<WithdrawCash> getPage(Page<WithdrawCash> page, WithdrawCash withdrawCash) {
		withdrawCash.setUser(UserHelper.getCurrUser());
		List<WithdrawCash> withdrawCashList = withdrawCashDAO.getWithdrawCashListWithWithdrawCash(withdrawCash);
		page.setTotalRecord(withdrawCashList.size());
		if (withdrawCashList.size() >= (page.getStartOfPage() + page.getPageSize())) {
			page.setResults(withdrawCashList.subList(page.getStartOfPage(), page.getStartOfPage() + page.getPageSize()));
		} else {
			page.setResults(withdrawCashList.subList(page.getStartOfPage(), withdrawCashList.size()));
		}
		return page;
	}
	/**
	 * 导出excel
	 * @param withdrawCash
	 */
	public void doExport(WithdrawCash withdrawCash) {
		withdrawCash.setUser(UserHelper.getCurrUser());
		List<WithdrawCash> withdrawCashList = withdrawCashDAO.getWithdrawCashListWithWithdrawCash(withdrawCash);
		List<ExcelSheetVO> excelSheetVOList = new ArrayList<ExcelSheetVO>();
		ExcelSheetVO sheetVo = new ExcelSheetVO();
		sheetVo.setName("提现单");
		List<List<String>> dataList = new ArrayList<List<String>>();
		//第一行
		List<String> firstLineList = new ArrayList<String>();
		firstLineList.add("序号");
		firstLineList.add("订单号");
		firstLineList.add("客户");
		firstLineList.add("开户人");
		firstLineList.add("账号");
		firstLineList.add("开户银行");
		firstLineList.add("金额");
		firstLineList.add("日期");
		firstLineList.add("状态");
		dataList.add(firstLineList);
		//内容
		for(int i=0;i<withdrawCashList.size();i++) {
			List<String> contentList = new ArrayList<String>();
			contentList.add(i+1+"");
			contentList.add(withdrawCashList.get(i).getCode());
			contentList.add(withdrawCashList.get(i).getCustomer().getName());
			contentList.add(withdrawCashList.get(i).getAccountName());
			contentList.add(withdrawCashList.get(i).getAccountNum());
			contentList.add(withdrawCashList.get(i).getAccountBank());
			contentList.add(withdrawCashList.get(i).getMoney());
			contentList.add(withdrawCashList.get(i).getDate());
			contentList.add(withdrawCashList.get(i).getStateValue());
			dataList.add(contentList);
		}
		sheetVo.setDatas(dataList);
		excelSheetVOList.add(sheetVo);
		File dir = new File(AttachmentUtil.getTempDir());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir.getAbsolutePath() + "/" + UUID.getUUID());
		try {
			OutputStream out = new FileOutputStream(file);
			ExcelUtil.exportExcel(excelSheetVOList, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileUtil.download(file, "提现单信息.xls");
	}
	
	public void doComfirm(WithdrawCash withdrawCash) {
		withdrawCashDAO.update(withdrawCash);
		Customer customer = customerService.getCustomerWithId(withdrawCash.getCustomer().getId());
		String str = WeChatUtil.httpRequest(RequestUrlUtil.getSendMessageWithOpenIdUrl(WeChatUtil.getAccessToken(customer.getUser())), "POST", 
				"{\"touser\":\""+customer.getWeChatNum()+"\",\"text\":{\"content\":\"您的提现申请已通过审核，正在进行转账处理，请耐心等待并查收。\"},\"msgtype\":\"text\"}");
		System.out.println(str);
	}
	
	@Autowired
	private WithdrawCashDAO withdrawCashDAO;
	@Autowired
	private CustomerService customerService;
}