package com.cf.fuer.webapp.controller.user;

import javax.servlet.http.HttpServletRequest;

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

@Controller
public class AccountController {
    @Autowired
    IUserManager userManager;

	@RequestMapping("/account/myAccount.php")
	public String myAccount() {
		return "account/myAccount";
	}	
	
	@RequestMapping(value="/account/changePassword.php",method = RequestMethod.GET)
	public String changePassword() {
		return "account/changePassword";
	}
	
	@RequestMapping(value="/account/changePassword.php",method = RequestMethod.POST)
	public String changePasswordSave(HttpServletRequest request) throws ServletRequestBindingException {
		String oldPwd = ServletRequestUtils.getStringParameter(request, "oldPwd","");
		String newPwd = ServletRequestUtils.getStringParameter(request, "newPwd","");
		String rePwd = ServletRequestUtils.getStringParameter(request, "rePwd","");
		try {
			User loginUser = AccessHelper.getLoginUser();
			userManager.savePassword(loginUser, oldPwd, newPwd, rePwd);
			AccessHelper.login(userManager.loadUserByUsername(loginUser.getUsername()));
			ControllerUtil.setSuccessMessage(request, "密码修改成功!");
		} catch (ValidationException ve) {
			ControllerUtil.setErrorMessage(request, ve.getDefaultMessage());
		}
		return "redirect:/account/changePassword.php";
	}
}
