package com.cf.fuer.model;

import java.util.Date;

/**
 * 新闻文章
 * 
 * @author sunke
 * 
 */
public class Article {

	private Long id;

	/**
	 * 文章标题
	 */
	private String title;

	/**
	 * 类别,详见CommonConstants.ARTICLE_TYPE
	 */
	private Integer type = -1;

	/**
	 * 文章来源
	 */
	private String source = "本站";

	/**
	 * 作者
	 */
	private String author ="管理员";

	/**
	 * 关键字
	 */
	private String keywords;

	/**
	 * 文章提要
	 */
	private String summary;

	/**
	 * 文章内容
	 */
	private String content;

	/**
	 * 浏览次数
	 */
	private int click;

	/**
	 * 置顶
	 */
	private boolean top;

	/**
	 * 创建日期
	 */
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
