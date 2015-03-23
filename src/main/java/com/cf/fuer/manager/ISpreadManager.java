package com.cf.fuer.manager;

import java.util.Date;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.model.SpreadUser;
import com.cf.fuer.webapp.bean.SearchBean;

/**
 * Created by IntelliJ IDEA. User: kangmor Date: 13-7-4 Time: 下午3:12
 */
public interface ISpreadManager {

	public boolean ipExist(String ip);

	/**
	 * 保存点一点活动的IP
	 * 
	 * @param ip
	 *            IP地址
	 * @param serverCode
	 *            服务器代码
	 * @param managerId
	 *            经理人ID
	 * @return
	 */
	public int saveIP(String ip, String serverCode, String managerId);

	public void saveSpreadUser(Long userId, String name, String addressCode) throws ValidationException;

	void updateSpreadAddressCode(Long userId, String addressCode) throws ValidationException;

	SpreadUser getSpreadUser(Long userId);

	SpreadUser getSpreadUserWithData(Long userId);

	void search(SearchBean searchBean, Date dateFrom, Date dateTo);
}
