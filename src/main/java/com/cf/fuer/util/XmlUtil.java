package com.cf.fuer.util;

import java.sql.Timestamp;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class XmlUtil {

	/**
	 * 获取XML某一节点的body字符串内容<br>
	 * 正则表达式为 <b><i>{@code "<nodeName[^>]*>(.+?)</nodeName>"}</i></b>
	 * 
	 * @param nodeName
	 *            节点名称
	 * @param xmlData
	 *            XML内容
	 * @return 节点的body内容
	 */
	public static String getStrNodeBody(String nodeName, String xmlData) {
		String value = "";
		if (xmlData == null || xmlData.isEmpty()) {
			return value;
		}
		xmlData = removeNewLineChar(xmlData);
		StringBuilder regex = new StringBuilder("<");
		regex.append(nodeName);
		regex.append("[^>]*>(.+?)</");
		regex.append(nodeName);
		regex.append(">");
		Pattern p = Pattern.compile(regex.toString());
		Matcher m = p.matcher(xmlData);
		if (m.find()) {
			value = m.group(1);
		}
		return value;
	}

	/**
	 * 获取XML某一节点的body数字内容<br>
	 * 正则表达式为 <b><i>{@code "<nodeName[^>]*>(.+?)</nodeName>"}</i></b>
	 * 
	 * @param nodeName
	 *            节点名称
	 * @param xmlData
	 *            XML内容
	 * @return 节点的body内容
	 */
	public static int getIntNodeBody(String nodeName, String xmlData) {
		String body = getStrNodeBody(nodeName, xmlData);
		if (body.isEmpty()) {
			return 0;
		}
		return Integer.valueOf(body);
	}

	/**
	 * 获取XML某一节点的body日期内容<br>
	 * 正则表达式为 <b><i>{@code "<nodeName[^>]*>(.+?)</nodeName>"}</i></b>
	 * 
	 * @param nodeName
	 *            节点名称
	 * @param xmlData
	 *            XML内容
	 * @return 节点的body内容
	 */
	public static Timestamp getTimeNodeBody(String nodeName, String xmlData) {
		String body = getStrNodeBody(nodeName, xmlData);
		try {
			return Timestamp.valueOf(body);
		} catch (Exception e) {
			return new Timestamp(System.currentTimeMillis());
		}
	}

	/**
	 * 将所有的换行符替换为空格，如\r,\n等
	 * 
	 * @return 替换后的字符串
	 */
	private static String removeNewLineChar(String str) {
		return str.replaceAll("[\\r\\n|\\n|\\r]", " ").trim();
	}

	/**
	 * 创建节点
	 * 
	 * @param nodeName
	 * @param value
	 * @return
	 */
	public static StringBuilder createNode(String nodeName, Object value) {
		StringBuilder node = new StringBuilder();
		node.append("<");
		node.append(nodeName);
		node.append(">");
		node.append(value);
		node.append("</");
		node.append(nodeName);
		node.append(">");
		return node;
	}

	/**
	 * 私有构造函数，防止实例化
	 */
	private XmlUtil() {
	};
	
}
