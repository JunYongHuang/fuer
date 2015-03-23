package com.cf.fuer.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.fuer.dao.IArticleDao;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IArticleManager;
import com.cf.fuer.model.Article;
import com.cf.fuer.util.ArticleUtil;
import com.cf.fuer.validator.ArticleValidator;
import com.cf.fuer.webapp.bean.SearchBean;

@Service
public class ArticleManagerImpl implements IArticleManager {

	@Autowired
	private IArticleDao articleDao;

	@Autowired
	private ArticleValidator articleValidator;

	@Override
	public Long create(Article article) throws ValidationException {
		articleValidator.validateCreate(article);
		Long articleId = articleDao.createArticle(article);
		//刷新首页缓存
		ArticleUtil.refreshArticleBean(article.getType());
		return articleId;
	}

	@Override
	public Long update(Article article) throws ValidationException {
		articleValidator.validateUpdate(article);
		Long articleId =  articleDao.updateArticle(article);
		//刷新首页缓存
		ArticleUtil.refreshArticleBean(article.getType());
		return articleId;
	}

	@Override
	public Article get(Long id) {
		return articleDao.get(id);
	}

	@Override
	public void search(SearchBean searchBean) {
		articleDao.search(searchBean);
	}

	@Override
	public void updateClick(Long id) {
		articleDao.updateClick(id);
	}

	@Override
	public void remove(Long id) {
		int type = get(id).getType();
		articleDao.remove(id);
		//刷新首页缓存
		ArticleUtil.refreshArticleBean(type);
	}

}
