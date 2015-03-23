package com.cf.fuer.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.model.User;

@Component
public class UserValidator extends BaseValidator {

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 修改密码验证
	 */
	public void validatePwd(User loginUser, String oldPwd, String newPwd, String rePwd) throws ValidationException {
		if (isBlank(oldPwd)) {
			throw new ValidationException("oldPwd", "旧密码不能为空");
		}
		String encoderPwd = passwordEncoder.encodePassword(oldPwd, loginUser.getSalt());
		if (!encoderPwd.equals(loginUser.getPassword())) {
			throw new ValidationException("oldPwd", "旧密码输入错误!");
		}
		if (isBlank(newPwd)) {
			throw new ValidationException("newPwd", "新密码不能为空");
		}

		if (!isValideLenthStr(newPwd, 6, 20) || newPwd.equalsIgnoreCase(loginUser.getUsername())) {
			throw new ValidationException("newPwd", "新密码必须由6-20个字符组成,区分大小写,不能和用户名一样");
		}

		if (!newPwd.equals(rePwd)) {
			throw new ValidationException("rePwd", "两次密码输入不一致");
		}
	}

	/**
	 * 更新验证
	 */
	public void validateUpdate(User user) throws ValidationException {
		// String name = user.getName();
		// if (!isCorrectName(name)) {
		// throw new ValidationException("name", "姓名必须为2～4个汉字");
		// }

		String idcard = user.getIdcard();
		if (!isBlank(idcard)) {
			if (!isCorrectIdcard(idcard)) {
				throw new ValidationException("idcard", "请填写15或18位真实身证号！");
			}

			if (!notExist("user", "idcard", idcard, user.getId())) {
				throw new ValidationException("idcard", "此身份证号已被使用,请重新输入!");
			}
		}

		String email = user.getEmail();
		if (isBlank(email)) {
			throw new ValidationException("email", "邮箱地址不能为空!");
		}
		if (!isCorrectEmail(email)) {
			throw new ValidationException("email", "邮箱地址输入不正确!");
		}
		if (!notExist("user", "email", email,user.getId())) {
			throw new ValidationException("email", "此邮箱已被使用,请重新输入!");
		}

		if (isBlank(user.getName())) {
			throw new ValidationException("name", "请填写昵称!");
		}
	}

	/**
	 * 验证注册时的用户名
	 */
	public void validateRegUsername(String username) throws ValidationException {
		if (!isCorrectUserName(username)) {
			throw new ValidationException("username", "用户名必须由字母a-z、数字0-9、下划线组成长度3-20,不区分大小写");
		}
		if (!notExist("user", "username", username)) {
			throw new ValidationException("username", "此用户名已被使用,请重新输入!");
		}
	}

	/**
	 * 验证注册时的用户名
	 */
	public void validateRegPwd(String password, String username) throws ValidationException {
		if (!isValideLenthStr(password, 6, 20) || password.equalsIgnoreCase(username)) {
			throw new ValidationException("password", "密码必须由6-20个字符组成，区分大小写，不能和用户名一样");
		}
	}

	/**
	 * 验证注册时的身份证号
	 */
	public void validateRegIdcard(String idcard) throws ValidationException {
		if (!isBlank(idcard)) {
			if (!isCorrectIdcard(idcard)) {
				throw new ValidationException("idcard", "请填写15或18位真实身证号！");
			}

			if (!notExist("user", "idcard", idcard)) {
				throw new ValidationException("idcard", "此身份证号已被使用,请重新输入!");
			}
		}
	}

	/**
	 * 验证注册邮箱
	 */
	public void validateRegEmail(String email) throws ValidationException {
		if (!isCorrectEmail(email)) {
			throw new ValidationException("email", "邮箱地址输入不正确!");
		}
		if (!notExist("user", "email", email)) {
			throw new ValidationException("email", "此邮箱已被使用,请重新输入!");
		}
	}

	/**
	 * 验证姓名
	 */
	public void validateName(String name) throws ValidationException {
		if (!isCorrectName(name)) {
			throw new ValidationException("name", "姓名必须为2～4个汉字");
		}
	}

	/**
	 * 验证注册
	 */
	public void validateRegister(User user) throws ValidationException {
		String username = user.getUsername();
		validateRegUsername(username);

		String password = user.getPassword();
		validateRegPwd(password, username);
		if (!password.equals(user.getConfirmPassword())) {
			throw new ValidationException("confirmPassword", "两次密码输入不一致");
		}
		validateRegEmail(user.getEmail());
		// validateName(user.getName());
		validateRegIdcard(user.getIdcard());
	}

	/**
	 * 身份证号码是否正确
	 */
	private boolean isCorrectIdcard(String idcard) {
		int length = idcard.length();
		// 地区检验及长度校验31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"
		String regex = "^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|(71)|(8[12])|(91))((\\d{15})|(\\d{12}))[0-9Xx]$";
		Matcher m = Pattern.compile(regex).matcher(idcard);
		if (!m.matches()) {
			return false;
		}
		int year = 0;
		String birthday = "";
		if (length == 15) {
			year = Integer.valueOf(idcard.substring(6, 8)) + 1900;
			birthday = idcard.substring(6, 12);
		} else {
			year = Integer.valueOf(idcard.substring(6, 10));
			birthday = idcard.substring(6, 14);
		}

		// 出生日期
		if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {// 闰年
			regex = "(19|20)[0-9]{2}(((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1]))|((04|06|09|11)(0[1-9]|[1-2][0-9]|30))|(02(0[1-9]|[1-2][0-9])))";
		} else {
			regex = "(19|20)[0-9]{2}(((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1]))|((04|06|09|11)(0[1-9]|[1-2][0-9]|30))|(02(0[1-9]|1[1-9]|2[0-8])))";
		}
		if (!birthday.matches(regex)) {
			return false;
		}
		// 校验位验证
		if (length == 18) {
			// 系数
			int[] coefficients = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
			int total = 0;
			for (int i = 0; i < 17; i++) {
				int num = Integer.valueOf(idcard.substring(i, i + 1));
				total = total + num * coefficients[i];
			}
			String[] codes = new String[] { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
			String checkCode = codes[total % 11];
			if (!idcard.toLowerCase().endsWith(checkCode)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证用户名由字母a-z、数字0-9、下划线组成长度3-20,不区分大小写
	 * 
	 * @param username
	 * @return 正确返回true，不符合格式返回false
	 */
	private boolean isCorrectUserName(String username) {
		if (isBlank(username)) {
			return false;
		}
		String regex = "[0-9[a-z][A-Z]_]{3,20}";
		Matcher m = Pattern.compile(regex).matcher(username);
		return m.matches();
	}

	/**
	 * 验证姓名是否为2～4个汉字
	 * 
	 * @param name
	 * @return 正确返回true，不符合格式返回false
	 */
	public boolean isCorrectName(String name) {
		if (isBlank(name)) {
			return false;
		}
		String regex = "[\\u4E00-\\u9FA5]{1,15}";
		Matcher m = Pattern.compile(regex).matcher(name);
		return m.matches();
	}

	/**
	 * 验证地址是否包含2～4个汉字
	 * 
	 * @param address
	 * @return 正确返回true，不符合格式返回false
	 */
	public boolean isCorrectAddress(String address) {
		if (isBlank(address)) {
			return false;
		}
		String regex = ".*[\\u4E00-\\u9FA5]{2,4}.*";
		Matcher m = Pattern.compile(regex).matcher(address);
		return m.matches();
	}

	/**
	 * 验证email地址的有效性
	 * 
	 * @param mail
	 * @return 正确返回true， 不符合格式返回false
	 */
	public boolean isCorrectEmail(String email) {
		if (isBlank(email)) {
			return false;
		}
		if (!isValideLenthStr(email, 1, 120)) {
			return false;
		}
		final String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		return m.find();
	}

}
