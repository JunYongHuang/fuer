package com.cf.fuer.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.model.User;
import com.cf.fuer.util.TokenUtil;

/**
 * 为登陆用户生成Token,还可以记录登陆日志等操作
 * 
 * @author sunke
 * 
 */
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	/**
	 * 登陆成功后,记录登陆日志并将其放入session中,等到注销或失效时,在LogSessionListener中记录登陆日志.
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		TokenUtil.saveLoginUserToken(request);
		User user = AccessHelper.getLoginUser();
		if(user != null){
			setCookie(CommonConstants.COOKIE_USERNAME, user .getUsername(), request, response);
		}
		String targetUrl = determineTargetUrl(request, response);
		if(targetUrl.equals(getDefaultTargetUrl())){
			setDefaultTargetUrl("/afterLogin.php");
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	/**
	 * 登陆完成后设置cookie,如果已存在,修改cookie.
	 */
	private void setCookie(String cookieName, String cookieValue, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		Cookie c = null;
		if(cookies != null){
			for (int i = 0; i < cookies.length; i++) {
				c = cookies[i];
				if (c.getName().equalsIgnoreCase(cookieName)) {
					//c.setValue(cookieValue);
					if(CommonConstants.COOKIE_USERNAME.equals(cookieName) && !cookieValue.equalsIgnoreCase(c.getValue())){
						System.out.println(c.getValue() +"\t 用户2:" + cookieValue);
					}
					c.setMaxAge(0);
					response.addCookie(c); // 修改后，要更新到浏览器中
				}
			}
		}
		// 不存在时添加新的cookie
		Cookie c1 = new Cookie(cookieName, cookieValue);
		// 设定有效时间 以s为单位
		c1.setMaxAge(-1);// 浏览器关闭时才失效
		// 发送Cookie文件
		response.addCookie(c1);
	}

}
