package com.cf.fuer.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cf.fuer.dao.IPlatformDao;
import com.cf.fuer.model.Platform;

@Repository
public class PlatformDaoImpl implements IPlatformDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Platform get(long id) {
		try {
			String sql = "select * from platform where id=:id";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<Platform>(Platform.class));
		} catch (Exception e) {

		}
		return null;
	}

}
