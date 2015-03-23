package com.cf.fuer.model;

import java.util.Date;

import com.cf.fuer.util.DateUtil;
import com.cf.fuer.util.GameServerUtils;

/**
 * 游戏服务器用户角色
 * 
 * @author sunke
 * 
 */
public class GameRole {

	private Long id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 服务器代码
	 */
	private String serverCode;

	/**
	 * 经理名称
	 */
	private String managerName;

	/**
	 * 角色创建日期
	 */
	private Date creationDate;

	/**
	 * 上次登陆日期
	 */
	private Date loginDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	public String getLoginDateStr(){
		return DateUtil.toDateString(loginDate);
	}
	
	public String getCreationDateStr(){
		return DateUtil.toDateString(creationDate);
	}
	
	public String getServerName(){
		return GameServerUtils.getServerName(serverCode);
	}

}
