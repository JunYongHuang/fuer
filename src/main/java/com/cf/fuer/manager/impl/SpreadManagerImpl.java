package com.cf.fuer.manager.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.dao.ISpreadDao;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.ISpreadManager;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.SpreadUser;
import com.cf.fuer.validator.SpreadValidator;
import com.cf.fuer.webapp.bean.SearchBean;

/**
 * Created by IntelliJ IDEA. User: kangmor Date: 13-7-4 Time: 下午3:12
 */
@Service
public class SpreadManagerImpl implements ISpreadManager {
	@Autowired
	private ISpreadDao spreadDao;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private SpreadValidator spreadValidator;

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
	public int saveIP(String ip, String serverCode, String managerId) {
		return spreadDao.saveIP(ip, serverCode, managerId);
	}

	public boolean ipExist(String ip) {
		return spreadDao.ipExist(ip);
	}

	@Override
	public void saveSpreadUser(Long userId, String name, String addressCode) throws ValidationException {
		spreadValidator.validateCreate(userId, name, addressCode);
		spreadDao.saveSpreadUser(userId, name, addressCode);
		userManager.updateUserRole(userId, CommonConstants.ROLE.ROLE_SPREAD);
	}

	@Override
	public SpreadUser getSpreadUser(Long userId) {
		return spreadDao.getSpreadUser(userId);
	}

	@Override
	public void search(SearchBean searchBean, Date dateFrom, Date dateTo) {
		spreadDao.search(searchBean, dateFrom, dateTo);
	}

	@Override
	public SpreadUser getSpreadUserWithData(Long userId) {
		return spreadDao.getSpreadUserWithData(userId);
	}

	@Override
	public void updateSpreadAddressCode(Long userId, String addressCode) throws ValidationException {
		spreadValidator.validateUpdate(userId, addressCode);
		spreadDao.updateSpreadAddressCode(userId, addressCode);
	}
}
