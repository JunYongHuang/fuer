package com.cf.fuer.manager;

import java.util.Date;
import java.util.List;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.model.Charge;
import com.cf.fuer.webapp.bean.SearchBean;

public interface IChargeManager {

	Long create(Charge charge) throws ValidationException;

	Charge get(Long id);

	/**
	 * 根据订单号获取充值订单
	 */
	Charge getByOrderId(Long orderId);

	/**
	 * 完成支付,返回更新的条数0(失败)或1(成功)
	 */
	int finishPay(Charge charge);

	void search(SearchBean searchBean);

	/**
	 * 完成充值到游戏服务器.
	 * 
	 * @param orderId
	 *            充值编号
	 * @param status
	 *            状态,只能为充值成功或失败
	 * @return 更新的条数0(失败)或1(成功)
	 */
	int finishCharge(String orderId, int status);
	
	/**
	 * 获取某台(所有)服务器充值未完成(付款成功或充值失败)的订单
	 * 
	 * @param serverCode
	 *            服务器代码或所有游戏服务器
	 * @return 未完成的订单列表,按付款日期排序
	 */
	public List<Charge> fetchUnFinishedCharge(String serverCode);

	/**
	 * 作废已过期且未支付的订单
	 * @param abandonDate 作废日期，通常是当天日期减去3天
	 */
	void abandonedUnPayChargeOrder(Date abandonDate);

	/**
	 * 用户取消未支付的订单，需要验证订单创建人
	 */
	int cancelChargeOrder(String orderId);

}
