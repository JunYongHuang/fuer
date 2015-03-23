package com.cf.fuer.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cf.fuer.dao.IGameServerDao;
import com.cf.fuer.model.GameRole;
import com.cf.fuer.model.GameServer;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;

@Component
public class GameServerUtils {

	private static IGameServerDao gameServerDao;

	/**
	 * 获取服务器编码
	 */
	public static String getServerCode(Long serverId) {
		GameServer server = gameServerDao.get(serverId);
		if (server == null) {
			return "";
		} else {
			return server.getCode();
		}
	}

	/**
	 * 获取用户所有注册过的服务器列表,最后一个登陆的在最前面.
	 * 
	 * @return
	 */
	public static List<String> getUserRegServers() {
		// 保持顺序
		List<String> latestServers = new ArrayList<String>();
		User loginUser = AccessHelper.getLoginUser();
		if (loginUser != null) {
			latestServers = gameServerDao.getAllUserServers(loginUser.getUsername());
		}
		return latestServers;
	}

	/**
	 * 获取经理名
	 * 
	 * @param username
	 *            用户名
	 * @param serverCode
	 *            服务器编码
	 * @return 经理名
	 */
	public static String getManagerName(String username, String serverCode) {
		return gameServerDao.getManagerName(username, serverCode);
	}

	/**
	 * 获取服务器名称
	 */
	public static String getServerName(String serverCode) {
		GameServer server = gameServerDao.getByCode(serverCode);
		if (server != null) {
			return server.getName();
		}
		return "";
	}

	/**
	 * 获取用户角色列表
	 * 
	 * @param username
	 *            用户名
	 * @return 角色列表
	 */
	public static List<GameRole> getUserRoles(String username) {
		return gameServerDao.getAllUserRoles(username);
	}
	/**
	 * 到达注册页面统计
	 * @param sourceFrom
	 */
	public static void insertArrive(String sourceFrom){
		gameServerDao.insertArrive("", sourceFrom);
	}

	/**
	 * 用户是否可以进入服务器,已注册的用户可以进入,未注册的用户且服务器未满员也可以进入
	 * 
	 * @param serverCode
	 *            要进入的服务器代码
	 * @param username
	 *            用户名
	 * @return true 可以进入, false 不可以进入
	 */
	public static boolean canEnterServer(String serverCode, String username) {
		// 已注册可以进入
		if (GameServerUtils.isRegInServer(serverCode, username)) {
			return true;
		}

		// 未注册,且服务器未满员可以进入
		if (gameServerDao.isNotFull(serverCode)) {
			//此处用于增加到达数(即用户未在该服务器上注册过,且服务器并未满员)
			gameServerDao.insertArrive(username, serverCode);
			return true;
		}
		return false;
	}

	/**
	 * 用户是否已注册该服务器
	 * 
	 * @param serverCode
	 *            要验证的服务器
	 * @param username
	 *            用户名
	 * @return true 用户已注册过此服务器
	 */
	public static boolean isRegInServer(String serverCode, String username) {
		return gameServerDao.isRegInServer(serverCode, username);
	}

	/**
	 * 更新用户最近登陆的游戏列表.
	 * 
	 * @param username
	 *            用户名
	 * @param serverCode
	 *            服务器代码
	 */
	public static void updateUserLastServers(String username, String serverCode) {
		gameServerDao.updateUserServer(username, serverCode);
	}

	/**
	 * 
	 * 插入用户注册的游戏列表.
	 * 
	 * @param username
	 *            用户名
	 * @param serverCode
	 *            服务器代码
	 * @param managerName
	 *            游戏经理名
	 * 
	 * @param registerCount
	 *            服务器注册人数
	 */
	public static void insertRegServer(String username, String serverCode, Integer managerId, String managerName, Integer registerCount) {
		gameServerDao.insertUserServer(username, serverCode, managerId, managerName, registerCount);
	}

	/**
	 * 所有服务器信息,key为服务器编码
	 */
	public static Map<String, GameServer> allGameServerMap() {
		List<GameServer> gameServerList = gameServerDao.allGameServers();
		Map<String, GameServer> gameServerMap = new LinkedHashMap<String, GameServer>();
		for (GameServer gameServer : gameServerList) {
			gameServerMap.put(gameServer.getCode(), gameServer);
		}
		return gameServerMap;
	}

	@Autowired
	public void setGameServerDao(IGameServerDao gameServerDao) {
		GameServerUtils.gameServerDao = gameServerDao;
	}

}
