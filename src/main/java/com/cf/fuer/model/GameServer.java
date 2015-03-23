package com.cf.fuer.model;

import java.util.Date;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.util.DateUtil;

/**
 * 游戏服务器
 * 
 * @author sunke
 * 
 */
public class GameServer {

	private Long id;

	/**
	 * 服务器代码
	 */
	private String code;

	/**
	 * 服务器名称
	 */
	private String name;

	/**
	 * 游戏服务器IP地址
	 */
	private String ip;

	/**
	 * 游戏服务器端口
	 */
	private int port;

	/**
	 * 容纳用户最大数量
	 */
	private int maxCount = 3000;
	/**
	 * 服务器当前用户数
	 */
	private int currentCount;

	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 服务器类型,详见CommonConstants.SERVER_TYPE
	 */
	private int type = CommonConstants.SERVER_TYPE.TESTING;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 是否是维护服务器
	 */
	public boolean isMaintain() {
		//不是测试服务器（防止测试服务器暴露）并且在维护时间时为维护服务器
		if(!isTest() && DateUtil.isMaintainTime()){
			return true;
		}
		return type == CommonConstants.SERVER_TYPE.MAINTAIN;
	}

	/**
	 * 是否是测试服务器
	 */
	public boolean isTest() {
		return type == CommonConstants.SERVER_TYPE.TESTING;
	}

	@Override
	public String toString() {
		return "[code:" + code + " name:" + name + " ip:" + ip + " port:" + port + " type:" + type + " maxCount:" + maxCount + "]";
	}

}
