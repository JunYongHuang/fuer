package com.cf.fuer.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cf.fuer.model.User;

/**
 * 此类主要用于在Session销毁时清除用户Token.
 * 
 * @author sunke
 * 
 */
public class LoginLogSessionListener implements HttpSessionListener {
	
	Log log = LogFactory.getLog(getClass());
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}

	/**
	 * 在Session销毁时(注销或session过期)记录日志.
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		User loginUser = AccessHelper.getLoginUser();
		if (loginUser != null) {
			String username = loginUser.getUsername();
			log.info("用户" + username + "登出");
		}
	}

}
