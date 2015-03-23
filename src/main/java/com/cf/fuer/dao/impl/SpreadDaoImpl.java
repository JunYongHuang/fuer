package com.cf.fuer.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cf.fuer.dao.ISpreadDao;
import com.cf.fuer.model.SpreadUser;
import com.cf.fuer.webapp.bean.SearchBean;

/**
 * Created by IntelliJ IDEA. User: kangmor Date: 13-7-4 Time: 下午2:51
 */
@Repository("spreadDao")
public class SpreadDaoImpl implements ISpreadDao {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 保存点一点活动的IP
	 * 
	 * @param ip
	 *            IP地址
	 * @param serverCode
	 *            服务器代码
	 * @param managerId
	 *            经理人ID
	 * @return
	 */
	public int saveIP(String ip, String serverCode, String managerId) {
		String sql = "INSERT into spread_ip (ip,serverCode,managerId) values (:ip,:serverCode,:managerId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ip", ip);
		paramMap.put("serverCode", serverCode);
		paramMap.put("managerId", managerId);
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
		return keyHolder.getKey().intValue();
	}

	public boolean ipExist(String ip) {
		String sql = "SELECT count(id) FROM spread_ip WHERE ip=:ip";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ip", ip);
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql, paramMap);
		} catch (Exception e) {
			// ip doesn't exist
		}
		if (count > 0)
			return true;
		else
			return false;
	}

	/**
	 * 保存推广人员
	 * 
	 * @param userId
	 *            用户ID
	 * @param name
	 *            推广人
	 * @param addressCode
	 *            推广代码
	 * @return
	 */
	@Override
	public boolean saveSpreadUser(Long userId, String name, String addressCode) {
		String sql = "INSERT INTO spreaduser (userId, name, addressCode) VALUES (:userId, :name, :addressCode);";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("name", name);
		paramMap.put("addressCode", addressCode);
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap));
		return true;
	}

	@Override
	public void updateSpreadAddressCode(Long userId, String addressCode) {
		String sql = "UPDATE spreaduser SET addressCode=:addressCode WHERE userId=:userId ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("addressCode", addressCode);
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap));
	}

	/**
	 * 增加推广点击次数
	 * 
	 * @param addressCode
	 *            推广代码
	 */
	@Override
	public void addSpreadClick(String addressCode) {
		String sql = "UPDATE  spreaduser SET clickCount=clickCount+1 WHERE addressCode=:addressCode ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("addressCode", addressCode);
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap));
	}

	@Override
	public SpreadUser getSpreadUser(Long userId) {
		try {
			String sql = "SELECT * FROM spreaduser WHERE userId=:userId";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<SpreadUser>(SpreadUser.class));
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public SpreadUser getSpreadUserWithData(Long userId) {
		try {
			String sql = "SELECT * FROM spreaduser WHERE userId=:userId";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			SpreadUser spreadUser = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<SpreadUser>(SpreadUser.class));
			int userCount = countSpreadUser(userId, null, null);
			spreadUser.setUserCount(userCount);

			long chargeAmount = sumSpreadCharge(userId, null, null);
			spreadUser.setChargeAmount(chargeAmount);

			return spreadUser;
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 统计推广用户数
	 */
	private Integer countSpreadUser(Long userId, Date dateFrom, Date dateTo) {
		String sql = " SELECT COUNT(u.id) FROM user u INNER JOIN spreaduser s ON u.sourcefrom=CONCAT('-',s.addressCode) WHERE s.userId=:userId ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		if (dateFrom != null) {
			sql = sql + " AND u.regdate>=" + dateFrom.getTime() / 1000;
		}
		if (dateTo != null) {
			sql = sql + " AND u.regdate<=" + dateTo.getTime() / 1000;
		}
		return jdbcTemplate.queryForInt(sql, paramMap);
	}
	/**
	 * 统计推广充值
	 * @param userId
	 */
	private Long sumSpreadCharge(Long userId, Date dateFrom, Date dateTo){
		String sql = " SELECT SUM(payAmount) FROM charge c LEFT JOIN user u ON c.user=u.username LEFT JOIN spreaduser s ON u.sourcefrom=CONCAT('-',s.addressCode) WHERE s.userId=:userId ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		if (dateFrom != null) {
			sql = sql + " AND DATE(c.chargeDate)>=:dateFrom ";
			paramMap.put("dateFrom", dateFrom);
		}
		if (dateTo != null) {
			sql = sql + " AND DATE(c.chargeDate)<=:dateTo ";
			paramMap.put("dateTo", dateTo);
		}
		return jdbcTemplate.queryForLong(sql, paramMap);
	}

	@Override
	public void search(SearchBean searchBean, Date dateFrom, Date dateTo) {
		List<SpreadUser> list = search(searchBean.getQuerySql(), searchBean.getParamMap());
		for (SpreadUser spreadUser : list) {
			int userCount = countSpreadUser(spreadUser.getUserId(), dateFrom, dateTo);
			long chargeAmount = sumSpreadCharge(spreadUser.getUserId(), dateFrom, dateTo);
			spreadUser.setUserCount(userCount);
			spreadUser.setChargeAmount(chargeAmount);
		}
		searchBean.setList(list);
		int totalCount = jdbcTemplate.queryForInt(searchBean.getTotalCountSql(), searchBean.getParamMap());
		searchBean.setFullListSize(totalCount);
	}

	private List<SpreadUser> search(String sql, Map<String, Object> paramMap) {
		List<SpreadUser> list = jdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<SpreadUser>(SpreadUser.class));
		return list;
	}
}
