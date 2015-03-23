package com.cf.fuer.bank;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.dao.IChargeDao;

@Component
public class ChargeNumberUtils implements InitializingBean {

	private static IChargeDao chargeDao;

	/**
	 * 获取活动返还金币数量
	 * 单笔充值返金币（活动返现   100(含)~500返5%；500(含)~1000返10%；1000(含)~5000返15%；5000(含)以上返20%）
	 * @param serverCode
	 *            服务器代码
	 * @param rmbMoney
	 *            充值金额（单位为分）
	 * @return 返还金币数
	 */
	public static long getPresntGold(String serverCode, long rmbMoney) {
		if (chargeDao.hasActivity(serverCode)) {
			long presentRmb = 0;
			rmbMoney = rmbMoney / 100;
			if (rmbMoney >= 100 && rmbMoney <= 499) {//100(含)~500返5%;
				presentRmb = rmbMoney * 100 * 5 / 100;
			} else if (rmbMoney >= 500 && rmbMoney <= 999) {//500(含)~1000返10%;
				presentRmb = rmbMoney * 100 * 10 / 100;
			} else if (rmbMoney >= 1000 && rmbMoney <= 4999) {//1000(含)~5000返15%;
				presentRmb = rmbMoney * 100  * 15 / 100;
			} else if (rmbMoney >= 5000) {//5000(含)以上返20%
				presentRmb = rmbMoney * 100  * 20 / 100;
			}
			return presentRmb * CommonConstants.RMB_TO_GOLD_RATE / 100;
		}
		return 0L;
	}

	/**
	 * 生成订单号,订单号生成规则:当前毫秒数的前8位+订单序号
	 */
	public static Long generateChargeNumber() {
		String now = String.valueOf(System.currentTimeMillis());
		String prefix = now.substring(0, NUM_LENGTH);
		String chargeNumberStr = prefix + orderSequence.getAndIncrement();
		return Long.valueOf(chargeNumberStr);
	}

	/**
	 * 订单号前缀的位数.
	 */
	private static final int NUM_LENGTH = 8;

	/**
	 * 产生订单序号的序列
	 */
	private static AtomicLong orderSequence;

	/**
	 * 启动时从数据库获取订单号的最大值,根据最大值设置初始值,防止订单号重复.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Long sequence = 0L;
		try {
			Long maxOrderId = chargeDao.getMaxOrderId();
			String sequenceStr = maxOrderId.toString().substring(NUM_LENGTH);
			sequence = Long.valueOf(sequenceStr);
		} catch (Exception e) {
			// 数据库为空时会报异常,忽略
		}
		orderSequence = new AtomicLong(sequence + 1);
	}

	@Autowired
	public void setChargeDao(IChargeDao chargeDao) {
		ChargeNumberUtils.chargeDao = chargeDao;
	}

}
