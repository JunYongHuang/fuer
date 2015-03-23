package com.cf.fuer.dao;

import java.util.List;

import com.cf.fuer.model.Article;
import com.cf.fuer.webapp.bean.SearchBean;

public interface IArticleDao {

	Long createArticle(Article article);

	Article get(Long id);

	Long updateArticle(Article article);

	void search(SearchBean searchBean);

	/**
	 * 更新文章的查看次数
	 */
	void updateClick(Long id);

	/**
	 * 根据类别获取文章列表,主要用于首页展示
	 * 
	 * @param type
	 *            文章类型
	 * @param fetchSize
	 *            获取条数
	 * @return 文章列表
	 */
	List<Article> getArticleByType(int type, int fetchSize);

	void remove(Long id);

}
