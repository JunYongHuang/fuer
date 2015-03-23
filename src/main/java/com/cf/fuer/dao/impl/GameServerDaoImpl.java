package com.cf.fuer.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.dao.IGameServerDao;
import com.cf.fuer.model.GameRole;
import com.cf.fuer.model.GameServer;
import com.cf.fuer.webapp.bean.SearchBean;

@Repository("gameServerDao")
public class GameServerDaoImpl implements IGameServerDao {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void search(SearchBean searchBean) {
		List<GameServer> list = search(searchBean.getQuerySql(), searchBean.getParamMap());
		searchBean.setList(list);
		int totalCount = jdbcTemplate.queryForInt(searchBean.getTotalCountSql(), searchBean.getParamMap());
		searchBean.setFullListSize(totalCount);
	}

	private List<GameServer> search(String sql, Map<String, Object> paramMap) {
		List<GameServer> list = jdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<GameServer>(GameServer.class));
		return list;
	}

	@Override
	public List<GameServer> allGameServers() {
		String sql = "SELECT * FROM gameServer ORDER BY id DESC";
		return search(sql, new HashMap<String, Object>());
	}

	@Override
	public Long createGameServer(GameServer gameServer) {
		final String sql = "insert into gameServer (code,name,ip,port,maxCount,createDate,type) values(:code,:name,:ip,:port,:maxCount,now(),:type)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", gameServer.getCode());
		paramMap.put("name", gameServer.getName());
		paramMap.put("ip", gameServer.getIp());
		paramMap.put("port", gameServer.getPort());
		paramMap.put("maxCount", gameServer.getMaxCount());
		paramMap.put("type", gameServer.getType());
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public GameServer get(Long id) {
		String sql = "select * from gameServer where id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		GameServer gameServer = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<GameServer>(GameServer.class));
		return gameServer;
	}

	@Override
	public GameServer getByCode(String code) {
		String sql = "select * from gameServer where code=:code";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", code);
		GameServer gameServer = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<GameServer>(GameServer.class));
		return gameServer;
	}

	@Override
	public String getMinLoadServerCode() {
		String sql = "select code from gameServer where (currentCount/maxCount)=(select min(currentCount/maxCount) from gameServer) limit 1";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String gameServerCode = jdbcTemplate.queryForObject(sql, paramMap, String.class);
		return gameServerCode;
	}

	@Override
	public Long updateGameServer(GameServer gameServer) {
		String sql = "UPDATE gameServer SET code=:code,name=:name,ip=:ip,port=:port,maxCount=:maxCount,type=:type WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", gameServer.getCode());
		paramMap.put("name", gameServer.getName());
		paramMap.put("ip", gameServer.getIp());
		paramMap.put("port", gameServer.getPort());
		paramMap.put("maxCount", gameServer.getMaxCount());
		paramMap.put("type", gameServer.getType());
		paramMap.put("id", gameServer.getId());
		jdbcTemplate.update(sql, paramMap);
		return gameServer.getId();
	}

	@Override
	public boolean isNotFull(String serverCode) {
		try {
			String sql = "select count(*) from gameServer where code=:code AND currentCount<maxCount";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("code", serverCode);
			// 能查询到说明没有满员
			int count = jdbcTemplate.queryForInt(sql, paramMap);
			return count > 0;
		} catch (Exception e) {
			// 用户没有登陆服务器,返回空
			log.error("验证服务器是否满员出错!", e);
		}
		return false;
	}

	@Override
	public boolean isRegInServer(String serverCode, String username) {
		try {
			String sql = "select count(*) from gamerole where username=:username AND serverCode=:code ";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("code", serverCode);
			paramMap.put("username", username);
			// 能查询到说明用户已创建角色
			int count = jdbcTemplate.queryForInt(sql, paramMap);
			return count > 0;
		} catch (Exception e) {
			// log.error("查询用户角色出错!");
		}
		return false;
	}

	@Override
	public List<String> getAllUserServers(String username) {
		List<String> servers = new ArrayList<String>();
		try {
			String sql = "select serverCode from gamerole where username=:username order by loginDate desc";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", username);
			servers = jdbcTemplate.queryForList(sql, paramMap, String.class);
		} catch (Exception e) {
			// 用户没有登陆服务器,返回空
			// log.error("获取用户所有注册过的服务器出错!", e);
		}
		return servers;
	}

	@Override
	public String getManagerName(String username, String serverCode) {
		String managerName = "";
		try {
			String sql = "select managerName from gamerole where username=:username AND serverCode=:serverCode limit 1 ";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", username);
			paramMap.put("serverCode", serverCode);
			managerName = jdbcTemplate.queryForObject(sql, paramMap, String.class);
		} catch (Exception e) {
			// 用户没有登陆服务器,返回空
			// log.error("查找用户最近服务器出错!", e);
		}
		return managerName;
	}

	@Override
	public List<GameRole> getAllUserRoles(String username) {
		List<GameRole> userRoles = new ArrayList<GameRole>();
		try {
			String sql = "select * from gamerole where username=:username order by creationDate desc";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", username);
			userRoles = jdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<GameRole>(GameRole.class));
		} catch (Exception e) {
			// 用户没有登陆服务器,返回空
			// log.error("获取用户所有注册过的服务器出错!", e);
		}
		return userRoles;
	}

	/**
	 * 新增一个角色注册,更新用户最近登陆服务器列表,同时更新注册人数
	 * 
	 * @param username
	 *            用户名
	 * @param serverCode
	 *            游戏服务器编号
	 * @param registerCount
	 *            当前游戏服务器注册的人数
	 */
	@Override
	public void insertUserServer(String username, String serverCode, Integer managerId, String managerName, Integer registerCount) {
		final String sql = "insert into gamerole(username,serverCode,managerId, managerName,creationDate,loginDate) values(:username,:serverCode,:managerId,:managerName,:creationDate,:loginDate)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("serverCode", serverCode);
		paramMap.put("managerId", managerId);
		paramMap.put("managerName", managerName);
		Date creationDate = new Date();
		paramMap.put("creationDate", creationDate);
		paramMap.put("loginDate", creationDate);
		jdbcTemplate.update(sql, paramMap);

		// 更新服务器人数
		updateRegCount(serverCode, registerCount);
		// 人数大于3000时自动开放下一个维护服务器
		// if (registerCount >= 3000) {
		// openNextServer(serverCode);
		// }
	}

	/**
	 * 更改下一个维护服务器为正常服务器
	 */
	private void openNextServer(String serverCode) {
		String sql = "UPDATE gameserver SET type=" + CommonConstants.SERVER_TYPE.COMMON + " WHERE id=(SELECT min(a.id) FROM (SELECT * FROM gameserver) AS a WHERE type=" + CommonConstants.SERVER_TYPE.MAINTAIN + " AND id>(SELECT b.id FROM (SELECT * FROM gameserver)  AS b WHERE code=:code))";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", serverCode);
		jdbcTemplate.update(sql, paramMap);
	}

	@Override
	public void updateUserServer(String username, String serverCode) {
		// 更新
		String sql = " update gamerole set loginDate=:loginDate where username=:username AND serverCode=:serverCode ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginDate", new Date());
		paramMap.put("username", username);
		paramMap.put("serverCode", serverCode);
		jdbcTemplate.update(sql, paramMap);
	}

	/**
	 * 更新服务器的注册人数.
	 */
	private void updateRegCount(String serverCode, Integer registerCount) {
		if (registerCount == null || registerCount == 0) {
			return;
		}
		// 更新总数
		String sql = "UPDATE gameServer SET currentCount=:registerCount WHERE code=:code";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("registerCount", registerCount);
		paramMap.put("code", serverCode);
		jdbcTemplate.update(sql, paramMap);
	}
	
	/**
	 * 新增一条到达人数
	 * 
	 * @param username
	 *            用户名
	 * @param serverCode
	 *            服务器编码
	 */
	@Override
	public void insertArrive(String username, String serverCode) {
		final String sql = "insert into arrive(username,serverCode) values(:username,:serverCode)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("serverCode", serverCode);
		jdbcTemplate.update(sql, paramMap);
	}

}
