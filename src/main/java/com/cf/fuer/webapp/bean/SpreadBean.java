package com.cf.fuer.webapp.bean;

/**
 * 点一点活动bean.
 * 
 * @author sunke
 * 
 */
public class SpreadBean {

	private String serverCode;

	private String managerId;

	private Integer count = 0;

	public SpreadBean(String serverCode, String managerId) {
		super();
		this.serverCode = serverCode;
		this.managerId = managerId;
	}

	/**
	 * 点击次数+1
	 */
	public void addCount() {
		count = count + 1;
	}

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
