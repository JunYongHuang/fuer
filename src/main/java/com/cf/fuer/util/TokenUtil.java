package com.cf.fuer.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cf.fuer.dao.IUserDao;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;

@Component
public class TokenUtil {

	private static Log log = LogFactory.getLog(TokenUtil.class);

	private static IUserDao userDao;

	/**
	 * 保存用户token,登陆成功后调用.
	 */
	public static void saveLoginUserToken(HttpServletRequest request) {
		User loginUser = AccessHelper.getLoginUser();
		String username = loginUser.getUsername();
		String ip = getRequestIp(request);
		loginUser.setLoginIp(ip);
		log.info("用户 [" + username + " role:" + loginUser.getRole() + "] 登陆,IP:" + ip);
		userDao.updateLogin(ip, loginUser.getUsername());
	}

	/**
	 * 从request中获取IP地址(如果经过代理,从http:头信息中获取)
	 * 
	 * @param request
	 * @return 客户IP地址
	 */
	public static String getRequestIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null) {
			return request.getRemoteAddr();
		}
		return ip;
	}

	@Autowired
	public void setUserDao(IUserDao userDao) {
		TokenUtil.userDao = userDao;
	}

	/**
	 * 插入用户token到数据库,通常不会调用,当中央服务器无法连接时使用.
	 */
	public static void insertToken(String username, int age, String token, String serverCode) {
		userDao.insertToken(username, age, token, serverCode);
	}

	/**
	 * 生成用户Token,Token长度为64位,前几位是用户的ID(保证不重复)
	 */
	public static String generateTokenStr(Long userId) {
		String userIdStr = String.valueOf(userId);
		String tokenSuffix = RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH - userIdStr.length());
		return userIdStr + tokenSuffix;
	}

	/**
	 * token长度
	 */
	private static final int TOKEN_LENGTH = 48;

}
