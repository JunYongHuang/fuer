package com.cf.fuer.webapp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.manager.IVoteManager;
import com.cf.fuer.webapp.bean.SearchBean;
import com.cf.fuer.webapp.controller.BaseSearchController;

@Controller
public class VoteSearchController extends BaseSearchController {

	private static final String SEARCH_VIEW = "admin/voteList";

	@Autowired
	private IVoteManager voteManager;

	public VoteSearchController() {
		setEntityName("bl_vote_register");
		setSearchView(SEARCH_VIEW);
	}

	@RequestMapping("/admin/voteList.php")
	public String articleList(HttpServletRequest request) {
		return super.execute(request);
	}

	@Override
	protected void search(SearchBean searchBean) {
		voteManager.search(searchBean);
	}

	@Override
	public void addCriteria(SearchBean searchBean, StringBuilder sb, HttpServletRequest request) {
		searchBean.setObjectsPerPage(20);
		addLikeString(searchBean, sb, request, "uname", "uname");
		addLikeString(searchBean, sb, request, "uemail", "email");
		addLikeString(searchBean, sb, request, "umobile", "mobile");
		addLikeString(searchBean, sb, request, "uaddr", "addr");
	}

}
