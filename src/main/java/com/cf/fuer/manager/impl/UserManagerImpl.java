package com.cf.fuer.manager.impl;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.cf.fuer.CommonConstants;
import com.cf.fuer.dao.IUserDao;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IEmailManager;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.User;
import com.cf.fuer.validator.UserValidator;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.bean.SpreadUser;

@Service
public class UserManagerImpl implements IUserManager {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IEmailManager emailManager;

	/**
	 * salt的长度,同时也作为用户重置密码的长度
	 */
	private static final int SALT_LENGTH = 6;

	/**
	 * 注册用户
	 */
	public void create(User user) throws ValidationException {
		userValidator.validateRegister(user);
		String salt = RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
		String password = user.getPassword();
		String encodePassword = passwordEncoder.encodePassword(password, salt);
		user.setPassword(encodePassword);
		user.setSalt(salt);
		user.setRole(CommonConstants.ROLE.ROLE_USER);
		userDao.createUser(user);
		emailManager.sendEmailAfterReg(user.getEmail(), user.getUsername(), password);
	}
	
	/**
	 * 管理员添加用户
	 * 
	 * @throws ValidationException 
	 */
	@Override
	public Long add(User user) throws ValidationException{
		userValidator.validateRegUsername(user.getUsername());
		String salt = RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
		String password = user.getPassword();
		if(!"1".equals(password)){
			String encodePassword = passwordEncoder.encodePassword(password, salt);
			user.setPassword(encodePassword);
		}
		user.setSalt(salt);
		return userDao.createUser(user);
	}
	
	@Override
	public Long createAfterVote(String email, String name){
		String salt = RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
		String password = RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
		String encodePassword = passwordEncoder.encodePassword(password, salt);
		Long userId = userDao.createVoteUser(name, email, encodePassword, salt);
		emailManager.sendEmailAfterVote(email, password, name);
		return userId;
	}


	@Override
	public Long createThirdUser(String username, int gender,int loginType, String openId, String name,String from) {
		String salt = RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
		String password = "thirdlogin";//不加密,无法直接登陆,可以设置邮箱后进行重置
		Long userId = userDao.createThirdUser(username, gender, loginType, openId, name, password, salt,from);		
		return userId;
	}

	/**
	 * 修改密码
	 */
	public void savePassword(User loginUser, String oldPwd, String newPwd, String rePwd) throws ValidationException {
		userValidator.validatePwd(loginUser, oldPwd, newPwd, rePwd);
		saveNewPassword(loginUser, newPwd);
	}

	/**
	 * 忘记密码时,重设密码
	 */
	@Override
	public void saveNewPassword(User user, String newPwd) {
		String newEncodePassword = passwordEncoder.encodePassword(newPwd, user.getSalt());
		userDao.savePassword(user.getId(), newEncodePassword);
	}
	
	@Override
	public boolean resetPassword(Long userId){
		User user = get(userId);
		String email = user.getEmail();
		if(!userValidator.isCorrectEmail(email)){//无邮箱
			return false;
		}
		String newPwd = RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
		saveNewPassword(user, newPwd);
		emailManager.sendEmailAfterResetPwd(email, newPwd, user.getName());
		return true;
	}

	public User loadUserByUsername(String username) {
		return (User) userDao.loadUserByUsername(username);
	}
	
	@Override
	public void sendSpreadEmail(){
		//emailManager.sendSpreadEmail("283599542@qq.com", "ABCD","sunke");
		List<SpreadUser> spreadUsers = userDao.listSpreadUsers();
		int index = 0;
		for (SpreadUser spreadUser : spreadUsers) {
			index = index + 1;
			if(index>=0){
				if(index % 20 == 0){
					System.out.println("-----------------"+ index + "-------------");
				}
				int atindex = spreadUser.getEmail().indexOf("@");
				if(atindex>=5){
					System.out.println(index + "--发送邮件到："+spreadUser.getEmail());
					emailManager.sendSpreadEmail(spreadUser.getEmail(), spreadUser.getTicketId(),spreadUser.getUsername());
				}
			}
		}
		System.out.println("-------------发送完成----------------");
	}
	
	@Override
	public boolean resetPwdAndSendEmail(User user) throws ValidationException{
		userValidator.validateUpdate(user);
		String email = user.getEmail();
		String newPwd = RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
		boolean emailSend = emailManager.finishEmail(email, user.getName(),newPwd);
		if(emailSend){//发送成功后保存信息
			userDao.updateUser(user);
			saveNewPassword(user, newPwd);
		}
		return emailSend;
	}
	

	public Long update(User user) throws ValidationException {
		userValidator.validateUpdate(user);
		return userDao.updateUser(user);
	}

	@Override
	public User getByEmail(String email) {
		return userDao.getByEmail(email);
	}

	@Override
	public void search(SearchBean searchBean) {
		userDao.search(searchBean);
	}

	@Override
	public User get(Long id) {
		return userDao.get(id);
	}

	@Override
	public void updateUserRole(long id, int role) {
		userDao.updateUserRole(id, role);
	}

	@Override
	public Long updateWithEmail(User user) throws ValidationException {
		//userValidator.validateUpdate(user);
		return userDao.updateUserWithEmail(user);
	}

	@Override
	public boolean canRegister(String ip) {
		return userDao.canRegister(ip);
	}

	
	public static void main(String[] args) {
		int atindex = "sun@163.com".indexOf("@");
		System.out.println(atindex);
	}
}
