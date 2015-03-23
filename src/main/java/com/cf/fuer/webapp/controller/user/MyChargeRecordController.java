package com.cf.fuer.webapp.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.manager.IArticleManager;
import com.cf.fuer.manager.IChargeManager;
import com.cf.fuer.model.Charge;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.controller.BaseSearchController;

/**
 * 查询与修改account信息   继承自BaseSearchController.
 *
 * 需要写一个无参构造方法给父类set实体类名称，和将要跳转的页面
 * 需要重写父类search，和addCriteria方法
 * 需要写自己的调用方法，返回调用父类的execute方法，传递一个request参数
 * @author ZhouChaoLiang
 * @date 2012-02-23
 *
 */
@Controller
public class MyChargeRecordController extends BaseSearchController{

	@Autowired
	private IChargeManager chargeManager;
	
	@Value("#{jdbcSettings['app.url']}")
	private String appUrl;

	private static final String SEARCH_VIEW="account/chargeRecord";

	public MyChargeRecordController(){
		setEntityName("charge");
		setSearchView(SEARCH_VIEW);
	}
	
	@RequestMapping("/account/chargeRecord.php")
	public String chargeRecord(HttpServletRequest request) {
		return super.execute(request);
	}
	
	@RequestMapping("/account/orderDetails.php")
	public String orderDetails(HttpServletRequest request) {
		long orderId = ServletRequestUtils.getLongParameter(request, "id", -1L);
		Charge charge = chargeManager.getByOrderId(orderId);
		request.setAttribute("detail", true);
		request.setAttribute("order", charge);
		request.setAttribute("appUrl", appUrl);
		return "account/chargeRecord";
	}

	@Override
	protected void search(SearchBean searchBean) {
		chargeManager.search(searchBean);
	}

	@Override
	public void addCriteria(SearchBean searchBean, StringBuilder sb,
			HttpServletRequest request) {
		searchBean.setObjectsPerPage(15);
		//addEqualInteger(searchBean, sb, request, "type", "type");
		//addLikeString(searchBean, sb, request, "title", "title");
		sb.append(" and o.payUser='"+AccessHelper.getLoginUser().getUsername()+"' ");
		//addLikeString(searchBean, sb, request, "accountNum", "accountNum");
	}
	
	
}
