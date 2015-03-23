package com.cf.fuer.manager;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.model.Article;
import com.cf.fuer.webapp.bean.SearchBean;

public interface IArticleManager {

	/**
	 * 创建文章
	 * 
	 * @param article
	 *            文章
	 * @return 文章的ID
	 * @throws ValidationException
	 */
	Long create(Article article) throws ValidationException;

	/**
	 * 更新文章信息
	 * 
	 * @param article
	 *            文章
	 * @return 文章的ID
	 * @throws ValidationException
	 */
	Long update(Article article) throws ValidationException;

	Article get(Long id);

	void search(SearchBean searchBean);

	/**
	 * 更新文章的查看次数
	 */
	void updateClick(Long id);
	
	void remove(Long id);

}
