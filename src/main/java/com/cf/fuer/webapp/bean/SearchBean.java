package com.cf.fuer.webapp.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

public class SearchBean implements PaginatedList {
	String totalCountSql;
	String querySql;
	private int fullListSize;
	private List list;
	private int objectsPerPage = 20;
	private int pageNumber = 1;
	private String sortCriterion = "id";
	// used for dislayTag, Consistency with 'sortDir'
	private SortOrderEnum sortDirection = SortOrderEnum.DESCENDING;
	// used in hql, Consistency with 'sortDirection'
	private String sortDir = "desc";
	// optional
	private String searchId;
	/**
	 * 为了hql的安全，保存参数值到Dao层利用hibernate的query设置参数。
	 * <p>
	 * 键：h1l参数代名：hql参数值
	 * 
	 */
	private Map<String, Object> paramMap = new HashMap<String, Object>();

	public int getFullListSize() {
		return fullListSize;
	}

	public List getList() {
		return list;
	}

	public int getObjectsPerPage() {
		return objectsPerPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public String getSortCriterion() {
		return sortCriterion;
	}

	public SortOrderEnum getSortDirection() {
		return sortDirection;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setFullListSize(int fullListSize) {
		this.fullListSize = fullListSize;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	public void setSortDirection(SortOrderEnum sortDirection) {
		this.sortDirection = sortDirection;
		this.sortDir = SortOrderEnum.ASCENDING.equals(sortDirection) ? "asc" : "desc";
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getSortDir() {
		return sortDir;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public String getTotalCountSql() {
		return totalCountSql;
	}

	public void setTotalCountSql(String totalCountSql) {
		this.totalCountSql = totalCountSql;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

}
