package com.cf.fuer.webapp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.manager.IArticleManager;
import com.cf.fuer.util.ControllerUtil;
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
public class ArticleSearchController extends BaseSearchController{

	private static final String SEARCH_VIEW="admin/articleList";
	
	@Autowired
	private IArticleManager articleManager;

	public ArticleSearchController(){
		setEntityName("article");
		setSearchView(SEARCH_VIEW);
	}
	@RequestMapping("/admin/articleList.php")
	public String articleList(HttpServletRequest request){
		return super.execute(request);
	}
	
	@RequestMapping("/admin/articleRemove.php")
	public String articleRemove(HttpServletRequest request){
		long id = ServletRequestUtils.getLongParameter(request, "id", -1L);
		if(id != -1){
			articleManager.remove(id);
			ControllerUtil.setSuccessMessage(request, "文章删除成功!");
		}
		return "redirect:articleList.php";
	}

	@Override
	protected void search(SearchBean searchBean) {
		articleManager.search(searchBean);
	}

	@Override
	public void addCriteria(SearchBean searchBean, StringBuilder sb,
			HttpServletRequest request) {
		searchBean.setObjectsPerPage(20);
		addEqualInteger(searchBean, sb, request, "type", "type");
		addLikeString(searchBean, sb, request, "title", "title");
		//addLikeString(searchBean, sb, request, "accountNum", "accountNum");
	}
	
	
}
