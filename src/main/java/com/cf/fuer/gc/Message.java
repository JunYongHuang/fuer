package com.cf.fuer.gc;

import com.cf.fuer.model.Charge;
import com.cf.fuer.model.User;
import com.cf.fuer.util.XmlUtil;
import com.cf.fuer.webapp.bean.SpreadBean;

public class Message {

	/**
	 * 原始消息
	 */
	protected String messageStr;

	public Message(String messageStr) {
		this.messageStr = messageStr;
	}

	/**
	 * 是否是请求
	 */
	public boolean isReqest() {
		return messageStr.contains("<request>");
	}

	/**
	 * 是否是响应
	 */
	public boolean isResponse() {
		return messageStr.contains("<result>");
	}

	/**
	 * 是否是登陆请求的相关的消息
	 */
	public boolean isLoginMsg() {
		return getType() == MessageConstants.TYPE.LOGIN;
	}

	/**
	 * 是否是验证用户角色请求的相关的消息
	 */
	public boolean isCheckRoleMsg() {
		return getType() == MessageConstants.TYPE.CHECK_ROLE;
	}

	/**
	 * 是否是充值请求的相关消息
	 */
	public boolean isChargeMsg() {
		return getType() == MessageConstants.TYPE.CHARGE;
	}

	/**
	 * 是否是登陆反馈相关消息
	 */
	public boolean isLoginFeedbackMsg() {
		return getType() == MessageConstants.TYPE.LOGIN_FEEDBACK;
	}

	/**
	 * 是否是服务器上线通知
	 */
	public boolean isOnlineMsg() {
		return getType() == MessageConstants.TYPE.ONLINE;
	}

	/**
	 * 是否是正确的返回
	 */
	public boolean isOkResult() {
		return getCode() == MessageConstants.RESULT_STATUS.OK;
	}

	/**
	 * 是否是404响应
	 */
	public boolean is404Result() {
		return getCode() == MessageConstants.RESULT_STATUS.NOT_FOUND;
	}

	/**
	 * 创建成功响应
	 * 
	 * @return
	 */
	public String okResponse() {
		StringBuilder result = new StringBuilder();
		result.append(XmlUtil.createNode("requestId", getRequestId()));
		result.append(XmlUtil.createNode("from", MessageConstants.SERVER_CODE));
		result.append(XmlUtil.createNode("to", getFrom()));
		result.append(XmlUtil.createNode("type", getType()));
		result.append(XmlUtil.createNode("code", MessageConstants.RESULT_STATUS.OK));
		result.append(XmlUtil.createNode("message", "OK"));
		result.append(XmlUtil.createNode("content", ""));
		return XmlUtil.createNode("result", result).toString();
	}

	/**
	 * 创建新的注册消息,服务器启动时发送.
	 */
	public static String newRegRequest() {
		StringBuilder result = new StringBuilder();
		result.append(XmlUtil.createNode("requestId", "001"));
		result.append(XmlUtil.createNode("from", MessageConstants.SERVER_CODE));
		result.append(XmlUtil.createNode("to", MessageConstants.CENTER_SERVER_CODE));
		result.append(XmlUtil.createNode("type", MessageConstants.TYPE.REGISTER));
		result.append(XmlUtil.createNode("content", ""));
		return XmlUtil.createNode("request", result).toString();
	}

	/**
	 * 向游戏服务器发送登陆请求
	 * 
	 * @param targetServer
	 *            游戏服务器编码
	 * @param loginUser
	 *            登陆用户
	 * @param userToken
	 *            用户token
	 * @return 请求字符串
	 */
	public static String newLoginRequest(String targetServer, User loginUser, String userToken) {
		StringBuilder result = new StringBuilder();
		result.append(XmlUtil.createNode("requestId", userToken));
		result.append(XmlUtil.createNode("from", MessageConstants.SERVER_CODE));
		result.append(XmlUtil.createNode("to", targetServer));
		result.append(XmlUtil.createNode("type", MessageConstants.TYPE.LOGIN));
		StringBuilder content = new StringBuilder();
		content.append(XmlUtil.createNode("username", loginUser.getUsername()));
		content.append(XmlUtil.createNode("token", userToken));
		content.append(XmlUtil.createNode("age", loginUser.getAge()));
		result.append(XmlUtil.createNode("content", content));
		return XmlUtil.createNode("request", result).toString();
	}

	/**
	 * 向游戏服务器发送验证用户是否创建过角色的请求
	 * 
	 * @param targetServer
	 *            游戏服务器编码
	 * @param username
	 *            要验证的用户名
	 * @param requestId
	 *            请求Id
	 * @return 请求字符串
	 */
	public static String newCheckRoleRequest(String targetServer, String username, String requestId) {
		StringBuilder result = new StringBuilder();
		result.append(XmlUtil.createNode("requestId", requestId));
		result.append(XmlUtil.createNode("from", MessageConstants.SERVER_CODE));
		result.append(XmlUtil.createNode("to", targetServer));
		result.append(XmlUtil.createNode("type", MessageConstants.TYPE.CHECK_ROLE));
		result.append(XmlUtil.createNode("content", XmlUtil.createNode("username", username)));
		return XmlUtil.createNode("request", result).toString();
	}

	/**
	 * 充值到游戏请求
	 * 
	 * @param targetServer
	 *            游戏服务器编号
	 * @param charge
	 *            订单
	 * @return 充值请求字符串
	 */
	public static String newChargeRequest(Charge charge) {
		StringBuilder result = new StringBuilder();
		result.append(XmlUtil.createNode("requestId", charge.getOrderId()));
		result.append(XmlUtil.createNode("from", MessageConstants.SERVER_CODE));
		result.append(XmlUtil.createNode("to", charge.getGameServer()));
		result.append(XmlUtil.createNode("type", MessageConstants.TYPE.CHARGE));
		StringBuilder content = new StringBuilder();
		content.append(XmlUtil.createNode("username", charge.getUser()));
		content.append(XmlUtil.createNode("rmbMoney", charge.getPayAmount() / 100));
		content.append(XmlUtil.createNode("goldMoney", charge.getGoldMoney()));
		content.append(XmlUtil.createNode("presentGold", charge.getPresentGold()));
		content.append(XmlUtil.createNode("presentName", charge.getPresentName()));
		result.append(XmlUtil.createNode("content", content));
		return XmlUtil.createNode("request", result).toString();
	}

	/**
	 * 点一点推广活动请求
	 * 
	 * @param spreadBean
	 * @return
	 */
	public static String newSpreadRequest(SpreadBean spreadBean) {
		StringBuilder result = new StringBuilder();
		result.append(XmlUtil.createNode("requestId", System.currentTimeMillis()));
		result.append(XmlUtil.createNode("from", MessageConstants.SERVER_CODE));
		result.append(XmlUtil.createNode("to", spreadBean.getServerCode()));
		result.append(XmlUtil.createNode("type", MessageConstants.TYPE.PRESENT));
		// 奖励详情
		StringBuilder itemDetail = new StringBuilder();
		itemDetail.append(XmlUtil.createNode("objectType", MessageConstants.PRESENT_TYPE.MONEY));
		itemDetail.append(XmlUtil.createNode("objectId", 0));
		itemDetail.append(XmlUtil.createNode("objectNum", spreadBean.getCount() * 10000));

		StringBuilder content = new StringBuilder();
		content.append(XmlUtil.createNode("roleId", spreadBean.getManagerId()));
		content.append(XmlUtil.createNode("activityType", MessageConstants.ACTIVITY_TYPE.SPREAD));
		content.append(XmlUtil.createNode("msg", ""));
		content.append(XmlUtil.createNode("items", XmlUtil.createNode("item", itemDetail)));
		result.append(XmlUtil.createNode("content", content));
		return XmlUtil.createNode("request", result).toString();
	}

	/**
	 * 接收消息的时间,毫秒
	 */
	private Long receiveTime;

	/**
	 * 请求序号
	 */
	private String requestId;
	/**
	 * 发起请求的服务器编号
	 */
	private String from;
	/**
	 * 目标服务器编号
	 */
	private String to;
	/**
	 * 操作类型码
	 */
	private int type;
	/**
	 * 操作内容
	 */
	private String content;

	/**
	 * 响应码
	 */
	private int code;
	/**
	 * 响应消息
	 */
	private String message;

	/**
	 * 登陆反馈时的用户名
	 */
	private String username;
	/**
	 * 登陆反馈时是否创建了新的角色.
	 */
	private Boolean createRole;
	/**
	 * 登陆反馈时,游戏服务器返回当前服务器的注册人数
	 */
	private Integer registedCount;

	/**
	 * 登陆反馈时,用户创建的角色名称
	 */
	private String managerName;

	/**
	 * 登陆反馈时,用户创建的角色ID
	 */
	private Integer managerId;

	/**
	 * 上线通知时的上线服务器编号
	 */
	private String online;

	/**
	 * 404时获取离线服务器编号
	 */
	private String offline;

	public String getRequestId() {
		if (requestId == null) {
			requestId = XmlUtil.getStrNodeBody("requestId", messageStr);
		}
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getFrom() {
		if (from == null) {
			from = XmlUtil.getStrNodeBody("from", messageStr);
		}
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		if (to == null) {
			to = XmlUtil.getStrNodeBody("to", messageStr);
		}
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getContent() {
		if (content == null) {
			content = XmlUtil.getStrNodeBody("content", messageStr);
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		if (type == 0) {
			type = XmlUtil.getIntNodeBody("type", messageStr);
		}
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCode() {
		if (code == 0) {
			code = XmlUtil.getIntNodeBody("code", messageStr);
		}
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		if (message == null) {
			message = XmlUtil.getStrNodeBody("message", messageStr);
		}
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setReceiveTime(Long receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Long getReceiveTime() {
		return receiveTime;
	}

	public String getUsername() {
		if (username == null) {
			username = XmlUtil.getStrNodeBody("username", getContent());
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isCreateRole() {
		if (createRole == null) {
			String create = XmlUtil.getStrNodeBody("createRole", getContent());
			createRole = Boolean.valueOf(create);
		}
		return createRole;
	}

	public void setCreateRole(boolean createRole) {
		this.createRole = createRole;
	}

	public String getOffline() {
		if (offline == null) {
			offline = XmlUtil.getStrNodeBody("offline", getContent());
		}
		return offline;
	}

	public void setOffline(String offline) {
		this.offline = offline;
	}

	public String getOnline() {
		if (online == null) {
			online = XmlUtil.getStrNodeBody("online", getContent());
		}
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public Integer getRegistedCount() {
		if (registedCount == null) {
			registedCount = XmlUtil.getIntNodeBody("registedCount", getContent());
		}
		return registedCount;
	}

	public void setRegistedCount(Integer registedCount) {
		this.registedCount = registedCount;
	}

	public String getManagerName() {
		if (managerName == null) {
			managerName = XmlUtil.getStrNodeBody("managerName", getContent());
		}
		return managerName;
	}

	public Integer getManagerId() {
		if (managerId == null) {
			managerId = XmlUtil.getIntNodeBody("managerId", getContent());
		}
		return managerId;
	}

}
