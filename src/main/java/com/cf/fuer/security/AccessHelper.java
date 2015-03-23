package com.cf.fuer.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.cf.fuer.model.User;

public class AccessHelper {

	protected final static Log log = LogFactory.getLog(AccessHelper.class);
	
	/**
	 * 获取登陆的账号信息,如果没登陆返回null.
	 */
	public static User getLoginUser() {
		try {
			if (SecurityContextHolder.getContext().getAuthentication() == null)
				return null;

		} catch (NullPointerException npe) {
			log.error("Login user is null.");
		}
		Object object = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (object instanceof User) {
			return (User) object;
		}
		return null;
	}
	
	/**
	 * 注册完成后登陆系统
	 */
	public static void login(User user) {
		UserDetails userDetails = user;
		RunAsUserToken token = new RunAsUserToken(String.valueOf(user.getName()), userDetails, null, userDetails.getAuthorities(), null);
		token.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(token);
	}
}
