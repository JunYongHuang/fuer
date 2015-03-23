package com.cf.fuer.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.cf.fuer.util.StringUtil;

@Component
public class BaseValidator {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 字符串最大长度
	 */
	public static final int STR_MIN_LENGTH = 2;
	
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 字符串最小长度
	 */
	public static final int STR_MAXLENGTH = 15;

	/**
	 * Check if the bean is used by other bean.
	 *
	 * @return true if the object is been used by other bean, else return false.
	 */
	public boolean usedByOtherBean(String EntityName, String fieldName, String id) {
		String sql = "select count(*) from " + EntityName + " entity where entity." + fieldName + " =:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return jdbcTemplate.queryForInt(sql, paramMap) > 0;
	}

	/**
	 * 验证字段的值是否存在于数据库中.
	 *
	 * @return true 不存在，false存在
	 */
	public boolean notExist(String EntityName, String fieldName, Object value) {
		return this.notExist(EntityName, fieldName, value, null);
	}

	/**
	 * 验证字段的值是否存在于数据库中，自己使用的不算。
	 *
	 * @return true 不存在，false存在
	 */
	public boolean notExist(String EntityName, String fieldName, Object value,
			Long id) {
		String sql = "select count(*) from " + EntityName
				+ " entity where entity." + fieldName + " =:value";
		if (id != null) {
			sql += " and entity.id != " + id+"";
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("value", value);
		return jdbcTemplate.queryForInt(sql, paramMap) == 0;
	}

	/**
	 * 验证字符串是否为空(null或者empty或者-1)
	 *
	 * @return true 字符串为null或empty, false 不为空
	 */
	public boolean isBlank(String str) {
		return str == null || str.isEmpty() || str.equals("-1");
	}

	/**
	 * 验证字符串长度是否合法（2-15之间）.
	 * <p>
	 * 注意：进行此验证之前需要先调用isBlank()验证是否为空，方便显示不同的消息
	 * </p>
	 *
	 * @return true 字符串不为空且长度在2－15（含）之间, 反之返回false
	 */
	public boolean isValideLenthStr(String str) {
		return isValideLenthStr(str, STR_MIN_LENGTH, STR_MAXLENGTH);
	}

	/**
	 * 验证字符串长度是否合法（minLength到maxLength之间）.
	 * <p>
	 * 注意：进行此验证之前需要先调用isBlank()验证是否为空，方便显示不同的消息
	 * </p>
	 *
	 * @return true 字符串不为空且长度在minLength到maxLength（含）之间, 反之返回false
	 */
	public boolean isValideLenthStr(String str, int minLength, int maxLength) {
		boolean result = true;
		if (isBlank(str)) {
			result = false;
		} else {
			str = str.trim();
			if (str.length() < minLength || str.length() > maxLength) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 验证字符串是否为数字类型
	 * @param str
	 * @return 是数字或为空返回true，非数字返回false
	 */
	public boolean isNumeric(String str) {
		return StringUtil.isNumeric(str);
	}

	/**
	 * 验证字符是否为正确的IP地址
	 * @param str
	 * @return 正确返回true，错误返回false
	 */
	public boolean isIP(String str){
		boolean result =false;
		if(str.matches("^([01]?[0-9][0-9]|[01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([01]?[0-9][0-9]|[01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([01]?[0-9][0-9]|[01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])\\.([01]?[0-9][0-9]|[01]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])$")){
			result = true;
		}
		if(str.equals("localhost")){
			result = true;
		}
		return result;
	}

	/**
	 * 验证电话号码的有效性手机，或者固话
	 * @param phoneNumber
	 * @return 正确返回true， 不符合格式返回false
	 */
	public boolean checkPhoneNumber(String phoneNumber){
		boolean result =false;
		final String mobile ="[1]{1}[3,5,8,6]{1}[0-9]{9}";
		final String tel = "[0]{1}[0-9]{2,3}-[0-9]{7,8}";
		if(phoneNumber.matches(tel)||phoneNumber.matches(mobile)){
			result = true;
		}
		return result;
	}
}
