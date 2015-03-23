package com.cf.fuer.model;

/**
 * 第三方登陆用户信息
 * 
 * @author sunke
 * 
 */
public class ThirdUser {

	private Long id;

	/**
	 * 生成的用户名:类型+openID
	 */
	private String username;

	/**
	 * 登陆类型,详见CommonConstants.LOGIN_TYPE
	 */
	private Integer loginType;

	/**
	 * 第三方会员ID
	 */
	private String openId;

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

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
