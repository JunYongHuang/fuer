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

import com.cf.fuer.dao.IArticleDao;
import com.cf.fuer.model.Article;
import com.cf.fuer.webapp.bean.SearchBean;

@Repository("articleDao")
public class ArticleDaoImpl implements IArticleDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void search(SearchBean searchBean) {
		List<Article> list = search(searchBean.getQuerySql(), searchBean.getParamMap());
		searchBean.setList(list);
		int totalCount = jdbcTemplate.queryForInt(searchBean.getTotalCountSql(), searchBean.getParamMap());
		searchBean.setFullListSize(totalCount);
	}

	private List<Article> search(String sql, Map<String, Object> paramMap) {
		List<Article> list = jdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<Article>(Article.class));
		return list;
	}

	/**
	 * 根据类别获取文章列表,主要用于首页展示
	 * 
	 * @param type
	 *            文章类型
	 * @param fetchSize
	 *            获取条数
	 * @return 文章列表
	 */
	@Override
	public List<Article> getArticleByType(int type, int fetchSize) {
		String sql = "SELECT * FROM article WHERE type=:type ORDER BY top DESC, createDate DESC limit :fetchSize";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("fetchSize", fetchSize);
		return search(sql, paramMap);
	}

	@Override
	public Long createArticle(final Article article) {
		final String sql = "insert into article (title,type,source,author,keywords,summary,content,top,createDate) values(:title,:type,:source,:author,:keywords,:summary,:content,:top,now())";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", article.getTitle());
		paramMap.put("type", article.getType());
		paramMap.put("source", article.getSource());
		paramMap.put("author", article.getAuthor());
		paramMap.put("keywords", article.getKeywords());
		paramMap.put("summary", article.getSummary());
		paramMap.put("content", article.getContent());
		paramMap.put("top", article.isTop());
		jdbcTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public Article get(Long id) {
		try{
			String sql = "select * from article where id=:id";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(paramMap), new BeanPropertyRowMapper<Article>(Article.class));
		}catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public void updateClick(Long id) {
		String sql = "UPDATE article SET click=click+1 WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		jdbcTemplate.update(sql, paramMap);
	}

	@Override
	public Long updateArticle(Article article) {
		String sql = "UPDATE article SET title=:title,type=:type,source=:source,author=:author,keywords=:keywords,summary=:summary,content=:content,top=:top WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", article.getTitle());
		paramMap.put("type", article.getType());
		paramMap.put("source", article.getSource());
		paramMap.put("author", article.getAuthor());
		paramMap.put("keywords", article.getKeywords());
		paramMap.put("summary", article.getSummary());
		paramMap.put("content", article.getContent());
		paramMap.put("top", article.isTop());
		paramMap.put("id", article.getId());
		jdbcTemplate.update(sql, paramMap);
		return article.getId();
	}

	@Override
	public void remove(Long id) {
		String sql = "DELETE FROM article WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		jdbcTemplate.update(sql, paramMap);
	}

}
