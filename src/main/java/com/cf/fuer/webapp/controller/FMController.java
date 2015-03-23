package com.cf.fuer.webapp.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.dao.IPlatformDao;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.gc.ConnectUtil;
import com.cf.fuer.gc.GCResult;
import com.cf.fuer.gc.Message;
import com.cf.fuer.gc.MessageConstants;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.GameServer;
import com.cf.fuer.model.Platform;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.ControllerUtil;
import com.cf.fuer.util.GameServerUtils;
import com.cf.fuer.util.TokenUtil;
import com.renren.api.client.utils.Md5Utils;

@Controller
public class FMController {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	private IPlatformDao platformDao;

	/**
	 * 参数加密字符串前面随机字母的长度
	 */
	private static final int authPrefixLength = 3;
	/**
	 * 参数加密字符串后面随机字母的长度
	 */
	private static final int authSuffixLength = 5;

	/**
	 * URL过期时间3分钟
	 */
	private static final long timeout = 3 * 60;

	@Autowired
	private IUserManager userManager;

	@RequestMapping("/games/fm.php")
	public String showFm(HttpServletRequest request) {
		long from = ServletRequestUtils.getLongParameter(request, "from", 0L);
		User loginUser = AccessHelper.getLoginUser();
		String requestIp = TokenUtil.getRequestIp(request);
		if (from == 0) {// 官网链接
			if(loginUser == null){
				return "redirect:/login.php";
			}
			String username = loginUser.getUsername();
//			String email = loginUser.getEmail();
//			if (email == null || email.isEmpty()) {
//				return "redirect:/account/edit.php";
//			}
			// 获取登陆用户的cookie(登陆完成时设置)
			String cookieUser = getCookie(request, "LOGINUSER");
			log.debug("Login user:" + username + "  Cookie User:" + cookieUser);
			if (cookieUser == null) {// Cookie不存在时验证IP
				// 请求IP与登陆的IP不一致时，强制重新登陆
				if (!loginUser.getLoginIp().equals(requestIp)) {
					log.info("用户[" + username + "] IP 不一致  登陆IP：" + loginUser.getLoginIp() + "请求IP：" + requestIp);
					return "redirect:/login.php";
				}
			} else if (!username.equals(cookieUser)) {// Cookie不相同时强制重新登陆
				log.info("用户[" + username + "] Cookie 与登陆用户不一致,Cookie User:" + cookieUser);
				return "redirect:/login.php";
			}

			// 黑名单用户
			if (loginUser.isBlack()) {
				ControllerUtil.setErrorMessage(request, "你已被管理员限制进入游戏,请联系管理员解除限制!");
				return "redirect:/allServers.php";
			}

			String code = ServletRequestUtils.getStringParameter(request, "c", "");// 获取url中的服务器编号
			GameServer gameServer = GameServerUtils.allGameServerMap().get(code);
			ControllerUtil.throwExceptionIfNull(gameServer, "指定的服务器不存在!");
			if (loginUser.isCommon()) {// 普通用户,验证服务器是否可以进入
				if (gameServer.isMaintain()) {
					// 维护服务器不可进入
					ControllerUtil.setErrorMessage(request, "抱歉,此服务器正在维护,您可以先选择其它服务器进行游戏!");
					return "redirect:/allServers.php";
				}
				if (gameServer.isTest()) {
					ControllerUtil.throwExceptionIfNull(null, "服务器不可进入!");
				}

				if (!GameServerUtils.canEnterServer(code, username)) {
					// 服务器不可进入
					ControllerUtil.setErrorMessage(request, "服务器不可进入,请更换服务器后重试!");
					return "redirect:/allServers.php";
				}
			}

			String token = TokenUtil.generateTokenStr(loginUser.getId());
			// 正常流程处理
			boolean normalProcess = false;
			if (ConnectUtil.canSend(code)) {// 目标地址可以连接,发送登陆消息
				String loginRequestMsg = Message.newLoginRequest(code, loginUser, token);
				ConnectUtil.sendMsg(loginRequestMsg);
				Message resultMessage = GCResult.getInstance().getResult(token);
				// 发送之后等待结果
				long waitTime = 0;
				while (resultMessage == null) {
					try {
						Thread.sleep(100);// 睡眠等待结果
						resultMessage = GCResult.getInstance().getResult(token);
					} catch (InterruptedException e) {
						//
					}
					waitTime = waitTime + 100;
					if (waitTime >= MessageConstants.TIMEOUT) {//
						break;
					}
				}
				if (resultMessage == null) {// 等待超时
					log.info(" MSG NULL");
				} else {
					if (resultMessage.isOkResult()) {// 正常
						normalProcess = true;
					}
					// 清除登陆结果
					GCResult.getInstance().removeResult(token);
				}
			}
			if (!normalProcess) {
				token = token + "0";// 49位token
				TokenUtil.insertToken(username, loginUser.getAge(), token, code);
			}

			// auth里包含域名+端口+username+token+年龄
			String auth = "p1=" + gameServer.getIp() + "&p2=" + gameServer.getPort() + "&p3=" + username + "&p4=" + token;
			// base64加密默认一行超出76字符会包含换行符"/r/n"，所以加密时要设置到76字符不自动换行,否则传到游戏js中javascript会报错
			byte[] encodeByte = Base64.encodeBase64(auth.getBytes(), false);
			String e_auth = new String(encodeByte);
			String authResult = getCharAndNumr(authPrefixLength) + e_auth + getCharAndNumr(authSuffixLength);// 前后添加字母和数字的随机数
			request.setAttribute("auth", authResult);
			request.setAttribute("serverName", gameServer.getName());

			String domain = request.getServerName() + request.getContextPath();
			request.setAttribute("rechargeUrl", "http://"+domain + "/account/charge.php?c=" + gameServer.getCode());
			GameServerUtils.updateUserLastServers(username, code);
			if (normalProcess) {
				log.info("用户" + username + "[" + loginUser.getRole() + "]登陆游戏服务器" + code + " IP:" + requestIp + " token:" + token);
			} else {
				log.warn("备用方案: 用户" + username + "[" + loginUser.getRole() + "]登陆游戏服务器" + code + " IP:" + requestIp + " token:" + token);
			}
			return "games/fm.index";
		} else {// 第三方链接
			Platform platform = platformDao.get(from);
			if (platform == null) {
				request.setAttribute("msg", "来源错误!");
			}
			Long linkTime = ServletRequestUtils.getLongParameter(request, "time", 0L);
			String uid = ServletRequestUtils.getStringParameter(request, "uid", "");
			String requestSign = ServletRequestUtils.getStringParameter(request, "sign", "");

			// String clientId = Config.getClientId();
			Long serverId = ServletRequestUtils.getLongParameter(request, "s", 1L);

			boolean flushSession = true;
			/**
			 * 测试人员和管理员不刷新session
			 */
			if (uid.isEmpty() && loginUser != null) {
				if (loginUser.isTest() || loginUser.isAdmin()) {
					flushSession = false;
				}
			}

			if (flushSession) {
				String key = platform.getLoginKey();
				String params = "from=" + from+ "&time=" + linkTime + "&s=" + serverId + "&uid=" + uid + "&key=" + key;
				String sign = Md5Utils.md5(params);
				if (!requestSign.equals(sign)) {// 签名错误
					log.warn("签名错误:" + params);
					// 重定向到新浪平台登陆
					return "redirect:" + platform.getLoginUrl();
				}

				long now = System.currentTimeMillis() / 1000;
				if (now - linkTime > timeout) {
					log.warn("链接过期:" + params);
					// 重定向到新浪平台登陆
					return "redirect:" + platform.getLoginUrl();
				}

				if (uid.isEmpty()) {// 没有获取UID
					// 重定向到新浪平台登陆
					log.warn("无Uid参数:" + params);
					return "redirect:" + platform.getLoginUrl();
				}
				//from与uid拼接,防止重复(虎扑除外)
				if(from != 101){
					uid =  from + "_" + uid;
				}
				// 获取UID后查找是否已创建用户,如未创建则创建新的用户并登陆
				try {
					loginUser = userManager.loadUserByUsername(uid);
					AccessHelper.login(loginUser);
					TokenUtil.saveLoginUserToken(request);
				} catch (Exception e) {
					User user = new User();
					user.setFrom(from);
					user.setUsername(uid);
					user.setPassword("1");// 随便设置一个密码,防止登陆
					user.setRole(CommonConstants.ROLE.ROLE_USER);
					try {
						Long userId = userManager.add(user);
						loginUser = userManager.get(userId);
						AccessHelper.login(loginUser);
						TokenUtil.saveLoginUserToken(request);
						log.info("新用户[" + uid + "]");
					} catch (ValidationException e1) {
						e1.printStackTrace();
					}
				}
			}

			// 黑名单用户
			if (loginUser.isBlack()) {
				request.setAttribute("msg", "你已被管理员限制进入游戏,请联系管理员解除限制!");
			}

			// String serverIdStr = server.replace(clientId, "");
			// if(serverIdStr.isEmpty()){
			// serverIdStr = "0";
			// }
			// Long serverId = Long.valueOf(serverIdStr);
			String username = loginUser.getUsername();
			String code = GameServerUtils.getServerCode(serverId);// 获取url中的服务器编号
			GameServer gameServer = GameServerUtils.allGameServerMap().get(code);
			ControllerUtil.throwExceptionIfNull(gameServer, "指定的服务器不存在!");
			if (loginUser.isCommon()) {// 普通用户,验证服务器是否可以进入
				if (gameServer.isMaintain()) {
					// 维护服务器不可进入
					request.setAttribute("msg", "够足球提醒您:合理安排游戏时间,享受健康生活! 服务器每日例行维护时间04:00~07:00");
				}
				if (gameServer.isTest()) {
					ControllerUtil.throwExceptionIfNull(null, "服务器不可进入!");
				}

				if (!GameServerUtils.canEnterServer(code, username)) {
					// 服务器不可进入
					request.setAttribute("msg", "服务器不可进入,请更换服务器后重试!");
				}
			}

			String token = TokenUtil.generateTokenStr(loginUser.getId());
			// 正常流程处理
			boolean normalProcess = false;
			if (ConnectUtil.canSend(code)) {// 目标地址可以连接,发送登陆消息
				String loginRequestMsg = Message.newLoginRequest(code, loginUser, token);
				ConnectUtil.sendMsg(loginRequestMsg);
				Message resultMessage = GCResult.getInstance().getResult(token);
				// 发送之后等待结果
				long waitTime = 0;
				while (resultMessage == null) {
					try {
						Thread.sleep(100);// 睡眠等待结果
						resultMessage = GCResult.getInstance().getResult(token);
					} catch (InterruptedException e) {
						//
					}
					waitTime = waitTime + 100;
					if (waitTime >= MessageConstants.TIMEOUT) {//
						break;
					}
				}
				if (resultMessage == null) {// 等待超时
					log.info(" MSG NULL");
				} else {
					if (resultMessage.isOkResult()) {// 正常
						normalProcess = true;
					}
					// 清除登陆结果
					GCResult.getInstance().removeResult(token);
				}
			}
			if (!normalProcess) {
				token = token + "0";// 49位token
				TokenUtil.insertToken(username, loginUser.getAge(), token, code);
			}

			// auth里包含域名+端口+username+token+年龄
			String auth = "p1=" + gameServer.getIp() + "&p2=" + gameServer.getPort() + "&p3=" + username + "&p4=" + token;
			// base64加密默认一行超出76字符会包含换行符"/r/n"，所以加密时要设置到76字符不自动换行,否则传到游戏js中javascript会报错
			byte[] encodeByte = Base64.encodeBase64(auth.getBytes(), false);
			String e_auth = new String(encodeByte);
			String authResult = getCharAndNumr(authPrefixLength) + e_auth + getCharAndNumr(authSuffixLength);// 前后添加字母和数字的随机数
			request.setAttribute("auth", authResult);
			request.setAttribute("serverName", gameServer.getName());
			request.setAttribute("serverNo", serverId);
			request.setAttribute("serverVersion", 3);
			request.setAttribute("rechargeUrl", platform.getChargeurl());
			GameServerUtils.updateUserLastServers(username, code);
			if (normalProcess) {
				log.info("用户" + username + "[" + loginUser.getRole() + "]登陆游戏服务器" + code + " IP:" + requestIp + " token:" + token);
			} else {
				log.warn("备用方案: 用户" + username + "[" + loginUser.getRole() + "]登陆游戏服务器" + code + " IP:" + requestIp + " token:" + token);
			}
			return "games/fm.index";
		}

	}

	/**
	 * 从cookie中获取登陆用户名
	 * 
	 * @return
	 */
	private String getCookie(HttpServletRequest request, String cookieName) {
		Cookie cookies[] = request.getCookies();
		Cookie c1 = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				c1 = cookies[i];
				if (c1.getName().equalsIgnoreCase(cookieName)) {
					return c1.getValue();
				}
			}
		}
		return null;
	}

	// 根据长度获得随机字母或数字
	public String getCharAndNumr(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

}
