package com.cf.fuer.util;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cf.fuer.manager.IChargeManager;

/**
 * 每日凌晨1点执行的计划任务
 * 
 * @author sunke
 * 
 */
@Component
public class DayJob implements InitializingBean {

	private static Log log = LogFactory.getLog(DayJob.class);

	/**
	 * 执行连接任务的线程池
	 */
	private ScheduledExecutorService connectExec = Executors.newScheduledThreadPool(1);

	private static IChargeManager chargeManager;

	/**
	 * 一天对应的毫秒数
	 */
	private static final long ONE_DAY_IN_MILLIS = 1 * 24 * 60 * 60 * 1000;
	/**
	 * 一小时对应的毫秒数
	 */
	private static final long ONE_HOUR_IN_MILLIS = 1 * 60 * 60 * 1000;

	/**
	 * 订单作废时间：3天
	 */
	private static final int ABANDON_PERIOD = -3;

	@Override
	public void afterPropertiesSet() throws Exception {
		// 凌晨1点执行
		long initialDelay = DateUtil.getTomorrowInMillis() + ONE_HOUR_IN_MILLIS - System.currentTimeMillis();
		connectExec.scheduleAtFixedRate(new AbandonedOrderJob(), initialDelay, ONE_DAY_IN_MILLIS, TimeUnit.MILLISECONDS);
	}

	/**
	 * 作废三天仍未付款的订单
	 * 
	 * @author sunke
	 * 
	 */
	private static class AbandonedOrderJob implements Runnable {

		@Override
		public void run() {
			log.info("开始执行作废未付款订单任务。。。");
			Date abandonDate = DateUtil.getAbandonDate(ABANDON_PERIOD);
			chargeManager.abandonedUnPayChargeOrder(abandonDate);
		}

	}

	@Autowired
	public void setChargeManager(IChargeManager chargeManager) {
		DayJob.chargeManager = chargeManager;
	}

}
