package com.cf.fuer.webapp.controller.user;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.CommonConstants;
import com.cf.fuer.manager.IChargeManager;
import com.cf.fuer.manager.ISpreadManager;
import com.cf.fuer.model.SpreadUser;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.ControllerUtil;
import com.cf.fuer.util.DateUtil;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.controller.BaseSearchController;

@Controller
public class SpreadChargeController extends BaseSearchController {
	
	Log log = LogFactory.getLog(getClass());

	private static final String SEARCH_VIEW = "account/adcharges";

	@Autowired
	private IChargeManager chargeManager;
	
	@Autowired
	private ISpreadManager spreadManager;

	public SpreadChargeController() {
		setEntityName("charge");
		setSearchView(SEARCH_VIEW);
	}

	@RequestMapping("/account/adcharges.php")
	public String articleList(HttpServletRequest request) {
		long role = AccessHelper.getLoginUser().getRole();
		if(role < 100 && role != CommonConstants.ROLE.ROLE_ADMIN && role != CommonConstants.ROLE.ROLE_SPREAD){
			ControllerUtil.throwExceptionIfNull(null, "页面不存在！");
		}
		return super.execute(request);
	}

	@Override
	protected void search(SearchBean searchBean) {
		chargeManager.search(searchBean);
	}

	@Override
	public void addCriteria(SearchBean searchBean, StringBuilder sb, HttpServletRequest request) {
		searchBean.setObjectsPerPage(15);

		request.setAttribute("dateFrom", request.getParameter("dateFrom"));
		request.setAttribute("dateTo", request.getParameter("dateTo"));
		
		String dateFromStr = ServletRequestUtils.getStringParameter(request, "dateFrom", null);
		Date dateFrom = DateUtil.parse(dateFromStr);
		if(dateFrom != null){
			sb.append(" and o.createDate>=:regFrom");
			searchBean.getParamMap().put("regFrom", dateFrom);
		}
		
		String dateToStr = ServletRequestUtils.getStringParameter(request, "dateTo", null);
		Date dateTo = DateUtil.parse(dateToStr);
		if(dateTo != null){
			sb.append(" and o.createDate<=:regTo");
			searchBean.getParamMap().put("regTo", dateTo);
		}
		User loginUser = AccessHelper.getLoginUser();
		int loginRole = loginUser.getRole();
		if(CommonConstants.ROLE.ROLE_SPREAD == loginRole){
			sb.append(" and u.sourcefrom= '"+getAddressCode(loginUser.getId())+"'");//仅查询该来源的用户
		}
		if(loginRole >= 100){
			sb.append(" and u.ufrom= "+loginRole);//仅查询该来源的用户
		}
		addEqualInteger(searchBean, sb, request, "status", "status");
		addExactString(searchBean, sb, request, "gameServer", "gameServer");
	}


	@Override
	protected String initQueryHQL() {
		return "select o.* from charge o left join user u on o.user=u.username where 1=1 ";
	}
	
	@Override
	protected String initCountHQL() {
		return "select count(*) from charge o left join user u on o.user=u.username where 1=1 ";
	}
	
	private String getAddressCode(Long userId){
		SpreadUser spreadUser = spreadManager.getSpreadUser(userId);
		return "-" + spreadUser.getAddressCode();
	}

}
