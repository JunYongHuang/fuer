package com.cf.fuer.webapp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IArticleManager;
import com.cf.fuer.model.Article;
import com.cf.fuer.util.ControllerUtil;

/**
 * 创建或更新文章
 */
@SessionAttributes("articleForm")
@Controller
@RequestMapping("/admin/articlePub.php")
public class ArticleSaveController {
	@Autowired
	private IArticleManager articleManager;

	/**
	 * 文章FORM
	 */
	public static final String ARTICLE_FORM = "articleForm";

	private static final String FORM_VIEW = "admin/articlePub";

	private static final String REDIRECT_URL = "redirect:/admin/articlePub.php";

	@RequestMapping(method = RequestMethod.GET)
	public String prepareForm(HttpServletRequest request, ModelMap model) {
		long id = ServletRequestUtils.getLongParameter(request, "id", -1L);
		Article article;
		if (id == -1) {
			article = new Article();
		} else {
			article = articleManager.get(id);
		}
		model.addAttribute(ARTICLE_FORM, article);
		return FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doSave(@ModelAttribute(ARTICLE_FORM) Article article, BindingResult result, Model model, HttpServletRequest request, SessionStatus status) {

		Long id = article.getId();
		try {
			boolean create = article.getId() == null || article.getId()<=0  ;
			if(create){
				id = articleManager.create(article);
				ControllerUtil.setSuccessMessage(request, "文章发布成功!");
			}else{
				id = articleManager.update(article);
				ControllerUtil.setSuccessMessage(request, "文章更新成功!");
			}
			// emailManager.sendEmailAfterReg(account.getUser().getEmail(),
			// account.getAccountName(), password);
			status.setComplete();
		} catch (ValidationException ve) {
			ControllerUtil.rejectErrors(ve, result);
			model.addAttribute("errors", result.getFieldErrors());
			return FORM_VIEW;
		}
		return REDIRECT_URL + "?id="+id;
	}
}
