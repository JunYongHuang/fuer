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
import org.springframework.stereotype.Repository;

import com.cf.fuer.dao.IVoteDao;
import com.cf.fuer.model.VoteItem;
import com.cf.fuer.model.VoteReg;
import com.cf.fuer.webapp.bean.SearchBean;

@Repository("voteDao")
public class VoteDaoImpl implements IVoteDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private List<VoteReg> searchVoteRegs(String sql, Map<String, Object> paramMap) {
		List<VoteReg> list = jdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<VoteReg>(VoteReg.class));
		return list;
	}
	
	@Override
	public void search(SearchBean searchBean) {
		List<VoteReg> list = searchVoteRegs(searchBean.getQuerySql(), searchBean.getParamMap());
		searchBean.setList(list);
		int totalCount = jdbcTemplate.queryForInt(searchBean.getTotalCountSql(), searchBean.getParamMap());
		searchBean.setFullListSize(totalCount);
	}
	
	private List<VoteItem> search(String sql, Map<String, Object> paramMap) {
		List<VoteItem> list = jdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<VoteItem>(VoteItem.class));
		return list;
	}

	@Override
	public List<VoteItem> allVoteItems() {
		String sql = "SELECT * FROM bl_vote_item ORDER BY img ASC";
		return search(sql, new HashMap<String, Object>());
	}
	
	@Override
	public int totalVote() {
		try{
			String sql = "select sum(voteTotal) from bl_vote_item";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			int count = jdbcTemplate.queryForInt(sql, paramMap);
			return count;
		}catch (Exception e) {
			//用户没有登陆服务器,返回空
		}
		return 0;
	}
	
	@Override
	public void updateVoteCount(Long itemId) {
		String sql = "UPDATE bl_vote_item SET voteTotal=voteTotal+1 WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", itemId);
		jdbcTemplate.update(sql, paramMap);
	}
	
	/**
	 * 插入投票注册
	 */
	@Override
	public Long createVoteReg(String name, String email, String mobile, String addr) {
		final String sql = "insert into bl_vote_register (uname,email,mobile,addr,regdate) values(:uname,:email,:mobile,:addr,:regdate)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uname", name);
		paramMap.put("email", email);
		paramMap.put("mobile", mobile);
		paramMap.put("addr", addr);
		paramMap.put("regdate", System.currentTimeMillis()/1000);
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
		return keyHolder.getKey().longValue();
	}
	
	/**
	 * 插入投票注册
	 */
	@Override
	public Long createVoteIp(String ip,Long uid) {
		if(uid == null){
			uid = 0L;
		}
		final String sql = "insert into bl_vote_ip (ip,uid,regdate,subject_id) values(:ip,:uid,:regdate,1)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ip", ip);
		paramMap.put("uid", uid);
		paramMap.put("regdate", System.currentTimeMillis()/1000);
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
		return keyHolder.getKey().longValue();
	}

}
