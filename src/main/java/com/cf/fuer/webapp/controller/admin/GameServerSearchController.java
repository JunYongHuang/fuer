package com.cf.fuer.webapp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.manager.IGameServerManager;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.controller.BaseSearchController;

@Controller
public class GameServerSearchController extends BaseSearchController {

	private static final String SEARCH_VIEW = "admin/gameServerList";

	@Autowired
	private IGameServerManager gameServerManager;

	public GameServerSearchController() {
		setEntityName("gameServer");
		setSearchView(SEARCH_VIEW);
	}

	@RequestMapping("/admin/serverList.php")
	public String articleList(HttpServletRequest request) {
		return super.execute(request);
	}

	@Override
	protected void search(SearchBean searchBean) {
		gameServerManager.search(searchBean);
	}

	@Override
	public void addCriteria(SearchBean searchBean, StringBuilder sb, HttpServletRequest request) {
		searchBean.setObjectsPerPage(20);
		// addEqualInteger(searchBean, sb, request, "type", "type");
		// addLikeString(searchBean, sb, request, "title", "title");
		// addLikeString(searchBean, sb, request, "accountNum", "accountNum");
	}

}
