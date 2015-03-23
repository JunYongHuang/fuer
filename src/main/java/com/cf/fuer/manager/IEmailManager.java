package com.cf.fuer.manager;

public interface IEmailManager {
	/**
	 * 发送邮件信息
	 * 
	 * @param emailAddress
	 *            邮箱地址
	 * @param content
	 *            邮件内容
	 * @Description:
	 */
	void sendEmail(String emailAddress, String subject, String content);

	/**
	 * 完成注册后发送消息(另开线程)到用户邮箱.
	 */
	void sendEmailAfterReg(String email, String username, String password);

	/**
	 * 投票后发送消息(另开线程)到用户邮箱.
	 */
	void sendEmailAfterVote(String email, String password, String name);

	/**
	 * 管理员重置密码后发送消息(另开线程)到用户邮箱.
	 */
	void sendEmailAfterResetPwd(String email, String password, String name);

	/**
	 * 第三登陆用户完善邮箱信息后发送邮件
	 */
	boolean finishEmail(String email, String name, String password);

	/**
	 * 发送推广游戏
	 */
	void sendSpreadEmail(String to, String presnetCode, String username);
}
