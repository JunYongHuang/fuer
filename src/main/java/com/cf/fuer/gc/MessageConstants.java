package com.cf.fuer.gc;

public class MessageConstants {
	/**
	 * 本服务器代码
	 */
	public static final String SERVER_CODE = "GO";

	/**
	 * 中央服务器代码
	 */
	public static final String CENTER_SERVER_CODE = "GC";
	/**
	 * 所有游戏服务器
	 */
	public static final String ALL_GAME_SERVER = "ALL";

	/**
	 * 响应超时时间.
	 */
	public static final Long TIMEOUT = 3000L;

	/**
	 * 操作类型码
	 * 
	 * @author sunke
	 * 
	 */
	public static class TYPE {
		/**
		 * 注册
		 */
		public static final int REGISTER = 1;

		/**
		 * 上线
		 */
		public static final int ONLINE = 2;

		/**
		 * 登陆请求
		 */
		public static final int LOGIN = 101;
		/**
		 * 登陆反馈
		 */
		public static final int LOGIN_FEEDBACK = 102;
		/**
		 * 充值
		 */
		public static final int CHARGE = 103;
		/**
		 * 验证用户是否在游戏服务器创建过角色
		 */
		public static final int CHECK_ROLE = 104;

		/**
		 * 发放奖励
		 */
		public static final int PRESENT = 105;
	}

	/**
	 * 响应状态码
	 * 
	 * @author sunke
	 * 
	 */
	public static class RESULT_STATUS {
		/**
		 * 成功
		 */
		public static final int OK = 200;
		/**
		 * 未找到
		 */
		public static final int NOT_FOUND = 404;
		/**
		 * 错误
		 */
		public static final int ERROR = 500;
	}

	/**
	 * 发放的奖励类型
	 * 
	 * @author sunke
	 * 
	 */
	public static class PRESENT_TYPE {

		/**
		 * 金币
		 */
		public static final int GOLD = 1;
		/**
		 * 道具
		 */
		public static final int WARE = 2;
		/**
		 * 装备
		 */
		public static final int EQUIPMENT = 3;
		/**
		 * 球星
		 */
		public static final int STAR = 2;
		/**
		 * 游戏资金
		 */
		public static final int MONEY = 5;
	}

	/**
	 * 发放奖励活动类型
	 * 
	 * @author sunke
	 * 
	 */
	public static class ACTIVITY_TYPE {
		/**
		 * 点一点推广活动
		 */
		public static final int SPREAD = 1;
		/**
		 * 开服活动
		 */
		public static final int KAIFU = 2;
	}
}
