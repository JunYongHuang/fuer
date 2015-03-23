package com.cf.fuer.model;

import java.util.Date;

/**
 * 推广用户信息
 * 
 * @author sunke
 * 
 */
public class SpreadUser {

	private Long id;

	private Long userId;

	/**
	 * 推广人名称
	 */
	private String name;

	/**
	 * 推广地址码
	 */
	private String addressCode;

	/**
	 * 点击次数
	 */
	private int clickCount;

	/**
	 * 加入推广日期
	 */
	private Date date;

	/**
	 * 推广用户数
	 */
	private int userCount;

	/**
	 * 推广用户充值金额
	 */
	private long chargeAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public long getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(long chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
}
