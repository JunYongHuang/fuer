package com.cf.fuer.gc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;

public class ConnectTaskUtil {
	
	private static Log log = LogFactory.getLog(ConnectTaskUtil.class);
	
	private static AtomicBoolean isConnecting = new AtomicBoolean(false);
	
	public static void connect(IoConnector connector){
		boolean update = true;
		boolean expect = false;
		//如果是false(即未正在连接),设置为true后进行连接
		boolean success = isConnecting.compareAndSet(expect , update);
		if(success){
			new ConnectTasker(connector);
		}
	}
	
	/**
	 * 执行连接任务的线程池
	 */
	private static ScheduledExecutorService connectExec = Executors.newScheduledThreadPool(1);
	
	/**
	 * 重连服务器的间隔
	 */
	private static final int CONNECT_INTEVAL_TIME = 15;
	
	/**
	 * 用于服务器进行连接,开始100次重连间隔为100 * tryCount毫秒,如果20次之后还不通,重连间隔改为15秒.
	 * 
	 * @author sunke
	 * 
	 * @date 2012-4-10
	 */
	private static class ConnectTasker implements Runnable {
		
		/**
		 * 更改心跳间隔重试的次数.
		 */
		private static final int CHANGE_INTEVAL_TRY_COUNT = 100;
		/**
		 * 前CHANGE_INTEVAL_TRY_COUNT重试的心跳间隔(毫秒)
		 */
		private static final long MIN_INTEVAL_TIME = 100;
		/**
		 * 重试次数
		 */
		private int tryCount = 0;
		
		private IoConnector connector;

		private ConnectTasker(IoConnector connector) {
			super();
			this.connector = connector;
			connectExec.schedule(this, 0, TimeUnit.MILLISECONDS);
		}

		@Override
		public void run() {
			try{
				ConnectFuture future = connector.connect();
				// 等待是否连接成功，相当于是转异步执行为同步执行。 
				future.awaitUninterruptibly(); 
				// 连接成功后获取会话对象。如果没有上面的等待，由于connect()方法是异步的，session可能会无法获取。 
				future.getSession();
				isConnecting.set(false);
				if(tryCount > 0){
					log.info("中央服务器连接成功,共重试"+tryCount+"次!");
				}
			}catch(Exception e){
				//防止trycount过大
				if(++tryCount > Integer.MAX_VALUE - CHANGE_INTEVAL_TRY_COUNT){
					log.info("计数器过大,重置重连计数器");
					tryCount = CHANGE_INTEVAL_TRY_COUNT + 1;
				}
				if(tryCount % 10 == 1){//每10次打印一个日志
					log.info("中央服务器连接失败,尝试进行第"+tryCount+"次重连.....");
				}
				if(tryCount <= CHANGE_INTEVAL_TRY_COUNT){
					connectExec.schedule(this, MIN_INTEVAL_TIME * tryCount, TimeUnit.MILLISECONDS);
				}else{
					connectExec.schedule(this, CONNECT_INTEVAL_TIME, TimeUnit.SECONDS);
				}
			}
		}

	}

}
