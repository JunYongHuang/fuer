package com.cf.fuer.webapp.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.util.AlipayNotify;
import com.cf.fuer.CommonConstants;
import com.cf.fuer.bank.ChargeTaskUtil;
import com.cf.fuer.manager.IChargeManager;
import com.cf.fuer.model.Charge;
import com.cf.fuer.util.TokenUtil;

@Controller
public class AlipayChargeController {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private IChargeManager chargeManager;

	@RequestMapping("/alipay_return.php")
	public String alipay_return(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("return URL IP: "+TokenUtil.getRequestIp(request));
		processCharge(request, true);
		String out_trade_no = getParameter(request,"out_trade_no");
		return "redirect:orderDetail.php?orderId="+out_trade_no;
	}
	
	@RequestMapping("/alipay_notify.php")
	public void alipay_notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("notify URL IP: "+TokenUtil.getRequestIp(request));
		boolean result = processCharge(request, false);
		if(result){
			response.getWriter().println("success");	//请不要修改或删除
		}else{
			response.getWriter().println("fail");
		}
	}
	
	private boolean processCharge(HttpServletRequest request, boolean changeCharset){
		//获取支付宝GET过来反馈信息
		Map<String,String> params = getParamMap(request, changeCharset);
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		String out_trade_no = getParameter(request,"out_trade_no");

		//支付宝交易号
		String trade_no = getParameter(request,"trade_no");

		String notify_time = getParameter(request,"notify_time");
		//交易状态
		String trade_status = getParameter(request,"trade_status");
		
		String total_fee = getParameter(request,"total_fee");

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		log.info("收到充值结果:"+params);
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		if(verify_result){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码

			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				try {
					Charge charge = chargeManager.getByOrderId(Long.valueOf(out_trade_no));
					int oldStatus = charge.getStatus();
					int newStatus = CommonConstants.CHARGE_STATUS.PAY_SUCCESS;
					//只有未付款或付款失败或者付款错误的订单才处理,防止重复处理
					if(oldStatus == CommonConstants.CHARGE_STATUS.UNPAY || oldStatus == CommonConstants.CHARGE_STATUS.PAY_FAIL ||  oldStatus == CommonConstants.CHARGE_STATUS.PAY_ERROR){
						charge.setDealId(trade_no);
						charge.setPayType(CommonConstants.PAY_TYPE.ALIPAY);
						charge.setDealTime(notify_time);
						Float payAmount = Float.valueOf(total_fee) * 100;
						charge.setPayAmount(payAmount.intValue());
						charge.setStatus(newStatus);
						chargeManager.finishPay(charge);
						log.info("支付宝订单["+out_trade_no+"]的状态由"+oldStatus+"更新为:"+newStatus);
//						//充值到游戏服务器
						ChargeTaskUtil.sendChargeMsg(charge);
					}else{
						log.info("支付宝订单["+out_trade_no+"]之前已处理,忽略");
					}
					return true;
				} catch (Exception e) {
					log.error("更新支付宝充值订单出错", e);
					return false;
				}
			}
			//////////////////////////////////////////////////////////////////////////////////////////
		}else{
			//该页面可做页面美工编辑
			log.error("支付宝充值验证验证失败");
			
		}
		return false;
	}
	
	/**
	 * 获取支付宝返回参数，用于验证
	 */
	private Map<String,String> getParamMap(HttpServletRequest request, boolean changeCharset){
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			if(changeCharset){
				try {
					valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			params.put(name, valueStr);
		}
		return params;
	}
	
	private String getParameter(HttpServletRequest request, String paramName){
		try {
			return new String(request.getParameter(paramName).getBytes("ISO-8859-1"),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
