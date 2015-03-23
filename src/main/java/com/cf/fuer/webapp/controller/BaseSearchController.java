package com.cf.fuer.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.util.ControllerUtil;
import com.cf.fuer.util.DateUtil;
import com.cf.fuer.webapp.bean.SearchBean;


public abstract class BaseSearchController {
	protected String entityName;
	protected String searchView;

	public String execute(HttpServletRequest request) {
		additionalOperation(request);
		if(request.getParameter("archive")!=null){
			doArchive(request);
		}
		if(request.getParameter("restore")!=null){
			doRestore(request);
		}
		if (needSearch(request)) {
			SearchBean searchBean = getSearchBean(request);
			search(searchBean);
			afterSearch(request, searchBean);
		}
		return searchView;
	}

	private void additionalOperation(HttpServletRequest request){
	}

	protected void doRestore(HttpServletRequest request)  {
	}

	protected void doArchive(HttpServletRequest request){
		
	}

	/**
	 * 搜索完毕之后调用
	 */
	protected void afterSearch(HttpServletRequest request, SearchBean searchBean) {
		request.setAttribute("searchResult", searchBean);
	}

	/**
	 * 什么情况下需要搜索
	 */
	protected boolean needSearch(HttpServletRequest request) {
		//return request.getParameter("clearCriteria") == null;
		return true;
	}

	/**
	 * 一般实现方式如下:
	 * <P>
	 * XXManager.serrch(searchBean);
	 * 
	 */
	protected abstract void search(SearchBean searchBean);

	protected SearchBean getSearchBean(HttpServletRequest request) {
		SearchBean searchBean = new SearchBean();
		setDefaultValue(searchBean);
		ControllerUtil.setParameter(request, searchBean);
		searchBean.setTotalCountSql(buildTotalCountHQL(request, searchBean));
		searchBean.setQuerySql(buildQueryHQL(request, searchBean));
		return searchBean;
	}

	/**
	 * 设置初始的排序参数和排序方式。不设置也没关系。 设置方式如下：
	 * <P>
	 * searchBean.setSortCriterion("username");
	 * <P>
	 * searchBean.setSortDirection(SortOrderEnum.ASCENDING);
	 */
	protected void setDefaultValue(SearchBean searchBean) {

	}

	protected String buildTotalCountHQL(HttpServletRequest request, SearchBean searchBean) {
		StringBuilder sb = new StringBuilder(initCountHQL());
		addCriteria(searchBean, sb, request);
		return sb.toString();
	}

	protected String buildQueryHQL(HttpServletRequest request, SearchBean searchBean) {
		StringBuilder sb = new StringBuilder(initQueryHQL());
		addCriteria(searchBean, sb, request);
		sb.append(initOrderHQL() + searchBean.getSortCriterion());
		sb.append(" " + searchBean.getSortDir());
		int firstResult = (searchBean.getPageNumber() - 1) * searchBean.getObjectsPerPage();
		int maxResult = searchBean.getObjectsPerPage();
		sb.append(" limit " +maxResult + " offset " + firstResult);
		return sb.toString();
	}

	protected String initOrderHQL() {
		return " order by o.";
	}

	protected String initCountHQL() {
		return "select count(*) from " + entityName + " o where 1=1";
	}

	protected String initQueryHQL() {
		return "select * from " + entityName + " o where 1=1";
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setSearchView(String searchView) {
		this.searchView = searchView;
	}

	/**
	 * 添加搜索项的hql
	 */
	public abstract void addCriteria(SearchBean searchBean, StringBuilder sb, HttpServletRequest request);

	/**
	 * 以下为util方法
	 */
	protected void addLikeString(SearchBean searchBean, StringBuilder sb, HttpServletRequest request, String htmlName,
			String property) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute(htmlName, request.getParameter(htmlName));
		String fieldValue = ServletRequestUtils.getStringParameter(request, htmlName, null);
		if (!StringUtils.isEmpty(fieldValue)) {
			sb.append(" and o." + property + " like :" + htmlName);
			searchBean.getParamMap().put(htmlName, "%" + fieldValue + "%");
		}
	}

	// 这个字符串是在某个对象中，这个对象是一个可能为空的属性
	protected void addObjectLikeString(SearchBean searchBean, StringBuilder sb, HttpServletRequest request, String htmlName,
			String property) {
		request.setAttribute(htmlName, request.getParameter(htmlName));
		String fieldValue = ServletRequestUtils.getStringParameter(request, htmlName, null);
		if (!StringUtils.isEmpty(fieldValue)) {
			sb.append(" and o is not null");
			sb.append(" and o." + property + " like :" + htmlName);
			searchBean.getParamMap().put(htmlName, "%" + fieldValue + "%");
		}
	}

	protected void addExactString(SearchBean searchBean, StringBuilder sb, HttpServletRequest request, String htmlName,
			String property) {
		request.setAttribute(htmlName, request.getParameter(htmlName));
		String fieldValue = ServletRequestUtils.getStringParameter(request, htmlName, null);
		// 精确查找的话，空值不需要加入查找条件
		if (!StringUtils.isEmpty(fieldValue)) {
			sb.append(" and o." + property + " = :" + htmlName);
			searchBean.getParamMap().put(htmlName, fieldValue);
		}
	}

	protected void addEqualBoolean(SearchBean searchBean, StringBuilder sb, HttpServletRequest request, String htmlName,
			String property) {
		request.setAttribute(htmlName, request.getParameter(htmlName));
		Boolean bool = null;
		long fieldValue = ServletRequestUtils.getLongParameter(request, htmlName, -1);
		if (fieldValue == 0)
			bool = false;
		else if (fieldValue == 1)
			bool = true;
		if (bool != null) {
			// 精确查找的话，空值不需要加入查找条件
			sb.append(" and o." + property + " = :" + htmlName);
			searchBean.getParamMap().put(htmlName, bool);
		}
	}

	protected void addEqualLong(SearchBean searchBean, StringBuilder sb, HttpServletRequest request, String htmlName,
			String property) {
		this.addEqualLong(searchBean, sb, request, htmlName, property, "o");
	}

	protected void addEqualLong(SearchBean searchBean, StringBuilder sb, HttpServletRequest request, String htmlName,
			String property, String replaceName) {
		request.setAttribute(htmlName, request.getParameter(htmlName));
		long fieldValue = ServletRequestUtils.getLongParameter(request, htmlName, -1);
		if (fieldValue != -1) {
			sb.append(" and " + replaceName + "." + property + "=:" + htmlName);
			searchBean.getParamMap().put(htmlName, fieldValue);
		}
	}

	protected void addEqualInteger(SearchBean searchBean, StringBuilder sb, HttpServletRequest request, String htmlName,
			String property) {
		request.setAttribute(htmlName, request.getParameter(htmlName));
		int fieldValue = ServletRequestUtils.getIntParameter(request, htmlName, -1);
		if (fieldValue != -1) {
			sb.append(" and o." + property + "=:" + htmlName);
			searchBean.getParamMap().put(htmlName, fieldValue);
		}
	}

	protected void addGreatEqualDate(SearchBean searchBean, StringBuilder sb, HttpServletRequest request, String htmlName,
			String property) {
		request.setAttribute(htmlName, request.getParameter(htmlName));
		String fieldValue = ServletRequestUtils.getStringParameter(request, htmlName, null);
		Date date = DateUtil.parse(fieldValue);
		if (date != null) {
			sb.append(" and o." + property + ">=:" + htmlName);
			searchBean.getParamMap().put(htmlName, date);
		}
	}
	
	protected void addAddOneDayDate(SearchBean searchBean, StringBuilder sb, HttpServletRequest request, String htmlName,
			String property) {
		request.setAttribute(htmlName, request.getParameter(htmlName));
		String fieldValue = ServletRequestUtils.getStringParameter(request, htmlName, null);
		Date date = DateUtil.parse(fieldValue);
		if (date != null) {
			date = new Date(date.getTime()+CommonConstants.ONE_DAY_TIME_IN_MILLIS);
			sb.append(" and o." + property + "<:" + htmlName);
			searchBean.getParamMap().put(htmlName, date);
		}
	}
	
	
	
	protected void addEqualDate(SearchBean searchBean, StringBuilder sb, HttpServletRequest request, String htmlName,
			String property) {
		request.setAttribute(htmlName, request.getParameter(htmlName));
		String fieldValue = ServletRequestUtils.getStringParameter(request, htmlName, null);
		Date date = DateUtil.parse(fieldValue);
		if (date != null) {
			sb.append(" and o." + property + "=:" + htmlName);
			searchBean.getParamMap().put(htmlName, date);
		}
	}
	public static void setSuccessMessage(HttpServletRequest request, String message) {
		ControllerUtil.setSuccessMessage(request, message);
	}

}