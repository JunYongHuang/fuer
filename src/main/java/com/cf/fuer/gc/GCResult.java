package com.cf.fuer.gc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GCResult {

	private GCResult() {
	}
	
	private static GCResult singleton = new GCResult();
	
	public static GCResult getInstance(){
		return singleton;
	}


	/**
	 * 登陆结果Map
	 */
	private Map<String, Message> resultMap = new ConcurrentHashMap<String, Message>();

	/**
	 * 获取请求结果(登陆和充值验证)
	 * 
	 * @param requestId
	 *            请求的序号
	 * @return 请求结果,如果未返回结果则为null
	 */
	public  Message getResult(String requestId) {
		return resultMap.get(requestId);
	}

	/**
	 * 清除结果,用于登陆或充值验证完成之后
	 * 
	 * @param requestId
	 *            请求的序号
	 */
	public  void removeResult(String requestId) {
		resultMap.remove(requestId);
	}

	public  void putMsg(String requestId, Message msg) {
		resultMap.put(requestId, msg);
	}

	/**
	 * 清空MAP.
	 */
	public void clearAll() {
		resultMap.clear();		
	}

}
