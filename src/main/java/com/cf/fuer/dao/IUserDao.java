package com.cf.fuer.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.cf.fuer.model.User;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.bean.SpreadUser;

/**
 * User Data Access Object (GenericDao) interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IUserDao {

	/**
	 * Gets users information based on login name.
	 * 
	 * @param username
	 *            the user's username
	 * @return userDetails populated userDetails object
	 * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
	 *             thrown when user not found in database
	 */
	@Transactional
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	/**
	 * Saves a user's information.
	 * 
	 * @param user
	 *            the object to be saved
	 * @return the persisted User object
	 */
	Long createUser(User user);

	/**
	 * 更新用户
	 */
	Long updateUser(User user);

	/**
	 * 保存新密码.
	 * 
	 * @param uid
	 * @param newPassword
	 * @return
	 */
	int savePassword(long uid, String newPassword);

	User getByEmail(String email);

	/**
	 * 投票后注册用户
	 * 
	 * @param name
	 *            名字
	 * @param email
	 *            email
	 * @param password
	 *            密码
	 * @param salt
	 *            加密盐
	 * @return 返回用户ID
	 */
	Long createVoteUser(String name, String email, String password, String salt);

	List<User> all();

	int changePwd(long id, String newPassword, String salt);

	void search(SearchBean searchBean);

	/**
	 * 更改用户类型
	 */
	void updateUserRole(long id, int role);

	User get(Long id);

	/**
	 * 更新用户带EMAIL,用于管理员更新用户信息
	 */
	Long updateUserWithEmail(User user);

	/**
	 * 更新最后登陆时间及IP
	 */
	int updateLogin(String loginIp, String username);

	/**
	 * 插入用户token到数据库,通常不会调用,当中央服务器无法连接时使用.
	 */
	int insertToken(String username, int age, String token, String serverCode);

	/**
	 * 创建第三方登陆用户
	 */
	Long createThirdUser(String username, int gender, int loginType, String openId, String name, String password, String salt, String from);

	/**
	 * 同一IP不可注册多个小号
	 */
	boolean canRegister(String ip);

	List<SpreadUser> listSpreadUsers();
}
