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
import com.cf.fuer.manager.ISpreadManager;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.SpreadUser;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.ControllerUtil;
import com.cf.fuer.util.DateUtil;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.controller.BaseSearchController;

@Controller
public class UserFromController extends BaseSearchController {
	
	Log log = LogFactory.getLog(getClass());

	private static final String SEARCH_VIEW = "account/adusers";

	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private ISpreadManager spreadManager;

	public UserFromController() {
		setEntityName("user");
		setSearchView(SEARCH_VIEW);
	}

	@RequestMapping("/account/myspread.php")
	public String mySpread(HttpServletRequest request){
		Long userId = AccessHelper.getLoginUser().getId();
		SpreadUser spreadUser = spreadManager.getSpreadUserWithData(userId);
		request.setAttribute("mySpread", spreadUser);
		return "account/myspread";
	}

	@RequestMapping("/account/adusers.php")
	public String adusers(HttpServletRequest request) {
		long role = AccessHelper.getLoginUser().getRole();
		if(role < 100 && role != CommonConstants.ROLE.ROLE_ADMIN && role != CommonConstants.ROLE.ROLE_SPREAD){
			ControllerUtil.throwExceptionIfNull(null, "页面不存在！");
		}
		log.info("用户"+AccessHelper.getLoginUser().getUsername()+"[role:"+role+"]查看用户!");
		return super.execute(request);
	}

	@Override
	protected void search(SearchBean searchBean) {
		userManager.search(searchBean);
	}

	@Override
	public void addCriteria(SearchBean searchBean, StringBuilder sb, HttpServletRequest request) {
		searchBean.setObjectsPerPage(15);

		request.setAttribute("dateFrom", request.getParameter("dateFrom"));
		request.setAttribute("dateTo", request.getParameter("dateTo"));
		
		String dateFromStr = ServletRequestUtils.getStringParameter(request, "dateFrom", null);
		Date dateFrom = DateUtil.parse(dateFromStr);
		if(dateFrom != null){
			sb.append(" and o.regdate>=:regFrom");
			searchBean.getParamMap().put("regFrom", dateFrom.getTime()/1000);
		}
		
		String dateToStr = ServletRequestUtils.getStringParameter(request, "dateTo", null);
		Date dateTo = DateUtil.parse(dateToStr);
		if(dateTo != null){
			sb.append(" and o.regdate<=:regTo");
			searchBean.getParamMap().put("regTo", dateTo.getTime()/1000);
		}
		User loginUser = AccessHelper.getLoginUser();
		int loginRole = loginUser.getRole();
		if(CommonConstants.ROLE.ROLE_SPREAD == loginRole){
			sb.append(" and o.sourcefrom= '"+getAddressCode(loginUser.getId())+"'");//仅查询该来源的用户
		}
		if(loginRole >= 100){
			sb.append(" and o.ufrom= "+loginRole);//仅查询该来源的用户
		}
	}
	
	private String getAddressCode(Long userId){
		SpreadUser spreadUser = spreadManager.getSpreadUser(userId);
		return "-" + spreadUser.getAddressCode();
	}

}
