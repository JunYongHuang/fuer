package com.cf.fuer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.cf.fuer.dao.IUserDao;
import com.cf.fuer.exception.ObjectNotFoundException;
import com.cf.fuer.model.User;
import com.cf.fuer.util.DateUtil;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.bean.SpreadUser;

@Repository
public class UserDaoImpl implements IUserDao, UserDetailsService {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Long createUser(User user) {
		String sql = "insert into user (username,password,salt,name,email,idcard,regdate,role,ufrom,sourcefrom) values (:username,:password,:salt,:name,:email,:idcard,:regdate,:role,:ufrom,:sourcefrom)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", user.getUsername());
		paramMap.put("password", user.getPassword());
		paramMap.put("salt", user.getSalt());
		paramMap.put("name", user.getName());
		paramMap.put("email", user.getEmail());
		paramMap.put("idcard", user.getIdcard());
		paramMap.put("regdate", System.currentTimeMillis()/1000);
		paramMap.put("role", user.getRole());
		paramMap.put("ufrom", user.getFrom());
		paramMap.put("sourcefrom", user.getSourceFrom());
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
		return keyHolder.getKey().longValue();
	}
	
	/**
	 * 创建第三方登陆用户
	 */
	@Override
	public Long createThirdUser(String username, int gender,int loginType, String openId, String name,String password, String salt,String sourcefrom) {
		String sql = "insert into user (username,password,salt,name,sex,regdate,sourcefrom) values (:username,:password,:salt,:name,:sex,:regdate,:sourcefrom)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("password", password);
		paramMap.put("salt", salt);
		paramMap.put("name", name);
		paramMap.put("sex", gender);
		paramMap.put("regdate", System.currentTimeMillis()/1000);		
		paramMap.put("sourcefrom", sourcefrom);
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
		insertThirdUser(username, loginType, openId);
		return keyHolder.getKey().longValue();
	}
	
	/**
	 * 插入第三方用户到数据库.
	 */
	private int insertThirdUser(String username, int loginType, String openId) {
		String sql = "insert into third_user (username,openId,loginType,regdate) values (:username,:openId,:loginType,:regdate)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("openId", openId);
		paramMap.put("loginType", loginType);
		paramMap.put("regdate", System.currentTimeMillis()/1000);			
		return jdbcTemplate.update(sql, paramMap);
	}

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
	@Override
	public Long createVoteUser(String name, String email, String password, String salt) {
		String sql = "insert into user (username,password,salt,name,email,regdate) values (:username,:password,:salt,:name,:email,:regdate)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", email);
		paramMap.put("password", password);
		paramMap.put("salt", salt);
		paramMap.put("name", name);
		paramMap.put("email", email);
		paramMap.put("regdate", System.currentTimeMillis()/1000);
		
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
		return keyHolder.getKey().longValue();
	}

	/**
	 * {@inheritDoc}
	 */
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		if(usernameOrEmail == null || usernameOrEmail.isEmpty()){
			throw new UsernameNotFoundException("user '" + usernameOrEmail + "' not found...");
		}
		String sql = "select * from user where username=:usernameOrEmail OR email=:usernameOrEmail";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("usernameOrEmail", usernameOrEmail);
		User user = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<User>(User.class));
		if (user.getId() == null) {
			throw new UsernameNotFoundException("user '" + usernameOrEmail + "' not found...");
		} else {
			return (UserDetails) user;
		}
	}

	public int savePassword(long id, String newPassword) {
		String sql = "UPDATE  user SET password=:password WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("password", newPassword);
		paramMap.put("id", id);
		return jdbcTemplate.update(sql, paramMap);
	}

	public Long updateUser(User user) {
		String sql = "UPDATE user SET name=:name, idcard=:idcard, pic=:pic,sex=:sex,email=:email WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", user.getName());
		paramMap.put("idcard", user.getIdcard());
		paramMap.put("pic", user.getPic());
		paramMap.put("sex", user.getSex());
		paramMap.put("id", user.getId());
		paramMap.put("email", user.getEmail());
		jdbcTemplate.update(sql, paramMap);
		return user.getId();
	}
	
	@Override
	public Long updateUserWithEmail(User user) {
		String sql = "UPDATE user SET name=:name, idcard=:idcard, pic=:pic,sex=:sex,email=:email,role=:role WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", user.getName());
		paramMap.put("idcard", user.getIdcard());
		paramMap.put("pic", user.getPic());
		paramMap.put("sex", user.getSex());
		paramMap.put("email", user.getEmail());
		paramMap.put("role", user.getRole());
		paramMap.put("id", user.getId());
		jdbcTemplate.update(sql, paramMap);
		return user.getId();
	}
	
	@Override
	public boolean canRegister(String ip) {
		try {
			String sql = "select count(*) from user where loginIp=:ip ";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ip", ip);
			// 能查询到说明没有满员
			int count = jdbcTemplate.queryForInt(sql, paramMap);
			return count == 0;
		} catch (Exception e) {
			// 用户没有登陆服务器,返回空
		}
		return false;
	}

	/**
	 * 更新最后登陆时间及IP
	 */
	@Override
	public int updateLogin(String loginIp, String username) {
		String sql = "UPDATE user SET loginIp=:loginIp, lastLoginTime=:lastLoginTime WHERE username=:username";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginIp", loginIp);
		paramMap.put("lastLoginTime", System.currentTimeMillis()/1000);
		paramMap.put("username", username);
		return jdbcTemplate.update(sql, paramMap);
	}

	@Override
	public User getByEmail(String email) {
		try {
			if(email == null || email.isEmpty()){
				return null;
			}
			String sql = "select * from user where email=:email";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("email", email);
			return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<User>(User.class));
		} catch (Exception e) {

		}
		return null;
	}
	
	private List<User> search(String sql, Map<String, Object> paramMap) {
		List<User> list = jdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<User>(User.class));
		return list;
	}
	
	@Override
	public void search(SearchBean searchBean) {
		List<User> list = search(searchBean.getQuerySql(), searchBean.getParamMap());
		searchBean.setList(list);
		int totalCount = jdbcTemplate.queryForInt(searchBean.getTotalCountSql(), searchBean.getParamMap());
		searchBean.setFullListSize(totalCount);
	}
	
	@Override
	public List<User> all() {
		String sql = "SELECT * FROM user ORDER BY id ASC";
		return search(sql, new HashMap<String, Object>());
	}
	
	@Override
	public int changePwd(long id, String newPassword, String salt) {
		String sql = "UPDATE  user SET password=:password,salt=:salt WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("password", newPassword);
		paramMap.put("salt", salt);
		paramMap.put("id", id);
		return jdbcTemplate.update(sql, paramMap);
	}

	@Override
	public void updateUserRole(long id, int role) {
		String sql = "UPDATE user SET role=:role WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("role", role);
		paramMap.put("id", id);
		jdbcTemplate.update(sql, paramMap);
	}
	
	@Override
	public User get(Long id) {
		try{
			String sql = "select * from user where id=:id";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<User>(User.class));
		}catch (Exception e) {
			throw new ObjectNotFoundException();
		}
	}
	
	/**
	 * 插入用户token到数据库,通常不会调用,当中央服务器无法连接时使用.
	 */
	@Override
	public int insertToken(String username, int age, String token,String serverCode) {
		String sql = "insert into token (username,age,token,server,expireTime) values (:username,:age,:token,:server,:expireTime)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("age", age);
		paramMap.put("token", token);
		paramMap.put("server", serverCode);
		paramMap.put("expireTime", DateUtil.getTokenExpireDate());		
		return jdbcTemplate.update(sql, paramMap);
	}
	
	public List<SpreadUser> listSpreadUsers(){
		String sql = "select a.ticketId,u.username,u.email from awardticket a inner join testuser u on a.id=u.id order by u.id asc ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return jdbcTemplate.query(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<SpreadUser>(SpreadUser.class));
	}

}
