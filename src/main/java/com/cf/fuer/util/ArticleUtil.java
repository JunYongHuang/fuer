package com.cf.fuer.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.dao.IArticleDao;
import com.cf.fuer.model.Article;
import com.cf.fuer.webapp.bean.ArticlesBean;

@Component
public class ArticleUtil {

	private static IArticleDao articleDao;

	private static int fetchSize = 4;
	
	/**
	 * 缓存首页的文章列表
	 */
	private static Map<String,ArticlesBean> articleMap = new ConcurrentHashMap<String,ArticlesBean>();
	
	/**
	 * 获取某一类型的文章列表，用于首页显示，如果缓存中没有，更新缓存
	 */
	private static ArticlesBean getArticleBean(int type){
		String typeStr = String.valueOf(type);
		ArticlesBean articlesBean = articleMap.get(typeStr);
		if(articlesBean == null){
			refreshArticleBean(type);
			articlesBean = articleMap.get(typeStr);
		}
		return articlesBean;
	}
	
	/**
	 * 更新某一类型的文章列表缓存。
	 */
	public static void refreshArticleBean(int type){
		List<Article> articles = articleDao.getArticleByType(type, fetchSize);
		ArticlesBean bean = new ArticlesBean(articles);
		articleMap.put(String.valueOf(type),bean);
	}
	
	/**
	 * 游戏介绍
	 */
	public static ArticlesBean getGameAbouts(){
		return getArticleBean(CommonConstants.ARTICLE_TYPE.ABOUT.getType());
	}
	
	/**
	 * 新手指南
	 */
	public static ArticlesBean getBeginnerGuides(){
		return getArticleBean(CommonConstants.ARTICLE_TYPE.GUIDE.getType());
	}
	
	/**
	 * 游戏新闻
	 */
	public static ArticlesBean getGameNews(){
		return getArticleBean(CommonConstants.ARTICLE_TYPE.NEWS.getType());
	}
	/**
	 * 游戏公告 
	 */
	public static ArticlesBean getGameNotices(){
		return getArticleBean(CommonConstants.ARTICLE_TYPE.NOTICE.getType());
	}	

	@Autowired
	public void setArticleDao(IArticleDao articleDao) {
		ArticleUtil.articleDao = articleDao;
	}

}
