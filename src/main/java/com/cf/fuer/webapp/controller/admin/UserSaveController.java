package com.cf.fuer.webapp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.cf.fuer.CommonConstants;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.ControllerUtil;

/**
 * 创建或更新用户
 */
@SessionAttributes("userForm")
@Controller
@RequestMapping("/admin/userUpdate.php")
public class UserSaveController {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserManager userManager;

	/**
	 * 文章FORM
	 */
	public static final String USER_FORM = "userForm";

	private static final String FORM_VIEW = "admin/userUpdate";

	private static final String REDIRECT_URL = "redirect:/admin/userUpdate.php";

	@RequestMapping(method = RequestMethod.GET)
	public String prepareForm(HttpServletRequest request, ModelMap model) {
		long id = ServletRequestUtils.getLongParameter(request, "id", -1L);
		User user;
		if (id == -1) {
			user = new User();
			user.setRole(CommonConstants.ROLE.ROLE_TEST);
		} else {
			user = userManager.get(id);
		}
		model.addAttribute(USER_FORM, user);
		return FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doSave(@ModelAttribute(USER_FORM) User user, BindingResult result, Model model, HttpServletRequest request, SessionStatus status) {

		Long id = user.getId();
		try {
			boolean create = user.getId() == null || user.getId() <= 0;
			if (create) {
				id = userManager.add(user);
				log.info("管理员["+AccessHelper.getLoginUser().getUsername()+"]创建用户" + user);
				ControllerUtil.setSuccessMessage(request, "用户创建成功!");
			} else {
				id = userManager.updateWithEmail(user);
				log.info("管理员["+AccessHelper.getLoginUser().getUsername()+"]更新用户" + user);
				ControllerUtil.setSuccessMessage(request, "用户更新成功!");
			}
			status.setComplete();
		} catch (ValidationException ve) {
			ControllerUtil.rejectErrors(ve, result);
			model.addAttribute("errors", result.getFieldErrors());
			return FORM_VIEW;
		}
		return REDIRECT_URL + "?id=" + id;
	}
}
