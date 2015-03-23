package com.cf.fuer;

import java.util.LinkedHashMap;
import java.util.Map;

public final class CommonConstants {

	public static final String COOKIE_USERNAME = "LOGINUSER";

	/** The Constant ONE_DAY_TIME_IN_MILLIS. */
	public static final long ONE_DAY_TIME_IN_MILLIS = 24 * 60 * 60 * 1000;

	/**
	 * 1元人民币兑换的金币数.
	 */
	public static final int RMB_TO_GOLD_RATE = 10;

	/**
	 * 用户ROLE
	 */
	public static final class ROLE {
		/**
		 * 普通用户
		 */
		public static final int ROLE_USER = 0;
		/**
		 * 管理员
		 */
		public static int ROLE_ADMIN = 1;
		/**
		 * 测试人员
		 */
		public static final int ROLE_TEST = 2;
		/**
		 * 黑名单用户
		 */
		public static final int ROLE_BLACK = 3;
		
		/**
		 * 推广人员
		 */
		public static final int ROLE_SPREAD = 4;

		/**
		 * 所有用户的Map;
		 */
		private static final Map<Integer, String> allMap;

		public static Map<Integer, String> all() {
			return allMap;
		}

		static {
			allMap = new LinkedHashMap<Integer, String>();
			allMap.put(ROLE_USER, "普通用户");
			allMap.put(ROLE_TEST, "测试人员");
			allMap.put(ROLE_BLACK, "黑名单用户");
			allMap.put(ROLE_SPREAD, "推广人员");
		}
	}

	/**
	 * 服务器类别
	 * 
	 * @author sunke
	 * 
	 */
	public static final class SERVER_TYPE {
		/**
		 * 正常服务器(普通用户可以看到但无法进入)
		 */
		public static final int COMMON = 0;
		/**
		 * 维护服务器(普通用户可以看到但无法进入)
		 */
		public static final int MAINTAIN = 1;
		/**
		 * 测试服务器(普通用户无法看到也无法进入,测试人员可以看到也可以进入)
		 */
		public static final int TESTING = 2;

		/**
		 * 所有服务器类别的Map;
		 */
		private static final Map<Integer, String> allMap;

		public static Map<Integer, String> all() {
			return allMap;
		}

		static {
			allMap = new LinkedHashMap<Integer, String>();
			allMap.put(COMMON, "正常服务器");
			allMap.put(MAINTAIN, "维护服务器");
			allMap.put(TESTING, "测试服务器");
		}

	}

	/**
	 * 充值状态
	 * 
	 * @author sunke
	 * 
	 */
	public static final class CHARGE_STATUS {

		/**
		 * 待支付,即充值订单刚创建时的状态
		 */
		public static final int UNPAY = 1;
		/**
		 * 支付成功,即收到从快钱返回的消息为付款成功,此状态也可以认为是未充值到游戏服务器的订单
		 */
		public static final int PAY_SUCCESS = 2;
		/**
		 * 支付失败,即收到从快钱返回的消息为付款失败
		 */
		public static final int PAY_FAIL = 3;
		/**
		 * 充值到游戏服务器成功(即订单正常完成)
		 */
		public static final int CHARGE_SUCCESS = 4;
		/**
		 * 充值到游戏服务器失败
		 */
		public static final int CHARGE_FAIL = 5;

		/**
		 * 订单作废,三天未付款的订单自动作废
		 */
		public static final int ORDER_ABANDONED = 6;

		/**
		 * 订单被取消,用户手动取消未支付的订单
		 */
		public static final int ORDER_CANCELLED = 7;
		/**
		 * 支付错误.
		 */
		public static final int PAY_ERROR = 8;

		/**
		 * 所有状态的Map;
		 */
		private static final Map<Integer, String> allMap;

		public static Map<Integer, String> all() {
			return allMap;
		}

		static {
			allMap = new LinkedHashMap<Integer, String>();
			allMap.put(UNPAY, "待支付");
			allMap.put(PAY_SUCCESS, "支付成功");
			allMap.put(PAY_FAIL, "支付失败");
			allMap.put(CHARGE_SUCCESS, "充值成功");
			allMap.put(CHARGE_FAIL, "充值失败");
			allMap.put(ORDER_ABANDONED, "已作废");
			allMap.put(ORDER_CANCELLED, "已取消");
			allMap.put(PAY_ERROR, "支付错误");
		}

	}

	/**
	 * 文章分类类别
	 * 
	 * @author sunke
	 * 
	 */
	public static enum ARTICLE_TYPE {
		/**
		 * 游戏介绍
		 */
		ABOUT(1, "游戏介绍", "/about/gameAbout.php"),
		/**
		 * 新手指南
		 */
		GUIDE(2, "新手指南", "/guide/beginnerGuide.php"),
		/**
		 * 游戏新闻
		 */
		NEWS(3, "游戏新闻", "/news/gameNews.php"),
		/**
		 * 游戏公告
		 */
		NOTICE(4, "游戏公告", "/notice/gameNotice.php");
		int type;
		String label;
		String url;

		private ARTICLE_TYPE(int type, String label, String url) {
			this.type = type;
			this.label = label;
			this.url = url;
		}

		public int getType() {
			return type;
		}

		public String getLabel() {
			return label;
		}

		public String getUrl() {
			return url;
		}

		/**
		 * 根据URL获取类型
		 */
		public static ARTICLE_TYPE fromUrl(String url) {
			for (ARTICLE_TYPE article_type : ARTICLE_TYPE.values()) {
				if (article_type.getUrl().equals(url)) {
					return article_type;
				}
			}
			return null;
		}

		/**
		 * 根据类型值获取类型
		 */
		public static String getTypeLabel(int type) {
			for (ARTICLE_TYPE article_type : ARTICLE_TYPE.values()) {
				if (article_type.getType() == type) {
					return article_type.getLabel();
				}
			}
			return String.valueOf(type);
		}

	}

	/**
	 * 性别
	 * 
	 * @author sunke
	 * 
	 * @date 2012-5-4
	 */
	public static final class GENDER {
		/**
		 * 1:男
		 */
		public static final short MALE = 1;
		/**
		 * 2:女
		 */
		public static final short FEMALE = 0;

		/**
		 * 所有性别的Map;
		 */
		private static final Map<Short, String> genderMap;

		/**
		 * 所有性别
		 */
		public static Map<Short, String> all() {
			return genderMap;
		}

		static {
			genderMap = new LinkedHashMap<Short, String>();
			genderMap.put(MALE, "男");
			genderMap.put(FEMALE, "女");
		}
	}

	/**
	 * 第三方登陆的登陆类型
	 * 
	 * @author sunke
	 * 
	 */
	public static final class LOGIN_TYPE {

		/**
		 * 1:QQ
		 */
		public static final int QQ = 1;
		/**
		 * 2:新浪微博
		 */
		public static final int SINA_WEBO = 2;
		/**
		 * 3:人人网
		 */
		public static final int RENREN = 3;
		/**
		 * 4:开心网
		 */
		public static final int KAIXIN = 4;

		/**
		 * 所有登陆类型的Map;
		 */
		private static final Map<Integer, String> allMap;

		/**
		 * 所有登陆类型
		 */
		public static Map<Integer, String> all() {
			return allMap;
		}

		static {
			allMap = new LinkedHashMap<Integer, String>();
			allMap.put(QQ, "QQ");
			allMap.put(SINA_WEBO, "新浪微博");
			allMap.put(RENREN, "人人网");
		}
	}

	/**
	 * 用户来源
	 * 
	 * @author sunke
	 * 
	 */
	public static final class USER_FROM {

		/**
		 * 101:虎扑
		 */
		public static final String HUPU = "101";

		/**
		 * 所有登陆类型的Map;
		 */
		private static final Map<String, String> allMap;

		/**
		 * 所有登陆类型
		 */
		public static Map<String, String> all() {
			return allMap;
		}

		static {
			allMap = new LinkedHashMap<String, String>();
			allMap.put(HUPU, "虎扑");
		}
	}

	/**
	 * 支付方式
	 * 
	 * @author sunke
	 * 
	 */
	public static final class PAY_TYPE {
		/**
		 * 支付宝
		 */
		public static final String ALIPAY = "01";
		/**
		 * 快钱支付
		 */
		public static final String KUAIQIAN = "10";

		/**
		 * 所有状态的Map;
		 */
		private static final Map<String, String> allMap;

		public static Map<String, String> all() {
			return allMap;
		}

		static {
			allMap = new LinkedHashMap<String, String>();
			allMap.put(ALIPAY, "支付宝");
			allMap.put(KUAIQIAN, "快钱支付");
		}
	}
}
