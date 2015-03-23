package com.cf.fuer.webapp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.manager.IChargeManager;
import com.cf.fuer.model.Charge;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.controller.BaseSearchController;


@Controller
public class ChargeRecordSearchController extends BaseSearchController{

	@Autowired
	private IChargeManager chargeManager;

	private static final String SEARCH_VIEW="admin/chargeRecordList";

	public ChargeRecordSearchController(){
		setEntityName("charge");
		setSearchView(SEARCH_VIEW);
	}
	
	@RequestMapping("/admin/chargeRecordList.php")
	public String chargeRecord(HttpServletRequest request) {
		return super.execute(request);
	}
	
	@RequestMapping("/admin/chargeRecordDetail.php")
	public String orderDetails(HttpServletRequest request) {
		long orderId = ServletRequestUtils.getLongParameter(request, "id", -1L);
		Charge charge = chargeManager.getByOrderId(orderId);
		request.setAttribute("order", charge);
		return "admin/chargeRecordDetail";
	}

	@Override
	protected void search(SearchBean searchBean) {
		chargeManager.search(searchBean);
	}

	@Override
	public void addCriteria(SearchBean searchBean, StringBuilder sb,
			HttpServletRequest request) {
		//searchBean.setObjectsPerPage(15);
		addEqualInteger(searchBean, sb, request, "status", "status");
		addLikeString(searchBean, sb, request, "orderId", "orderId");
		addLikeString(searchBean, sb, request, "payUser", "payUser");
		addLikeString(searchBean, sb, request, "managerName", "managerName");
		addExactString(searchBean, sb, request, "gameServer", "gameServer");
	}
	
	
}
