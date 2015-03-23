package com.cf.fuer.webapp.bean;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cf.fuer.model.Article;

/**
 * 用于首页展示的Bean
 */
public class ArticlesBean {
	
	public ArticlesBean(List<Article> articles) {
		super();
		this.articles = articles;
		for (Article article : articles) {
			String content = article.getContent();
			Document doc = Jsoup.parse(content);
			Elements images = doc.select("img[src]");
			if(!images.isEmpty()){
				imageUrl = images.first().attr("src");
				imageArticleId = article.getId();
				title = article.getTitle();
				break;
			}
		}
	}

	/**
	 * 文章列表
	 */
	private List<Article> articles;
	
	/**
	 * 文章列表中的第一张图片的URL
	 */
	private String imageUrl;
	/**
	 * 文章标题
	 */
	private String title;
	
	/**
	 * 文章列表中第一个含有图片的ID
	 */
	private Long imageArticleId;

	public List<Article> getArticles() {
		return articles;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Long getImageArticleId() {
		return imageArticleId;
	}

	public String getTitle() {
		return title;
	}

}
