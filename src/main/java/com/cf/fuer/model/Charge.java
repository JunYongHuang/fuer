package com.cf.fuer.model;

import java.util.Date;

/**
 * 充值记录
 * 
 * @author sunke
 * 
 */
public class Charge {

	private Long id;

	/**
	 * 充值编号
	 */
	private Long orderId;

	/**
	 * 快钱交易号，商户每一笔交易都会在快钱生成一个交易号
	 */
	private String dealId;

	/**
	 * 用户提交的充值金额 (以分为单位)
	 */
	private long chargeAmount;

	/**
	 * 实付金额,该金额代表快钱账户最终收到的金额( 以分为单位)
	 */
	private long payAmount;
	/**
	 * 充值对应的金币
	 */
	private long goldMoney;
	
	/**
	 * 充值对应的返还金币数量
	 */
	private long presentGold = 0;
	
	/**
	 * 活动名称
	 */
	private String presentName = "";
	
	/**
	 * 用户输入的其它金额,数据库不存
	 */
	private Long otherChargeAmount;
	/**
	 * 支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10,该值与提交时相同。
	 */
	private String payType;
	/**
	 * 银行代码，如果payType为00，该值为空；如果payType为10,该值与提交时相同。
	 */
	private String bankId;
	/**
	 * 银行交易号 ，快钱交易在银行支付时对应的交易号，如果不是通过银行卡支付，则为空
	 */
	private String bankDealId;
	
	/**
	 * 错误代码 ，请参照《人民币网关接口文档》最后部分的详细解释,正常情况下此字段为空
	 */
	private String errCode;

	/**
	 * 快钱交易时间，快钱对交易进行处理的时间,格式：yyyyMMddHHmmss，如：20071117020101
	 */
	private String dealTime;

	/**
	 * 被充值账号
	 */
	private String user;
	
	/**
	 * 确认被充值账号
	 */
	private String confirmUser;

	/**
	 * 充值经理名
	 */
	private String managerName;

	/**
	 * 充值对应的游戏服务器编号
	 */
	private String gameServer;

	/**
	 * 付款账号,即充值时的登陆账号
	 */
	private String payUser;

	/**
	 * 订单创建日期
	 */
	private Date createDate;
	/**
	 * 收到快钱返回结果的时间
	 */
	private Date payDate;

	/**
	 * 手续费,快钱收取的手续费，单位为分
	 */
	private int fee;

	/**
	 * 订单状态
	 */
	private int status;

	/**
	 * 完成游戏服务器充值时间
	 */
	private Date chargeDate;
	
	/**
	 * 充值IP
	 */
	private String UserIP;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public long getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(long chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(long payAmount) {
		this.payAmount = payAmount;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPayUser() {
		return payUser;
	}

	public void setPayUser(String payUser) {
		this.payUser = payUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getGameServer() {
		return gameServer;
	}

	public void setGameServer(String gameServer) {
		this.gameServer = gameServer;
	}

	public String getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankDealId() {
		return bankDealId;
	}

	public void setBankDealId(String bankDealId) {
		this.bankDealId = bankDealId;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public long getGoldMoney() {
		return goldMoney;
	}

	public void setGoldMoney(long goldMoney) {
		this.goldMoney = goldMoney;
	}

	public Long getOtherChargeAmount() {
		return otherChargeAmount;
	}

	public void setOtherChargeAmount(Long otherChargeAmount) {
		this.otherChargeAmount = otherChargeAmount;
	}

	public long getPresentGold() {
		return presentGold;
	}

	public void setPresentGold(long presentGold) {
		this.presentGold = presentGold;
	}

	public String getPresentName() {
		return presentName;
	}

	public void setPresentName(String presentName) {
		this.presentName = presentName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getUserIP() {
		return UserIP;
	}

	public void setUserIP(String userIP) {
		UserIP = userIP;
	}

}
