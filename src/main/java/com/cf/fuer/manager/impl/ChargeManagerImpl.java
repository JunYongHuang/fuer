package com.cf.fuer.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.bank.ChargeNumberUtils;
import com.cf.fuer.dao.IChargeDao;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IChargeManager;
import com.cf.fuer.model.Charge;
import com.cf.fuer.util.GameServerUtils;
import com.cf.fuer.validator.ChargeValidator;
import com.cf.fuer.webapp.bean.SearchBean;

@Service
public class ChargeManagerImpl implements IChargeManager {

	@Autowired
	private IChargeDao chargeDao;

	@Autowired
	private ChargeValidator chargeValidator;

	@Override
	public Long create(Charge charge) throws ValidationException {
		chargeValidator.validateCreate(charge);
		//充值单位为分,转换为元后再转换成金币
		long rmbMoney = charge.getChargeAmount();
		charge.setGoldMoney(rmbMoney * CommonConstants.RMB_TO_GOLD_RATE / 100);
		
		long presentGold = ChargeNumberUtils.getPresntGold(charge.getGameServer(), rmbMoney);
		String presentName = "";
		if(presentGold > 0){
			presentName = "活动返现";
		}
		charge.setPresentName(presentName);
		charge.setPresentGold(presentGold);
		
		String managerName = GameServerUtils.getManagerName(charge.getUser(), charge.getGameServer());
		charge.setManagerName(managerName);
		return chargeDao.createCharge(charge);
	}

	@Override
	public Charge get(Long id) {
		return chargeDao.get(id);
	}

	@Override
	public Charge getByOrderId(Long orderId) {
		return chargeDao.getByOrderId(orderId);
	}

	@Override
	public int finishPay(Charge charge) {
		return chargeDao.finishPay(charge);
	}

	@Override
	public void search(SearchBean searchBean) {
		chargeDao.search(searchBean);
	}
	
	/**
	 * 完成充值到游戏服务器.
	 * 
	 * @param orderId
	 *            充值编号
	 * @param status
	 *            状态,只能为充值成功或失败
	 * @return 更新的条数0(失败)或1(成功)
	 */
	@Override
	public int finishCharge(String orderId, int status){
		return chargeDao.finishCharge(orderId, status);
	}

	@Override
	public List<Charge> fetchUnFinishedCharge(String serverCode) {
		return chargeDao.fetchUnFinishedCharge(serverCode);
	}

	@Override
	public void abandonedUnPayChargeOrder(Date abandonDate) {
		chargeDao.abandonedUnPayChargeOrder(abandonDate);
	}

	@Override
	public int cancelChargeOrder(String orderId) {
		return chargeDao.cancelChargeOrder(orderId);
	}

}
