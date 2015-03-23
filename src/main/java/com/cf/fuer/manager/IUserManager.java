package com.cf.fuer.manager;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.model.User;
import com.cf.fuer.webapp.bean.SearchBean;

public interface IUserManager {

	void create(User user) throws ValidationException;

	/**
	 * 更新用户信息
	 * 
	 * @return
	 */
	Long update(User user) throws ValidationException;

	/**
	 * 更改密码.
	 * 
	 * @param oldPwd
	 *            旧密码
	 * @param newPwd
	 *            新密码
	 * @param rePwd
	 *            确认密码
	 * @throws ValidationException
	 */
	void savePassword(User loginUser, String oldPwd, String newPwd, String rePwd) throws ValidationException;

	/**
	 * 根据用户名获取用户.
	 */
	User loadUserByUsername(String username);

	/**
	 * 忘记密码时,重设密码
	 */
	void saveNewPassword(User user, String newPwd);

	/**
	 * 根据邮箱获取用户, 用于忘记密码发送邮件
	 */
	User getByEmail(String email);

	/**
	 * 投票后注册用户
	 * 
	 * @return
	 */
	Long createAfterVote(String email, String name);

	/**
	 * 创建第三方登陆用户.
	 */
	Long createThirdUser(String username, int gender, int loginType, String openId, String name, String from);

	void search(SearchBean searchBean);

	User get(Long id);

	/**
	 * 管理员添加用户
	 * 
	 * @param user
	 * @return
	 * @throws ValidationException
	 */
	Long add(User user) throws ValidationException;

	/**
	 * 管理员重置用户密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	boolean resetPassword(Long userId);

	/**
	 * 更改用户类型
	 */
	void updateUserRole(long id, int role);

	/**
	 * 更新用户带EMAIL,用于管理员更新用户信息
	 */
	Long updateWithEmail(User user) throws ValidationException;

	/**
	 * 第三登陆用户填写邮箱后,发送密码到邮箱,发送邮件失败返回false
	 */
	boolean resetPwdAndSendEmail(User user) throws ValidationException;
	
	/**
	 * 同一IP不可注册多个小号
	 */
	boolean canRegister(String ip);

	/**
	 * 推广邮件
	 * @param to
	 * @param presnetCode
	 * @param username
	 */
	void sendSpreadEmail();

}
