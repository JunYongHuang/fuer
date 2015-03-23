package com.cf.fuer.webapp.bean;

import com.google.gson.annotations.Expose;

/**
 * 充值结果
 * 
 * @author sunke
 * 
 */
public class PayResult {
	/**
	 * 返回结果代码，成功为0，失败参照错误代码表
	 */
	@Expose
	private int Code;

	/**
	 * 当Code不为0时，ErrorMsg返回错误信息，默认为空字符串
	 */
	@Expose
	private String ErrorMsg;
	/**
	 * 服务器ID，规则为1服为1，2服为2
	 */
	@Expose
	private int ServerID;
	/**
	 * 游戏账号ID：新浪与游戏厂商认证用户的唯一标识
	 */
	@Expose
	private String UserID = "";
	/**
	 * 游戏内的角色ID，向指定角色充值时返回角色ID，向指定帐号充值时返回帐号UID
	 */
	@Expose
	private String RoleID = "";
	/**
	 * 游戏厂商的订单号
	 */
	@Expose
	private String GameOrderID = "";

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}

	public int getServerID() {
		return ServerID;
	}

	public void setServerID(int serverID) {
		ServerID = serverID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getRoleID() {
		return RoleID;
	}

	public void setRoleID(String roleID) {
		RoleID = roleID;
	}

	public String getGameOrderID() {
		return GameOrderID;
	}

	public void setGameOrderID(String gameOrderID) {
		GameOrderID = gameOrderID;
	}
}