package com.cf.fuer.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.bank.ChargeNumberUtils;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.manager.IVoteManager;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.validator.UserValidator;

/**
 * 此Controller主要用于弹出页面的AJAX请求.
 * 
 * @author sunke
 * 
 * @Date 2011-12-13
 * 
 */
@Component
public class AjaxUtil {

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private IVoteManager voteManager;

	@Autowired
	private IUserManager userManager;

	/**
	 * 检测验证码
	 * 
	 * @param code
	 *            用户填写的验证码
	 * @return true 验证码填写正确, false 验证码填写错误
	 */
	public Boolean checkCode(String code) {
		if (userValidator.isBlank(code)) {
			return false;
		}
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		String codeInSession = (String) session.getAttribute(RandomCode.RANDOM_CODE);
		return code.equalsIgnoreCase(codeInSession);
	}
	
	/**
	 * 获取活动返还金币数量
	 * @param serverCode
	 * @param rmbMoney 单位分
	 * @return
	 */
	public Long getPresentGold(String serverCode, long rmbMoney){
		return ChargeNumberUtils.getPresntGold(serverCode, rmbMoney);
	}
	
	/**
	 * 验证注册时的用户名
	 */
	public String checkUsername(String username){
		try {
			userValidator.validateRegUsername(username);
		} catch (ValidationException e) {
			return e.getDefaultMessage();
		}
		return "";		
	}
	/**
	 * 验证注册时的密码
	 */
	public String checkPwd(String username,String password){
		try {
			userValidator.validateRegPwd(password, username);
		} catch (ValidationException e) {
			return e.getDefaultMessage();
		}
		return "";		
	}
	/**
	 * 验证注册时的姓名
	 */
	public String checkName(String name){
		try {
			userValidator.validateName(name);
		} catch (ValidationException e) {
			return e.getDefaultMessage();
		}
		return "";	
	}

	/**
	 * 验证注册时的Email
	 */
	public String checkEmail(String email){
		try {
			userValidator.validateRegEmail(email);
		} catch (ValidationException e) {
			return e.getDefaultMessage();
		}
		return "";	
	}

	/**
	 * 验证注册时的身份证号
	 */
	public String checkIdcard(String idcard){
		try {
			userValidator.validateRegIdcard(idcard);
		} catch (ValidationException e) {
			return e.getDefaultMessage();
		}
		return "";		
	}

	/**
	 * 保存投票
	 */
	public String voteWithReg(Long voteSelect, String name, String email, String mobile, String addr) {
		if (voteSelect == -1) {
			return "请选择您支持的球队!";
		}
		if (!userValidator.isCorrectName(name)) {
			return "姓名必须为2～4个汉字!";
		}
		try {
			userValidator.validateRegEmail(email);
		} catch (ValidationException e) {
			return e.getDefaultMessage();
		}
		if (!userValidator.checkPhoneNumber(mobile)) {
			return "请输入正确的手机号码!";
		}
		if (!userValidator.isCorrectAddress(addr)) {
			return "请输入正确的地址!";
		}

		WebContext webContext = WebContextFactory.get();
		String ip = webContext.getHttpServletRequest().getRemoteAddr();
		voteManager.voteWithReg(voteSelect, name, email, mobile, addr, ip);
		return "";
	}
	
	public String sinaLogin(String username, String password){
			try {
				if(userValidator.isBlank(username) || userValidator.isBlank(password)){
					return "empty";
				}
				if(userValidator.notExist("user", "username", username)){//用户不存在,注册一个并登陆
					User user = new User();
					user.setUsername(username);
					user.setPassword(password);
					//user.setFrom(CommonConstants.USER_FROM.SINA);
					Long userId = userManager.add(user);
					user = userManager.get(userId);

					WebContext webContext = WebContextFactory.get();
					HttpServletRequest request = webContext.getHttpServletRequest();
					AccessHelper.login(user);
		            TokenUtil.saveLoginUserToken(request);
					System.out.println(username + "登陆注册！");
				}
			} catch (ValidationException e) {
				e.printStackTrace();
			}
		return "needLogin";
	}
	
	/**
	 * 保存新浪过来的注册用户
	 */
	public String sinaReg(String username, String pwd, String rePwd, String email,String from){
		User user = new User();
		user.setUsername(username);
		user.setPassword(pwd);
		user.setConfirmPassword(rePwd);
		user.setEmail(email);
		//user.setFrom("102");
		user.setSourceFrom(from);
		try {
			userManager.create(user);

			WebContext webContext = WebContextFactory.get();
			HttpServletRequest request = webContext.getHttpServletRequest();
			AccessHelper.login(user);
            TokenUtil.saveLoginUserToken(request);
			System.out.println(username + "通过"+from+"注册！");
			
		} catch (ValidationException e) {
			return e.getDefaultMessage();
		}
		return "";
	}
	
	/**
	 * 投票不带注册
	 */
	public void voteNoReg(Long voteSelect){
		WebContext webContext = WebContextFactory.get();
		String ip = webContext.getHttpServletRequest().getRemoteAddr();
		voteManager.voteNoReg(voteSelect, ip);
	}

}
