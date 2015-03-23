package com.cf.fuer.model;

/**
 * 平台信息，混服时使用
 * 
 * @author sunke
 * 
 */
public class Platform {

	private Long id;

	/**
	 * 平台名称
	 */
	private String name;

	/**
	 * 平台够足球登陆地址
	 */
	private String loginUrl;
	
	/**
	 * 平台够足球充值地址
	 */
	private String chargeurl;

	/**
	 * 登陆加密用的key
	 */
	private String loginKey;

	/**
	 * 充值加密用的key
	 */
	private String chargeKey;

	/**
	 * 充值IP白名单
	 */
	private String whiteIP;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public String getChargeKey() {
		return chargeKey;
	}

	public void setChargeKey(String chargeKey) {
		this.chargeKey = chargeKey;
	}

	public String getWhiteIP() {
		return whiteIP;
	}

	public void setWhiteIP(String whiteIP) {
		this.whiteIP = whiteIP;
	}

	public String getChargeurl() {
		return chargeurl;
	}

	public void setChargeurl(String chargeurl) {
		this.chargeurl = chargeurl;
	}
}
