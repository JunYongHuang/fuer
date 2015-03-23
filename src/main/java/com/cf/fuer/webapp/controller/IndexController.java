package com.cf.fuer.webapp.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.dao.ISpreadDao;
import com.cf.fuer.manager.IArticleManager;
import com.cf.fuer.model.GameRole;
import com.cf.fuer.model.GameServer;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.GameServerUtils;

@Controller
public class IndexController {
	
	@Autowired
	private ISpreadDao spreadDao;

	@RequestMapping("/login.php")
	public String login() {
		return "login";
	}

	@RequestMapping("/index.php")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/videos.php")
	public String videos(HttpServletRequest request) {
		String videoId = ServletRequestUtils.getStringParameter(request, "vid", "");
		request.setAttribute("videoId", videoId);
		return "videos";
	}
	
	
	/**
	 * 第一次活动页
	 * @return
	 */
	@RequestMapping(value="/fzc{sourcefrom}.php")
	public String fzc(HttpServletRequest request,HttpServletResponse response,@PathVariable String sourcefrom) {
		request.setAttribute("sourcefrom", sourcefrom);
		setCookie("sourcefrom",sourcefrom,request,response);
		GameServerUtils.insertArrive(sourcefrom);
		return "fzc";
	}
	
	@RequestMapping(value="/info/{addressCode}.php")
	public String spread(HttpServletRequest request,HttpServletResponse response,@PathVariable String addressCode) {
		String code = "-"+addressCode;
		setCookie("sourcefrom",code,request,response);
		spreadDao.addSpreadClick(addressCode);
		return "redirect:/index.php";
	}

	@RequestMapping("/dx.php")
	public String dxIndex(HttpServletRequest request,HttpServletResponse response) {
		//String from = CommonConstants.USER_FROM.SHAN_WEI;
//		request.setAttribute("dx", from);
		String referer = request.getHeader("referer");
		if(referer != null && !referer.isEmpty()){
			setCookie("referer",referer,request,response) ;
		}
//		setCookie("from",from,request,response) ;
		return "index";
	}
	/**
	 * 新浪广告
	 */
	@RequestMapping("/sr.php")
	public String sinaReg(HttpServletRequest request,HttpServletResponse response) {
		//String from = CommonConstants.USER_FROM.SINA;
		//setCookie("from",from,request,response) ;		
		String referer = request.getHeader("referer");
		if(referer != null && !referer.isEmpty()){
			setCookie("referer",referer,request,response) ;
		}
		return "sinaReg";
	}
	
	/**
	 * 登陆完成后的操作,设置cookie,并跳转到服务器列表页面
	 */
	@RequestMapping("/afterLogin.php")
	public String afterLogin(HttpServletRequest request,HttpServletResponse response) {
		User user = AccessHelper.getLoginUser();
		if(user != null){
			setCookie(CommonConstants.COOKIE_USERNAME, user.getUsername(), request, response);
			List<GameRole> userRoles = GameServerUtils.getUserRoles(user.getUsername());
			if(userRoles.isEmpty()){//未注册过游戏的直接跳转到新1服
				Map<String, GameServer> allServerMap = GameServerUtils.allGameServerMap();
				for (GameServer gameServer : allServerMap.values()) {
					Date openTime = gameServer.getCreateDate();
					if(!gameServer.isMaintain() &&!gameServer.isTest() && openTime.before(new Date())){
						return "redirect:/games/fm.php?c="+gameServer.getCode();
					}
				}
			}
		}
		return "redirect:/allServers.php";
	}
	
	/**
	 * 设置cookie,如果已存在,修改cookie.
	 */
	private void setCookie(String cookieName, String cookieValue, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookies[] = request.getCookies();
		Cookie c = null;
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
		// 不存在时添加新的cookie
		Cookie c1 = new Cookie(cookieName, cookieValue);
		c1.setPath("/");
		// 设定有效时间 以s为单位
		c1.setMaxAge(-1);// 浏览器关闭时才失效
		// 发送Cookie文件
		response.addCookie(c1);
	}

	@RequestMapping("/logout.php")
	public String logout() {
		return "logout";
	}

	@RequestMapping("/links.php")
	public String links() {
		return "links";
	}

	@RequestMapping("/allServers.php")
	public String allServers() {
		return "games/allServers";
	}

	@RequestMapping("/accessDenied.php")
	public String accessDenied() {
		return "accessDenied";
	}

	@RequestMapping("/objectNotfound.php")
	public String objectNotfound() {
		return "objectNotfound";
	}

	@Autowired
	private IArticleManager articleManager;
	
	@RequestMapping("/handbook/index.php")
	public String handbook(HttpServletRequest request) {
		Long indexId = ServletRequestUtils.getLongParameter(request, "i", -1L);
		request.setAttribute("i", indexId);
		articleManager.updateClick(indexId);
		return "manual";
	}

	/**
	 * 关于我们
	 * 
	 * @return
	 */
	@RequestMapping("/aboutUs.php")
	public String aboutUs() {
		return "aboutUs";
	}

	/**
	 * 保持session
	 * 
	 * @return
	 */
	@RequestMapping("/games/blank.php")
	public String blank() {
		return "games/blank";
	}

}
