package com.cf.fuer.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.manager.IEmailManager;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.User;
import com.cf.fuer.util.ControllerUtil;

/**
 * 忘记密码
 */
@Controller
public class ForgetPasswordController {
	private Log log = LogFactory.getLog(ForgetPasswordController.class);
	@Autowired
	private IUserManager userManager;

	@Autowired
	private IEmailManager emailManager;

	private static final String FORM_VIEW = "forget.password";

	private static final String REDIRECT_URL = "redirect:forget.php";

	/**
	 * 随机生成密码长度.
	 */
	private static final int PASSWORD_LENGTH = 8;

	@RequestMapping("/forget.php")
	public String forgetPassword(HttpServletRequest request) {
		String send = ServletRequestUtils.getStringParameter(request, "send", "");
		if (send.isEmpty()) {
			return FORM_VIEW;
		}
		String email = ServletRequestUtils.getStringParameter(request, "email", "");
		if (email.isEmpty()) {
			request.setAttribute("error", "请输入Email地址");
			return FORM_VIEW;
		}
		User user = userManager.getByEmail(email);
		if (user == null) {
			request.setAttribute("error", "用户不存在,请重新输入");
			return FORM_VIEW;
		}
		try {
			String newPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
			String content = "尊敬的用户,您够足球职业足球经理游戏的新密码为:" + newPassword + ",请尽快登陆系统修改密码.";
			emailManager.sendEmail(email, "够足球—职业足球经理游戏-重置密码", content);
			ControllerUtil.setSuccessMessage(request, "新密码已发送,如果10秒之后还没有收到请重新发送!");
			userManager.saveNewPassword(user, newPassword);
			log.info("用户[" + user.getUsername() + "]重置密码到" + email);
		} catch (Exception e) {
			log.error("发送密码到" + email + "出现错误", e);
			// request.setAttribute("error", "抱歉,发生内部错误,发送失败,请尝试使用其它方式再次发送!");
			request.setAttribute("error", "抱歉,发生内部错误,发送失败!");
			return FORM_VIEW;
		}
		return REDIRECT_URL;
	}

}
