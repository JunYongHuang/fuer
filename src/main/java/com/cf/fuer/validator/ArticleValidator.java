package com.cf.fuer.validator;

import org.springframework.stereotype.Component;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.model.Article;

@Component
public class ArticleValidator extends BaseValidator{
	/**
	 * 验证更新
	 */
	public void validateUpdate(Article article) throws ValidationException {
		validateSave(article);
	}
		

	/**
	 * 验证创建
	 */
	public void validateCreate(Article article) throws ValidationException {
		validateSave(article);
	}
	
	private void validateSave(Article article) throws ValidationException {
		String title = article.getTitle();
		if (!isValideLenthStr(title, 1, 125)) {
			throw new ValidationException("title", "文章标题长度必须在1到125之间!");
		}
		if(article.getType() == -1){
			throw new ValidationException("type", "请选择文章分类!");
		}
		
		if(!isValideLenthStr(article.getSource(), 1, 125)){
			throw new ValidationException("source", "文章来源长度必须在1到60之间!");
		}

		if(!isValideLenthStr(article.getAuthor(), 1, 15)){
			throw new ValidationException("author", "发布人长度必须在1到15之间!");
		}

		if(!isValideLenthStr(article.getKeywords(), 1, 125)){
			throw new ValidationException("keywords", "关键字长度必须在1到125之间!");
		}

		String summary = article.getSummary();
		if(!isBlank(summary) && !isValideLenthStr(summary, 2, 125)){
			throw new ValidationException("summary", "文章提要长度必须在2到250之间!");
		}
		
		if(isBlank(article.getContent())){
			throw new ValidationException("content", "请填写文章内容!");
		}
	}

}
