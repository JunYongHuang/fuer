package com.cf.fuer.webapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.bank.ChargeNumberUtils;
import com.cf.fuer.dao.IChargeDao;
import com.cf.fuer.dao.IPlatformDao;
import com.cf.fuer.gc.ConnectUtil;
import com.cf.fuer.gc.GCResult;
import com.cf.fuer.gc.Message;
import com.cf.fuer.gc.MessageConstants;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.Charge;
import com.cf.fuer.model.Platform;
import com.cf.fuer.util.DateUtil;
import com.cf.fuer.util.GameServerUtils;
import com.cf.fuer.util.TokenUtil;
import com.cf.fuer.webapp.bean.PayResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.renren.api.client.utils.Md5Utils;

/**
 * 新浪充值接口
 * 
 * @author sunke
 * 
 */
@Controller
public class ChargeApiController {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserManager userManager;

	@Autowired
	private IChargeDao chargeDao;

	@Autowired
	private IPlatformDao platformDao;

	/**
	 * 充值
	 * 
	 * <pre>
	 * 0	查询成功
	 * 200 SPID信息不完整或信息有误
	 * 201 ServerID信息不完整或信息有误
	 * 202 UserID信息不完整或信息有误
	 * 203 RoleID信息不完整或信息有误
	 * 204 OrderID信息不完整或信息有误
	 * 205 GamePoints信息不完整或信息有误
	 * 206 Sign信息不完整或信息有误
	 * 210 签名错误
	 * 211 IP不在允许的列表里面
	 * 212 游戏账户不存在
	 * 213 游戏角色未创建或者游戏角色不存在
	 * 214 用户帐户游戏点数过多，不能再进行充值
	 * 215 给用户帐户充值失败。
	 * 216 订单号重复，充值失败
	 * 217 服务器维护，暂停充值
	 * 218 服务器ID编号不在新浪服务组
	 * 219 GamePoint和PayPoint比例不正确
	 * 220 PayPoint信息不完整或信息有误
	 * </pre>
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/games/pay_togame.php")
	public void pay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long from = ServletRequestUtils.getLongParameter(request, "From", 0L);

		Long ServerID = ServletRequestUtils.getLongParameter(request, "ServerID", -1L);
		String UserID = ServletRequestUtils.getStringParameter(request, "UserID", "");
		String OrderID = ServletRequestUtils.getStringParameter(request, "OrderID", "");
		int GamePoint = ServletRequestUtils.getIntParameter(request, "GamePoint", -1);
		String requestSign = ServletRequestUtils.getStringParameter(request, "Sign", "");
		int PayPoint = ServletRequestUtils.getIntParameter(request, "PayPoint", -1);
		String UserIP = ServletRequestUtils.getStringParameter(request, "UserIP", "");

		PayResult payResult = new PayResult();
		payResult.setGameOrderID(OrderID);
		payResult.setServerID(ServerID.intValue());
		payResult.setUserID(UserID);
		
		if (ServerID == -1L) {// 201 ServerID信息不完整或信息有误
			writeResult(response, 201, "ServerID信息不完整或信息有误", payResult);
			return;
		}
		if (UserID.isEmpty()) {// 202 UserID信息不完整或信息有误
			writeResult(response, 202, "UserID信息不完整或信息有误", payResult);
			return;
		}
		if (OrderID.isEmpty()) {// 204 OrderID信息不完整或信息有误
			writeResult(response, 204, "ServerID信息不完整或信息有误", payResult);
			return;
		}
		if (GamePoint == -1) {// 205 ServerID信息不完整或信息有误
			writeResult(response, 205, "GamePoint信息不完整或信息有误", payResult);
			return;
		}
		if (requestSign.isEmpty()) {// 206 Sign信息不完整或信息有误
			writeResult(response, 206, "Sign信息不完整或信息有误", payResult);
			return;
		}

		if (from == 0) {
			writeResult(response, 207, "From信息不完整或信息有误", payResult);
			return;
		}

		if (PayPoint == -1) {// 220 PayPoint信息不完整或信息有误
			writeResult(response, 220, "PayPoint信息不完整或信息有误", payResult);
			return;
		}
		// 219 GamePoint和PayPoint比例不正确

		// 211 IP不在允许的列表里面
		Platform platform = platformDao.get(from);
		String key = platform.getChargeKey();
		String params = "From=" + from + "&ServerID=" + ServerID + "&UserID=" + UserID + "&UserIP=" + UserIP + "&OrderID=" + OrderID + "&GamePoint=" + GamePoint + "&PayPoint=" + PayPoint + "&key=" + key;
		String sign = Md5Utils.md5(params);
		if (!requestSign.equals(sign)) {// 210 签名错误
			writeResult(response, 210, "签名错误", payResult);
			return;
		}
		// 211 IP不在允许的列表里面
		String requestIp = TokenUtil.getRequestIp(request);
		if (!platform.getWhiteIP().contains(requestIp)) {
			writeResult(response, 211, "IP不在允许的列表里面", payResult);
			return;
		}


		//from与uid拼接,防止重复(虎扑除外)
		if(from != 101){
			UserID =  from + "_" + UserID;
		}
		try {
			userManager.loadUserByUsername(UserID);
		} catch (Exception e) {// 212 游戏账户不存在
			writeResult(response, 212, "游戏账户不存在", payResult);
			return;
		}
		String serverCode = GameServerUtils.getServerCode(ServerID);
		if (serverCode.isEmpty()) {
			writeResult(response, 218, "服务器ID编号不在服务组", payResult);
			return;
		}

		if (!GameServerUtils.isRegInServer(serverCode, UserID)) {// 213
			writeResult(response, 213, "游戏角色未创建或者游戏角色不存在", payResult);
			return;
		}

		if (DateUtil.isMaintainTime()) {// 217 服务器维护，暂停充值
			writeResult(response, 217, "服务器维护，暂停充值", payResult);
			return;
		}

		Charge charge = chargeDao.getByDealId(OrderID);
		if (charge != null) {// 216 订单号重复，充值失败
			writeResult(response, 216, "订单号重复，充值失败 ", payResult);
			return;
		}

		charge = new Charge();
		Long chargeNo = ChargeNumberUtils.generateChargeNumber();
		charge.setOrderId(chargeNo);
		charge.setDealId(OrderID);
		int rmb = PayPoint * 100;
		charge.setChargeAmount(rmb);
		charge.setPayAmount(rmb);
		charge.setUser(UserID);
		String managerName = GameServerUtils.getManagerName(UserID, serverCode);
		charge.setManagerName(managerName);
		charge.setGameServer(serverCode);
		Date now = new Date();
		charge.setCreateDate(now);
		charge.setPayDate(now);
		charge.setChargeDate(now);
		charge.setPayUser(UserID);
		charge.setGoldMoney(GamePoint);
		charge.setUserIP(UserIP);

		Long presentGold = ChargeNumberUtils.getPresntGold(serverCode, PayPoint * 100);
		if (presentGold > 0) {
			charge.setPresentGold(presentGold);
			charge.setPresentName("活动返现");
		}

		boolean success = false;
		if (ConnectUtil.canSend(serverCode)) {// 目标地址可以连接,发送登陆消息
			String chargeMsg = Message.newChargeRequest(charge);
			ConnectUtil.sendMsg(chargeMsg);
			Message resultMessage = GCResult.getInstance().getResult(chargeNo.toString());
			// 发送之后等待结果
			long waitTime = 0;
			while (resultMessage == null) {
				try {
					Thread.sleep(100);// 睡眠等待结果
					resultMessage = GCResult.getInstance().getResult(chargeNo.toString());
				} catch (InterruptedException e) {
					//
				}
				waitTime = waitTime + 100;
				if (waitTime >= MessageConstants.TIMEOUT) {//
					break;
				}
			}
			if (resultMessage == null) {// 等待超时
				log.info(" MSG NULL");
			} else {
				if (resultMessage.isOkResult()) {// 正常
					success = true;
				}
				// 清除登陆结果
				GCResult.getInstance().removeResult(OrderID);
			}
		}
		if (success) {
			chargeDao.createPlatformCharge(charge);
			writeResult(response, 0, "", payResult);// 充值成功
			return;
		} else {
			// 215 给用户帐户充值失败。
			writeResult(response, 215, "给用户帐户充值失败", payResult);
			return;
		}
	}

	private void writeResult(HttpServletResponse response, int code, String errorMsg, PayResult payResult) {
		payResult.setCode(code);
		payResult.setErrorMsg(errorMsg);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String errorResult = gson.toJson(payResult);
		writeResult(response, errorResult);
	}

	private void writeResult(HttpServletResponse response, String jsonMsg) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print(jsonMsg);
			writer.flush();
		} catch (IOException e) {

		}
	}
}
