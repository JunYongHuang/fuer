package com.cf.fuer.webapp.controller.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.bank.ChargeTaskUtil;
import com.cf.fuer.bank.Pkipair;
import com.cf.fuer.manager.IChargeManager;
import com.cf.fuer.model.Charge;

@Controller
public class ChargeController {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private IChargeManager chargeManager;
	

	@Value("#{jdbcSettings['app.url']}")
	private String appUrl;

	@RequestMapping("/account/sendCharge.php")
	public String charge(HttpServletRequest request) {
		long orderId = ServletRequestUtils.getLongParameter(request, "orderId", -1L);
		Charge charge = chargeManager.getByOrderId(orderId);
		request.setAttribute("step", 2);
		request.setAttribute("order", charge);
		request.setAttribute("appUrl", appUrl);
		return "charge";
	}
	
	@RequestMapping("/admin/sendCharge.php")
	public String testCharge(HttpServletRequest request){
		long orderId = ServletRequestUtils.getLongParameter(request, "orderId", -1L);
		Charge charge = chargeManager.getByOrderId(orderId);
		charge.setStatus(CommonConstants.CHARGE_STATUS.PAY_SUCCESS);
		charge.setPayAmount(charge.getChargeAmount());
		ChargeTaskUtil.sendChargeMsg(charge);
		return "redirect:orderDetail.php?orderId="+orderId;
	}

	@RequestMapping("/receive.php")
	public void receive(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//绑定快钱IP,非快钱IP不处理
		String reqeustIp = request.getRemoteHost();
//		if(){
//			return;
//		}

		// 人民币网关账号，该账号为11位人民币网关商户编号+01,该值与提交时相同。
		String merchantAcctId = request.getParameter("merchantAcctId");
		// 网关版本，固定值：v2.0,该值与提交时相同。
		String version = request.getParameter("version");
		// 语言种类，1代表中文显示，2代表英文显示。默认为1,该值与提交时相同。
		String language = request.getParameter("language");
		// 签名类型,该值为4，代表PKI加密方式,该值与提交时相同。
		String signType = request.getParameter("signType");
		// 支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10,该值与提交时相同。
		String payType = request.getParameter("payType");
		// 银行代码，如果payType为00，该值为空；如果payType为10,该值与提交时相同。
		String bankId = request.getParameter("bankId");
		// 商户订单号，该值与提交时相同。
		String orderId = request.getParameter("orderId");
		// 订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101,该值与提交时相同。
		String orderTime = request.getParameter("orderTime");
		// 订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试,该值与支付时相同。
		String orderAmount = request.getParameter("orderAmount");
		// 快钱交易号，商户每一笔交易都会在快钱生成一个交易号。
		String dealId = request.getParameter("dealId");
		// 银行交易号 ，快钱交易在银行支付时对应的交易号，如果不是通过银行卡支付，则为空
		String bankDealId = request.getParameter("bankDealId");
		// 快钱交易时间，快钱对交易进行处理的时间,格式：yyyyMMddHHmmss，如：20071117020101
		String dealTime = request.getParameter("dealTime");
		// 商户实际支付金额 以分为单位。比方10元，提交时金额应为1000。该金额代表商户快钱账户最终收到的金额。
		String payAmount = request.getParameter("payAmount");
		// 费用，快钱收取商户的手续费，单位为分。
		String fee = request.getParameter("fee");
		// 扩展字段1，该值与提交时相同。
		String ext1 = request.getParameter("ext1");
		// 扩展字段2，该值与提交时相同。
		String ext2 = request.getParameter("ext2");
		// 处理结果， 10支付成功，11 支付失败，00订单申请成功，01 订单申请失败
		String payResult = request.getParameter("payResult");
		// 错误代码 ，请参照《人民币网关接口文档》最后部分的详细解释。
		String errCode = request.getParameter("errCode");
		// 签名字符串
		String signMsg = request.getParameter("signMsg");
		String merchantSignMsgVal = "";
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId", merchantAcctId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "version", version);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "language", language);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType", signType);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType", payType);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId", bankId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId", orderId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime", orderTime);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount", orderAmount);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId", dealId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId", bankDealId);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime", dealTime);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount", payAmount);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", fee);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", ext1);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", ext2);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult", payResult);
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode", errCode);
		Pkipair pki = new Pkipair();
		log.info("收到快钱充值结果:"+merchantSignMsgVal);
		boolean flag = pki.enCodeByCer(merchantSignMsgVal, signMsg);
		String rtnUrl = "";
		int newStatus = 0;
		if (flag) {
			switch (Integer.parseInt(payResult)) {
			case 10:
				/*
				 * 此处商户可以做业务逻辑处理
				 */
				newStatus = CommonConstants.CHARGE_STATUS.PAY_SUCCESS;
				// 以下是我们快钱设置的show页面，商户需要自己定义该页面。
				rtnUrl = appUrl + "/show.php?msg=success";
				break;
			default:
				newStatus = CommonConstants.CHARGE_STATUS.PAY_FAIL;
				// 以下是我们快钱设置的show页面，商户需要自己定义该页面。
				rtnUrl = appUrl + "/show.php?msg=false";
				break;
			}
		} else {
			newStatus = CommonConstants.CHARGE_STATUS.PAY_ERROR;
			// 以下是我们快钱设置的show页面，商户需要自己定义该页面。
			rtnUrl = appUrl + "/show.php?msg=error";
		}

		try {
			Charge charge = chargeManager.getByOrderId(Long.valueOf(orderId));
			int oldStatus = charge.getStatus();
			//只有未付款或付款失败或者付款错误的订单才处理,防止重复处理
			if(oldStatus == CommonConstants.CHARGE_STATUS.UNPAY || oldStatus == CommonConstants.CHARGE_STATUS.PAY_FAIL ||  oldStatus == CommonConstants.CHARGE_STATUS.PAY_ERROR){
				charge.setDealId(dealId);
				charge.setPayType(CommonConstants.PAY_TYPE.KUAIQIAN);
				charge.setBankId(bankId);
				charge.setBankDealId(bankDealId);
				charge.setErrCode(errCode);
				charge.setDealTime(dealTime);
				charge.setPayAmount(Integer.valueOf(payAmount));
				charge.setFee(Integer.valueOf(fee));
				charge.setStatus(newStatus);
				chargeManager.finishPay(charge);
				log.info("订单["+orderId+"]的状态由"+oldStatus+"更新为:"+newStatus);
				//只有支付成功的才充值到游戏服务器
				if(newStatus == CommonConstants.CHARGE_STATUS.PAY_SUCCESS){
					ChargeTaskUtil.sendChargeMsg(charge);
				}
			}else{
				log.info("订单["+orderId+"]之前已处理,忽略");
			}
		} catch (Exception e) {
			log.error("更新充值订单出错", e);
		}
		
		response.getWriter().write("<result>1</result> <redirecturl>"+rtnUrl+"</redirecturl>");
	}


	@RequestMapping("/show.php")
	public String show(HttpServletRequest request) {
		String orderId=(String)request.getParameter("orderId").trim();
		return "redirect:orderDetail.php?orderId="+orderId;
	}
	
	@RequestMapping("/orderDetail.php")
	public String orderDetail(HttpServletRequest request){
		String orderId=(String)request.getParameter("orderId").trim();
		Charge charge = chargeManager.getByOrderId(Long.valueOf(orderId));
		request.setAttribute("charge", charge);
		request.setAttribute("step", 3);
		return "charge";
	}

	public String appendParam(String returns, String paramId, String paramValue) {
		if (!returns.equals("")) {
			if (!paramValue.equals("")) {
				returns += "&" + paramId + "=" + paramValue;
			}
		} else {
			if (!paramValue.equals("")) {
				returns = paramId + "=" + paramValue;
			}
		}

		return returns;
	}
}
