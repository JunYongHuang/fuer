package com.cf.fuer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cf.fuer.CommonConstants;

/**
 * This class represents the basic "user" object in AppFuse that allows for
 * authentication and user management. It implements Acegi Security's
 * UserDetails interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Updated by
 *         Dan Kibler (dan@getrolling.com) Extended to implement Acegi
 *         UserDetails interface by David Carter david@carter.net
 */

public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 3832626162173359411L;

	private Long id;
	/**
	 * 用户名
	 */
	private String username; // required
	/**
	 * 密码
	 */
	private String password; // required
	private String confirmPassword;
	/**
	 * 6位随机生成的加密盐
	 */
	private String salt;
	/**
	 * 用户类别,0为普通 1为管理员
	 */
	private int role = CommonConstants.ROLE.ROLE_USER;

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 真实姓名
	 */
	private String name;
	/**
	 * 用户头像
	 */
	private String pic;
	private String email; // required; unique
	private String phoneNumber;
	private String idcard;

	/**
	 * 性别
	 */
	private int sex = 1;

	private boolean enabled = true;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;

	/**
	 * 注册日期,秒需要*1000
	 */
	private Long regdate;
	/**
	 * 上次登陆日期,秒需要*1000
	 */
	private Long lastLoginTime;

	/**
	 * 上次登陆的IP.
	 */
	private String loginIp;

	/**
	 * 用户注册来源
	 */
	private long from;
	
	/**
	 * 发放礼包平台来源
	 */
	private String sourceFrom = "";

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public User() {
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 * @return true if account is still active
	 */

	public boolean isAccountNonExpired() {
		return !isAccountExpired();
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 * @return false if account is locked
	 */

	public boolean isAccountNonLocked() {
		return !isAccountLocked();
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 * @return true if credentials haven't expired
	 */

	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthority() {
			private static final long serialVersionUID = 4009006921283119582L;

			public String getAuthority() {
				return "ROLE_USER";
			}
		});
		if (role == 1) {
			authorities.add(new GrantedAuthority() {
				private static final long serialVersionUID = 4009006921283119582L;

				public String getAuthority() {
					return "ROLE_ADMIN";
				}
			});
		}
		return authorities;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public void setRegdate(Long regdate) {
		this.regdate = regdate;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getRegdate() {
		return regdate;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public Date getLastLoginDate() {
		if (lastLoginTime != null) {
			return new Date(lastLoginTime * 1000);
		}
		return null;
	}

	public Date getRegDate() {
		return new Date(regdate * 1000);
	}

	int age = 0;

	/**
	 * 获取用户的年龄,年龄由身份证号得出,如果身份证号为空或不正确返回16岁
	 */
	public int getAge() {
		// 生成的测试账号,返回20
		// if(role == 8){
		return 20;
		// }
		// if(age == 0){
		// age = DateUtil.getAge(idcard);
		// if(age == 0){
		// age = 16;
		// }
		// }
		// return age;
	}

	/**
	 * 是否是普通用户
	 */
	public boolean isCommon() {
		return role == CommonConstants.ROLE.ROLE_USER || role > 100;
	}

	/**
	 * 是否是测试用户
	 */
	public boolean isTest() {// 8为测试账号,之后删除
		return role == CommonConstants.ROLE.ROLE_TEST;// || role == 8;
	}

	/**
	 * 是否是管理员
	 */
	public boolean isAdmin() {
		return role == CommonConstants.ROLE.ROLE_ADMIN;
	}

	/**
	 * 是否是黑名单用户
	 */
	public boolean isBlack() {
		return role == CommonConstants.ROLE.ROLE_BLACK;
	}
	/**
	 * 是否是推广人员
	 */
	public boolean isSpread(){
		return role == CommonConstants.ROLE.ROLE_SPREAD;
	}

	@Override
	public String toString() {
		return "[username:" + username + " email:" + email + " role:" + role + " name:" + name + " idcard:" + idcard + "]";
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

}
