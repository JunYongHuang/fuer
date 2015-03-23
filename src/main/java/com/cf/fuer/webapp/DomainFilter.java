package com.cf.fuer.webapp;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 执行域名301跳转的过滤器。
 * 
 * @author sunke
 * 
 */
public class DomainFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		// 获取域名
		String serverName = request.getServerName();
		// 获取请求路径
		String path = httpServletRequest.getRequestURI();
		String queryString = (httpServletRequest.getQueryString() == null ? "" : "?" + httpServletRequest.getQueryString()); // 获取路径中的参数
		if (otherDomain(serverName)) { // 除www.yyfuer.com外其它的域名301跳转到goalgoal.cn
			httpServletResponse.setStatus(301);
			httpServletResponse.setHeader("Location", "http://www.yyfuer.com" + path + queryString);
			httpServletResponse.setHeader("Connection", "close");
			return;
		}
		chain.doFilter(request, response);
	}

	private boolean otherDomain(String serverName) {
		if (serverName.contains("goalgoal.cn") && !serverName.contains("www")) { // 不是www.yyfuer.com
			return true;
		}
		if (serverName.contains("goalgoal.net")) {
			return true;
		}
		if (serverName.contains("goalgoal.com.cn")) {
			return true;
		}
		return false;
	}

	@Override
	public void destroy() {

	}

}
