package com.cf.fuer.webapp.controller.admin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.util.DateUtil;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.controller.BaseSearchController;

@Controller
public class UserSearchController extends BaseSearchController {

	private static final String SEARCH_VIEW = "admin/userList";

	@Autowired
	private IUserManager userManager;

	public UserSearchController() {
		setEntityName("user");
		setSearchView(SEARCH_VIEW);
	}

	@RequestMapping("/admin/userList.php")
	public String articleList(HttpServletRequest request) {
		return super.execute(request);
	}

	@Override
	protected void search(SearchBean searchBean) {
		userManager.search(searchBean);
	}

	@Override
	public void addCriteria(SearchBean searchBean, StringBuilder sb, HttpServletRequest request) {
		searchBean.setObjectsPerPage(20);
		addLikeString(searchBean, sb, request, "uid", "username");
		addLikeString(searchBean, sb, request, "uname", "name");
		addLikeString(searchBean, sb, request, "uemail", "email");
		addLikeString(searchBean, sb, request, "uidcard", "idcard");
		addEqualInteger(searchBean, sb, request, "role", "role");
		
		request.setAttribute("loginDateFrom", request.getParameter("loginDateFrom"));
		request.setAttribute("loginDateTo", request.getParameter("loginDateTo"));
		
		String loginDateFromStr = ServletRequestUtils.getStringParameter(request, "loginDateFrom", null);
		Date loginDateFrom = DateUtil.parse(loginDateFromStr);
		if(loginDateFrom != null){
			sb.append(" and o.lastLoginTime>=:loginFrom");
			searchBean.getParamMap().put("loginFrom", loginDateFrom.getTime()/1000);
		}
		
		String loginDateToStr = ServletRequestUtils.getStringParameter(request, "loginDateTo", null);
		Date loginDateTo = DateUtil.parse(loginDateToStr);
		if(loginDateTo != null){
			sb.append(" and o.lastLoginTime<=:loginTo");
			searchBean.getParamMap().put("loginTo", loginDateTo.getTime()/1000);
		}
		

		request.setAttribute("regDateFrom", request.getParameter("regDateFrom"));
		request.setAttribute("regDateTo", request.getParameter("regDateTo"));
		
		String dateFromStr = ServletRequestUtils.getStringParameter(request, "regDateFrom", null);
		Date dateFrom = DateUtil.parse(dateFromStr);
		if(dateFrom != null){
			sb.append(" and o.regdate>=:regFrom");
			searchBean.getParamMap().put("regFrom", dateFrom.getTime()/1000);
		}
		
		String dateToStr = ServletRequestUtils.getStringParameter(request, "regDateTo", null);
		Date dateTo = DateUtil.parse(dateToStr);
		if(dateTo != null){
			sb.append(" and o.regdate<=:regTo");
			searchBean.getParamMap().put("regTo", dateTo.getTime()/1000);
		}

		addExactString(searchBean, sb, request, "ufrom", "ufrom");//用户来源
		sb.append(" and o.role!=1 ");//排除管理员
	}

}
