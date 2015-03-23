package com.cf.fuer.gc;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.bank.ChargeTaskUtil;
import com.cf.fuer.manager.IChargeManager;
import com.cf.fuer.util.DateUtil;
import com.cf.fuer.util.GameServerUtils;

@Component
public class ConnectUtil extends IoHandlerAdapter implements InitializingBean {

	private static Log log = LogFactory.getLog(ConnectUtil.class);

	@Value("#{jdbcSettings['gc.host']}")
	private String centerServerHost;

	@Value("#{jdbcSettings['gc.port']}")
	private int centerServerPort;

	@Autowired
	private IChargeManager chargeManager;

	@Override
	public void afterPropertiesSet() throws Exception {
//		IoConnector connector = new NioSocketConnector();
////		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));PrefixedStringCodecFactory factory = new PrefixedStringCodecFactory(Charset.forName("UTF-8"));
//		PrefixedStringCodecFactory factory = new PrefixedStringCodecFactory(Charset.forName("UTF-8"));
//		factory.setDecoderPrefixLength(4);
//		factory.setEncoderPrefixLength(4);
//		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(factory));
//		connector.getSessionConfig().setUseReadOperation(true);
//		connector.setHandler(this);
//		connector.setDefaultRemoteAddress(new InetSocketAddress(centerServerHost, centerServerPort));
//		log.info("开始连接中央服务器.....");
//		ConnectTaskUtil.connect(connector);
	}

	private static IoSession gcSession;

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.info("中央服务器连接成功!");
		GCResult.getInstance().clearAll();
		offlineServerMap.clear();
		gcSession = session;
		// 连接后进行注册
		String regRequest = Message.newRegRequest();
		log.info("发送注册消息:" + regRequest);
		session.write(regRequest);
		// 注册后发送未完成的充值任务
		ChargeTaskUtil.startChargeTask(MessageConstants.ALL_GAME_SERVER);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("Session 关闭");
		gcSession = null;
		ConnectTaskUtil.connect((IoConnector) session.getService());
		super.sessionClosed(session);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		gcSession = null;
		ConnectTaskUtil.connect((IoConnector) session.getService());
		log.info("Session 异常", cause);
	}

	@Override
	public void messageReceived(IoSession session, Object msg) throws Exception {
		log.info("接收到:" + msg.toString());
		Message message = new Message(msg.toString());
		if (message.isResponse()) {
			if (message.is404Result()) {// 目标服务器不在线,加入到离线列表
				String offlineServer = message.getOffline();
				offlineServerMap.put(offlineServer, offlineServer);
			}
			if (message.isLoginMsg() || message.isCheckRoleMsg()) {// 登陆或验证响应
				message.setReceiveTime(System.currentTimeMillis());
				//resultMap.put(message.getRequestId(), message);
				GCResult.getInstance().putMsg(message.getRequestId(), message);
			} else if (message.isChargeMsg()) {// 充值响应
				int status = CommonConstants.CHARGE_STATUS.CHARGE_FAIL;// 404或500都认为是失败
				if (message.isOkResult()) {// 正常充值完成
					status = CommonConstants.CHARGE_STATUS.CHARGE_SUCCESS;
					GCResult.getInstance().putMsg(message.getRequestId(), message);
					log.info("订单号:" + message.getRequestId() + "充值成功:" + status);
				} else {
					log.warn("订单号:" + message.getRequestId() + "充值失败:" + message.getMessage());
				}
				chargeManager.finishCharge(message.getRequestId(), status);
			}
		} else {// 请求
			if (message.isLoginFeedbackMsg()) {// 登陆反馈
				GameServerUtils.insertRegServer(message.getUsername(), message.getFrom(), message.getManagerId(), message.getManagerName(), message.getRegistedCount());
				log.info("用户[" + message.getUsername() + "]登陆游戏服务器" + message.getFrom() + "成功,创建角色:" + message.getManagerName());
			} else if (message.isOnlineMsg()) {// 上线通知
				String onlineServer = message.getOnline();
				if(DateUtil.isMaintainTime()){
					GCResult.getInstance().clearAll();
				}
				// 清除离线列表
				offlineServerMap.remove(onlineServer);
				log.info("服务器" + onlineServer + "上线");
				// 开始未完成的充值任务
				ChargeTaskUtil.startChargeTask(onlineServer);
			}
		}
	}

	/**
	 * 通过中央服务器发送消息(GO-->中央服务器-->目标服务器),<font color="red">发送之前首先调用
	 * {@link #canSend(String)}方法判断目标地址是否可达</font>
	 * 
	 * @param msg
	 *            消息内容
	 */
	public static void sendMsg(String msg) {
		//log.info("发送请求:" + msg);
		gcSession.write(msg);
	}

	public static Map<String, String> offlineServerMap = new ConcurrentHashMap<String, String>();

	/**
	 * 判断是否可以向目标服务器发送消息,只有当中央服务器和目标服务器都在线时可以发送.
	 * 
	 * @param targetServer
	 *            目标服务器code
	 * @return ture 可以发送, false 不可发送
	 */
	public static boolean canSend(String targetServer) {
		if(gcSession == null){
			log.info("无法发送,中央服务器(GC)没有连接!");
			return false;
		}
		if(offlineServerMap.get(targetServer) != null){
			log.info("无法发送,服务器["+targetServer+"]不在线!");
			return false;
		}
		return true;
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.info("Session 空闲");
	}

}
