package com.cf.fuer.dao;

import java.util.List;

import com.cf.fuer.model.GameRole;
import com.cf.fuer.model.GameServer;
import com.cf.fuer.webapp.bean.SearchBean;

public interface IGameServerDao {

	Long createGameServer(GameServer gameServer);

	GameServer get(Long id);

	Long updateGameServer(GameServer gameServer);

	void search(SearchBean searchBean);

	/**
	 * 获取所有服务器列表,按编号排序
	 */
	List<GameServer> allGameServers();

	/**
	 * 根据编号获取服务器信息
	 */
	GameServer getByCode(String code);

	/**
	 * 获取压力最小(当前人数与最大人数比值最小)的服务器,用于用户未创建过角色时推荐服务器
	 */
	String getMinLoadServerCode();

	/**
	 * 获取用户登陆过的游戏服务器代码列表
	 */
	List<String> getAllUserServers(String username);

	/**
	 * 服务器是否满员
	 * 
	 * @param serverCode
	 *            要验证的服务器代码
	 * @return true 未满员, false 满员
	 */
	boolean isNotFull(String serverCode);

	/**
	 * 更新用户最近登陆的游戏
	 * 
	 * @param username
	 *            用户名
	 * @param serverCode
	 *            服务器编码
	 */
	void updateUserServer(String username, String serverCode);

	/**
	 * 新增一个角色注册,更新用户最近登陆服务器列表,同时更新注册人数
	 * 
	 * @param username
	 *            用户名
	 * @param serverCode
	 *            游戏服务器编号
	 * @param registerCount
	 *            当前游戏服务器注册的人数
	 */
	void insertUserServer(String username, String serverCode, Integer managerId, String managerName, Integer registerCount);

	/**
	 * 用户是否已注册该服务器
	 * 
	 * @param serverCode
	 *            要验证的服务器
	 * @param username
	 *            用户名
	 * @return true 用户已注册过此服务器
	 */
	boolean isRegInServer(String serverCode, String username);

	/**
	 * 获取用户所有角色
	 * 
	 * @param username
	 *            用户名
	 * @return 用户角色
	 */
	List<GameRole> getAllUserRoles(String username);

	/**
	 * 获取用户在服务器上的经理名称
	 * 
	 * @param username
	 *            用户名
	 * @param serverCode
	 *            服务器编码
	 * @return 用户经理名
	 */
	String getManagerName(String username, String serverCode);
	
	/**
	 * 新增一条到达人数
	 * 
	 * @param username
	 *            用户名
	 * @param serverCode
	 *            服务器编码
	 */
	void insertArrive(String username, String serverCode);

}
