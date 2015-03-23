package com.cf.fuer.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.properties.SortOrderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.CommonConstants.ARTICLE_TYPE;
import com.cf.fuer.manager.IArticleManager;
import com.cf.fuer.model.Article;
import com.cf.fuer.util.ControllerUtil;
import com.cf.fuer.webapp.bean.SearchBean;

/**
 * 查询与修改account信息 继承自BaseSearchController.
 * 
 * 需要写一个无参构造方法给父类set实体类名称，和将要跳转的页面 需要重写父类search，和addCriteria方法
 * 需要写自己的调用方法，返回调用父类的execute方法，传递一个request参数
 * 
 * @author ZhouChaoLiang
 * @date 2012-02-23
 * 
 */
@Controller
public class ArticleController extends BaseSearchController {

	private static final String SEARCH_VIEW = "article";

	@Autowired
	private IArticleManager articleManager;

	public ArticleController() {
		setEntityName("article");
		setSearchView(SEARCH_VIEW);
	}

	@RequestMapping(value = { "/news/*.php", "/guide/*.php", "/about/*.php","/notice/*.php" })
	public String officalNews(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		ARTICLE_TYPE article_type = ARTICLE_TYPE.fromUrl(servletPath);
		ControllerUtil.throwExceptionIfNull(article_type, "类型不存在");
		request.setAttribute("type", article_type.getType());
		request.setAttribute("articleType", article_type);
		long id = ServletRequestUtils.getLongParameter(request, "id", -1L);
		if (id != -1) {
			Article article = articleManager.get(id);
			ControllerUtil.throwExceptionIfNull(article, "未找到相关信息");
			articleManager.updateClick(id);
			request.setAttribute("article", article);
			return SEARCH_VIEW;
		} else {
			return super.execute(request);
		}
	}

	@Override
	protected void search(SearchBean searchBean) {
		articleManager.search(searchBean);
	}

	@Override
	public void addCriteria(SearchBean searchBean, StringBuilder sb, HttpServletRequest request) {
		// addEqualInteger(searchBean, sb, request, "t", "type");
		sb.append(" and o.type=" + request.getAttribute("type"));
		// searchBean.getParamMap().put(htmlName, fieldValue);
		// addLikeString(searchBean, sb, request, "accountName", "accountName");
		// addLikeString(searchBean, sb, request, "accountNum", "accountNum");
	}

	/**
	 * 设置初始的排序参数和排序方式。不设置也没关系。 设置方式如下：
	 * <P>
	 * searchBean.setSortCriterion("username");
	 * <P>
	 * searchBean.setSortDirection(SortOrderEnum.ASCENDING);
	 */
	@Override
	protected void setDefaultValue(SearchBean searchBean) {
		searchBean.setSortCriterion("top desc, createDate");
		searchBean.setSortDirection(SortOrderEnum.DESCENDING);
	}

}
