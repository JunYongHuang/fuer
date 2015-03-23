package com.cf.fuer.dao;

import java.util.Date;

import com.cf.fuer.model.SpreadUser;
import com.cf.fuer.webapp.bean.SearchBean;

/**
 * Created by IntelliJ IDEA. User: kangmor Date: 13-7-4 Time: 下午2:49
 */
public interface ISpreadDao {
	public boolean ipExist(String ip);

	public int saveIP(String ip, String serverCode, String managerId);

	/**
	 * 保存推广人员
	 * 
	 * @param userId
	 *            用户ID
	 * @param name
	 *            推广人
	 * @param addressCode
	 *            推广代码
	 * @return
	 */
	boolean saveSpreadUser(Long userId, String name, String addressCode);

	/**
	 * 增加推广点击次数
	 * 
	 * @param addressCode
	 *            推广代码
	 */
	void addSpreadClick(String addressCode);

	SpreadUser getSpreadUser(Long userId);

	void search(SearchBean searchBean, Date dateFrom, Date dateTo);

	SpreadUser getSpreadUserWithData(Long userId);

	void updateSpreadAddressCode(Long userId, String addressCode);
}
