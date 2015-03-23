package com.cf.fuer.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.ISpreadManager;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.GameRole;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;

/**
 * 管理员Ajax操作
 * 
 * @author sunke
 * 
 */
@Component
public class AdminAjaxUtil {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserManager userManager;
	
	@Autowired 
	private ISpreadManager spreadManager;

	/**
	 * 更改用户的类型
	 */
	public void changeUserRole(long id, boolean isTestUser) {
		if (isLoginAsAdmin()) {
			int role = CommonConstants.ROLE.ROLE_USER;
			if (isTestUser) {
				role = CommonConstants.ROLE.ROLE_TEST;
			}
			log.info("管理员[" + AccessHelper.getLoginUser().getUsername() + "]更改用户[" + id + "]类型为" + role);
			userManager.updateUserRole(id, role);
		}
	}

	/**
	 * 获取用户角色列表
	 * 
	 * @param username
	 * @return
	 */
	public List<GameRole> getUserRoles(String username) {
		return GameServerUtils.getUserRoles(username);
	}

	/**
	 * 重置用户密码.
	 */
	public boolean resetUserPwd(long userId) {
		if (isLoginAsAdmin()) {
			log.info("管理员[" + AccessHelper.getLoginUser().getUsername() + "]重置用户[" + userId + "]密码");
			return userManager.resetPassword(userId);
		}
		return false;
	}
	
	/**
	 * 保存推广人员
	 */
	public String saveSpreadUser(Long userId, String name, String addressCode){
		try {
			spreadManager.saveSpreadUser(userId, name, addressCode);
		} catch (ValidationException e) {
			return e.getDefaultMessage();
		}
		return "";
	}
	
	/**
	 * 修改推广码
	 */
	public String updateSpreadCode(String code){
		try {
			User loginUser = AccessHelper.getLoginUser();
			spreadManager.updateSpreadAddressCode(loginUser.getId(), code);
			log.warn("用户["+loginUser.getUsername()+"]修改推广码为:" + code);
		} catch (ValidationException e) {
			return e.getDefaultMessage();
		}
		return "";
	}

	/**
	 * 管理员权限验证
	 */
	private boolean isLoginAsAdmin() {
		User loginUser = AccessHelper.getLoginUser();
		return loginUser != null && loginUser.getRole() == CommonConstants.ROLE.ROLE_ADMIN;
	}

}
