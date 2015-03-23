package com.cf.fuer.webapp.controller.admin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.manager.ISpreadManager;
import com.cf.fuer.util.DateUtil;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.controller.BaseSearchController;

@Controller
public class SpreadListController extends BaseSearchController {
	
	Log log = LogFactory.getLog(getClass());

	private static final String SEARCH_VIEW = "admin/spreadList";
	
	@Autowired
	private ISpreadManager spreadManager;

	public SpreadListController() {
		setEntityName("spreaduser");
		setSearchView(SEARCH_VIEW);
	}

	@RequestMapping("/admin/spreadList.php")
	public String execute(HttpServletRequest request) {
		if (needSearch(request)) {
			SearchBean searchBean = getSearchBean(request);
			search(searchBean, request);
			afterSearch(request, searchBean);
		}
		return searchView;
	}

	protected void search(SearchBean searchBean, HttpServletRequest request) {
		String dateFromStr = ServletRequestUtils.getStringParameter(request, "dateFrom", null);
		Date dateFrom = DateUtil.parse(dateFromStr);
		String dateToStr = ServletRequestUtils.getStringParameter(request, "dateTo", null);
		Date dateTo = DateUtil.parse(dateToStr);
		request.setAttribute("dateFrom", dateFromStr);
		request.setAttribute("dateTo", dateToStr);
		spreadManager.search(searchBean,dateFrom,dateTo);
	}

	@Override
	public void addCriteria(SearchBean searchBean, StringBuilder sb, HttpServletRequest request) {
		searchBean.setObjectsPerPage(20);
	}

	@Override
	protected void search(SearchBean searchBean) {
		
	}

}
