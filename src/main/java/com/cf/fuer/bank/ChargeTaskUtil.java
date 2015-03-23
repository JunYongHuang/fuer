package com.cf.fuer.bank;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.gc.ConnectTaskUtil;
import com.cf.fuer.gc.ConnectUtil;
import com.cf.fuer.gc.Message;
import com.cf.fuer.gc.MessageConstants;
import com.cf.fuer.manager.IChargeManager;
import com.cf.fuer.model.Charge;

/**
 * 此类主要用于定时发送未完成的充值请求
 * 
 * @author sunke
 * 
 */
@Component
public class ChargeTaskUtil implements InitializingBean {

	private static Log log = LogFactory.getLog(ConnectTaskUtil.class);

	private static IChargeManager chargeManager;

	private static Map<String, String> runningChargeServerMap = new ConcurrentHashMap<String, String>();

	/**
	 * 开始发送未完成的充值任务线程,目标服务器上线时或本服务器启动或中央服务器启动时执行此方法.
	 */
	public static void startChargeTask(final String serverCode) {
		if (!isTargetServerTaskRunning(serverCode)) {
			runningChargeServerMap.put(serverCode, serverCode);
			Thread thread = new Thread() {
				@Override
				public void run() {
					boolean hasUnfinishedCharges = true;
					try{
						try {
							if(MessageConstants.ALL_GAME_SERVER.equals(serverCode)){
								//休眠3秒,等待注册完成.
								Thread.sleep(3000L);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						List<Charge> unfinishedCharges = chargeManager.fetchUnFinishedCharge(serverCode);
						hasUnfinishedCharges = !unfinishedCharges.isEmpty();
						if(hasUnfinishedCharges){
							log.info("开始充值任务到["+serverCode+"]...");
							for (Charge charge : unfinishedCharges) {
								sendChargeMsg(charge);
							}	
						}					
					}finally{
						runningChargeServerMap.remove(serverCode);
						if(hasUnfinishedCharges){
							log.info("到["+serverCode+"]的充值任务结束!");
						}
					}
				}
			};
			thread.start();
		}
	}

	/**
	 * 目标服务器有任务在运行.
	 * 
	 * @param serverCode
	 *            目标服务器代码
	 * @return true 目标服务器充值任务在运行
	 */
	private static boolean isTargetServerTaskRunning(String serverCode) {
		// 所有游戏服务器充值线程在运行
		if (runningChargeServerMap.get(MessageConstants.ALL_GAME_SERVER) != null) {
			return true;
		}
		return runningChargeServerMap.get(serverCode) != null;
	}

	public static void sendChargeMsg(Charge charge) {
		String targetServer = charge.getGameServer();
		Long orderId = charge.getOrderId();
		if (!ConnectUtil.canSend(targetServer)) {
			// 路径不通
			log.info("充值请求"+orderId+"取消,目标服务器["+targetServer+"]无法连接!");
			return;
		}
		// 只有付款成功或者充值到游戏服务器失败的订单才可以发送充值请求
		if (charge.getStatus() != CommonConstants.CHARGE_STATUS.CHARGE_FAIL && charge.getStatus() != CommonConstants.CHARGE_STATUS.PAY_SUCCESS) {
			log.warn("充值请求" + orderId + "失败:订单状态["+charge.getStatus()+"]有误");
			return;
		}
		String chargeMsg = Message.newChargeRequest(charge);
		log.info("发送充值请求:" + charge.getOrderId());
		ConnectUtil.sendMsg(chargeMsg);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	/**
	 * 启动时注入
	 */
	@Autowired
	public void setChargeManager(IChargeManager chargeManager) {
		ChargeTaskUtil.chargeManager = chargeManager;
	}

}
