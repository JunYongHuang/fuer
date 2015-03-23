package com.cf.fuer.webapp.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.ControllerUtil;

/**
 * 注册
 */
@Controller
public class AdminController {
	
	Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserManager userManager;
	
	@RequestMapping("/admin/index.php")
	public String index() {
		return "admin/index";
	}
	
	@RequestMapping(value="/admin/changePassword.php",method = RequestMethod.GET)
	public String changePassword() {
		return "admin/changePassword";
	}
	
	@RequestMapping(value="/admin/changePassword.php",method = RequestMethod.POST)
	public String changePasswordSave(HttpServletRequest request) throws ServletRequestBindingException {
		String oldPwd = ServletRequestUtils.getStringParameter(request, "oldPwd","");
		String newPwd = ServletRequestUtils.getStringParameter(request, "newPwd","");
		String rePwd = ServletRequestUtils.getStringParameter(request, "rePwd","");
		try {
			User loginUser = AccessHelper.getLoginUser();
			userManager.savePassword(loginUser, oldPwd, newPwd, rePwd);
			log.info("用户["+loginUser.getUsername() + "]重置密码");
			AccessHelper.login(userManager.loadUserByUsername(loginUser.getUsername()));
			ControllerUtil.setSuccessMessage(request, "密码修改成功!");
		} catch (ValidationException ve) {
			ControllerUtil.setErrorMessage(request, ve.getDefaultMessage());
		}
		return "redirect:/admin/changePassword.php";
	}
	
	@RequestMapping(value="/admin/sendSpreadEmail.php",method = RequestMethod.GET)
	public void sendSpreadEmail(HttpServletResponse response) {
		//List<User> users = userManager.loadUserByUsername("");
		try{
			userManager.sendSpreadEmail();
		}catch (Exception e) {
			
		}
	}

}
