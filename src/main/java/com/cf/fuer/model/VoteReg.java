package com.cf.fuer.model;

import java.util.Date;

/**
 * 投票参与列表
 * 
 * @author sunke
 * 
 */
public class VoteReg {

	private Long id;

	private String uname;

	private String email;

	private String mobile;

	private String addr;

	private Long regdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Long getRegdate() {
		return regdate;
	}

	public void setRegdate(Long regdate) {
		this.regdate = regdate;
	}
	
	public Date getVoteDate() {
		return new Date(regdate * 1000);
	}
}
