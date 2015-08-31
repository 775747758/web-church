package com.unitever.module.customer.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitever.module.customer.dao.manual.CustomerDAO;
import com.unitever.module.customer.model.Customer;
import com.unitever.module.customer.vo.CustomerListVo;
import com.unitever.module.user.model.User;
import com.unitever.module.user.service.UserService;
import com.unitever.module.weChat.util.EmojiFilter;
import com.unitever.module.weChat.util.RequestUrlUtil;
import com.unitever.module.weChat.util.WeChatUtil;
import com.unitever.platform.core.common.helper.UserHelper;
import com.unitever.platform.core.dao.Page;
import com.unitever.platform.util.JsonUtil;

@Service
@Transactional
public class CustomerService {

	/**
	 * 根据id获取客户信息
	 * @param id
	 * @return
	 */
	public Customer getCustomerWithId(String id) {
		Customer customer = customerDAO.get(id);
		String str = WeChatUtil.httpRequest(RequestUrlUtil.getUserInfoUrl(WeChatUtil.getAccessToken(customer.getUser()), customer.getWeChatNum()), "GET", null);
		Map<String, String> customerMap = JsonUtil.json2Map(str);
		customer.setHeadimgurl(customerMap.get("headimgurl"));
		return customer;
	}
	
	public Customer getCustomerWithHeadimg(String customerId) {
		Customer customer = customerDAO.get(customerId);
		String str = WeChatUtil.httpRequest(RequestUrlUtil.getUserInfoUrl(WeChatUtil.getAccessToken(customer.getUser()), customer.getWeChatNum()), "GET", null);
		Map<String, String> customerMap = JsonUtil.json2Map(str);
		customer.setHeadimgurl(customerMap.get("headimgurl"));
		return customer;
	}
	/**
	 * 根据用户获取客户集合
	 * @return
	 */
	public List<Customer> getCustomerListWithUser(User user) {
		Customer customer = new Customer();
		customer.setUser(user);
		return customerDAO.getCustomerListWithCustomer(customer);
	}
	/**
	 * 修改
	 * @param customer
	 */
	public void update(Customer customer) {
		customerDAO.update(customer);
	}
	/**
	 * 取消代理
	 * @param id
	 */
	public void cancelAgent(String id) {
		Customer customer = customerDAO.get(id);
		customer.setKind(Customer.CUSTOMER_KIND_UNAGENT);
		customerDAO.update(customer);
	}
	/**
	 * 根据用户微信平台号、客户微信号、父级id保存客户信息
	 * @param userWeChatNum
	 * @param weChatNum
	 * @param parentId
	 */
	public void saveWithWeChatNumParentId(String userWeChatNum, String weChatNum, String parentId) {
		User user = userService.getUserWithWeChatNum(userWeChatNum);
		if(getCustomerWithWeChatNum(weChatNum, user)==null) {
			Customer customer = new Customer();
			customer.setKind(Customer.CUSTOMER_KIND_UNAGENT);
			customer.setUser(user);
			if(StringUtils.isNotBlank(parentId)) {
				customer.setParent(new Customer(parentId));
			}
			customer.setWeChatNum(weChatNum);
			customer.setCommission("0.00");
			customer.setCash("0.00");
			String str = WeChatUtil.httpRequest(RequestUrlUtil.getUserInfoUrl(WeChatUtil.getAccessToken(user), weChatNum), "GET", null);
			Map<String, String> customerMap = JsonUtil.json2Map(str);
			customer.setName(EmojiFilter.filterEmoji(customerMap.get("nickname")));
			customerDAO.save(customer);
		}
	}
	/**
	 * 获取客户分页对象
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Customer> getPage(Page<Customer> page, Customer customer) {
		customer.setUser(UserHelper.getCurrUser());
		List<Customer> customerList = customerDAO.getCustomerListWithCustomer(customer);
		page.setTotalRecord(customerList.size());
		if (customerList.size() >= (page.getStartOfPage() + page.getPageSize())) {
			page.setResults(customerList.subList(page.getStartOfPage(), page.getStartOfPage() + page.getPageSize()));
		} else {
			page.setResults(customerList.subList(page.getStartOfPage(), customerList.size()));
		}
		return page;
	}
	/**
	 * 根据code、用户微信平台号获取客户id
	 * @param code
	 * @param userWeChatNum
	 * @return
	 */
	public Customer getCustomerWithCodeUser(String code, User user) {
		Customer customer = new Customer();
		/*if(StringUtils.isNotBlank(code)){
			String str = WeChatUtil.httpRequest(RequestUrlUtil.getAuthorizationAccessTokenUrl(user.getAppId(), user.getAppSecret(), code), "GET", null);
			Map<String, String> map = JsonUtil.json2Map(str);
			String openId = map.get("openid");
			customer = getCustomerWithWeChatNum(openId, user);
			String str1 = WeChatUtil.httpRequest(RequestUrlUtil.getUserInfoUrl(WeChatUtil.getAccessToken(user), openId), "GET", null);
			Map<String, String> customerMap = JsonUtil.json2Map(str1);
			customer.setName(EmojiFilter.filterEmoji(customerMap.get("nickname")));
			customer.setHeadimgurl(customerMap.get("headimgurl"));
		}*/
		return customer;
	}
	/**
	 * 根据客户微信号、用户获取客户信息
	 * @param weChatNum
	 * @param user
	 * @return
	 */
	public Customer getCustomerWithWeChatNum(String weChatNum, User user) {
		Customer customer = new Customer();
		customer.setWeChatNum(weChatNum);
		customer.setUser(user);
		return customerDAO.getCustomerListWithCustomer(customer).size()>0?customerDAO.getCustomerListWithCustomer(customer).get(0):null;
	}
	
	public void initCustomer() {
		User user = UserHelper.getCurrUser();
		List<Customer> customerList = getCustomerListWithUser(user);
		String str = WeChatUtil.httpRequest(RequestUrlUtil.getUserListUrl(WeChatUtil.getAccessToken(user), ""), "GET", null);
		CustomerListVo customerListVo = JsonUtil.json2JavaPojo(str, CustomerListVo.class);
		Map<String, List<String>> map = customerListVo.getData();
		for(String openId : map.get("openid")) {
			boolean flag = true;
			for(Customer customer : customerList) {
				if(openId.equals(customer.getWeChatNum())) {
					flag = false;
				}
			}
			if(flag) {
				System.out.println(openId);
				//saveWithWeChatNumParentId(user.getWeChatNum(), openId, "");
			}
		}
	}
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private UserService userService;
	
}