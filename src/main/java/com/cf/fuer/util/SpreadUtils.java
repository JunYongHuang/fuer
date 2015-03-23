package com.cf.fuer.util;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cf.fuer.gc.ConnectUtil;
import com.cf.fuer.gc.Message;
import com.cf.fuer.webapp.bean.SpreadBean;

/**
 * 点一点推广活动定时向游戏服务器发送奖励。
 * 
 * @author sunke
 * 
 */
public class SpreadUtils {

	private static Map<String, SpreadBean> spreadMap = new ConcurrentHashMap<String, SpreadBean>();

	/**
	 * 用户发出的链接增加点击
	 * 
	 * @param serverCode
	 * @param managerId
	 */
	public static void addClick(String serverCode, String managerId) {
		String key = serverCode + "-" + managerId;
		SpreadBean spreadBean = spreadMap.get(key);
		if (spreadBean == null) {
			spreadBean = new SpreadBean(serverCode, managerId);
			spreadMap.put(key, spreadBean);
		}
		spreadBean.addCount();
	}

	/**
	 * 执行连接任务的线程池
	 */
	private static ScheduledExecutorService jobExec = Executors.newScheduledThreadPool(1);

	/**
	 * 发送到游戏服务器间隔（1分钟）
	 */
	private static final long JOB_PERIOD = 1;

	/**
	 * 开启点一点活动线程
	 */
	public static void startJob() {
		// 每10分钟执行1次
		jobExec.scheduleAtFixedRate(new SendPresentJob(), JOB_PERIOD, JOB_PERIOD, TimeUnit.MINUTES);
	}

	/**
	 * 作废三天仍未付款的订单
	 * 
	 * @author sunke
	 * 
	 */
	private static class SendPresentJob implements Runnable {

		@Override
		public void run() {
			if (!DateUtil.isMaintainTime()) {// 系统维护时间不发送
				Collection<SpreadBean> spreadDetails = spreadMap.values();
				spreadMap = new ConcurrentHashMap<String, SpreadBean>();
				for (SpreadBean spreadBean : spreadDetails) {
					String msg = Message.newSpreadRequest(spreadBean);
					ConnectUtil.sendMsg(msg);
				}
			}else{
				spreadMap.clear();
			}
		}

	}

}
