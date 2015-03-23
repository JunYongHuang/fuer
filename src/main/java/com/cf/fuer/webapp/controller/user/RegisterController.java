package com.cf.fuer.webapp.controller.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.ControllerUtil;
import com.cf.fuer.util.RandomCode;
import com.cf.fuer.util.TokenUtil;

/**
 * 注册
 */
@SessionAttributes("userForm")
@Controller
@RequestMapping("/register.php")
public class RegisterController {

	private static Log log = LogFactory.getLog(RegisterController.class);
	
	@Autowired
	private IUserManager userManager;
	
	/**
	 * 账户FORM
	 */
	public static final String USER_FORM = "userForm";

	private static final String FORM_VIEW = "register";

	private static final String REDIRECT_URL = "redirect:/afterLogin.php";

	@RequestMapping(method = RequestMethod.GET)
	public String prepareForm(HttpServletRequest request, ModelMap model) {
		User user = new User();
		model.addAttribute(USER_FORM, user);
		return FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doSave(@ModelAttribute(USER_FORM) User user, BindingResult result, Model model, HttpServletRequest request,HttpServletResponse response,
			SessionStatus status) {
		try {
			validateCode(request);
//			String ip = TokenUtil.getRequestIp(request);
//			if(!userManager.canRegister(ip)){
//				throw new ValidationException("login.errorCode", "您已注册过游戏,请直接登陆!");
//			}
			String sourcefrom = getCookie(request,"sourcefrom");
			user.setSourceFrom(sourcefrom);
//			user.setFrom(Long.valueOf(from));
			userManager.create(user);
			log.info("用户["+user.getUsername()+"]注册来源："+sourcefrom+" "+getCookie(request,"referer"));
			//emailManager.sendEmailAfterReg(account.getUser().getEmail(), account.getAccountName(), password);
			status.setComplete();
			AccessHelper.login(user);
            TokenUtil.saveLoginUserToken(request);
		} catch (ValidationException ve) {
			ControllerUtil.rejectErrors(ve, result);
			model.addAttribute("errors", result.getFieldErrors());
			return FORM_VIEW;
		}
		return REDIRECT_URL;
	}
	
	/**
	 * 从cookie中获取注册用户的来源，如果cookie不存在，返回0
	 * @return
	 */
	private String getCookie(HttpServletRequest request,String cookieName){
		Cookie cookies[] = request.getCookies() ;
        Cookie c1 = null ;
        if(cookies != null){
            for(int i=0;i<cookies.length;i++){
               c1 = cookies[i] ;
               if(c1.getName().equalsIgnoreCase(cookieName)){
            	   return c1.getValue();
               }
            }
        }
        return "";
	}


	/**
	 * 验证码是否正确
	 */
	private void validateCode(HttpServletRequest request) throws ValidationException {
		String codeInSession = (String) request.getSession().getAttribute(RandomCode.RANDOM_CODE);
		String codeSubmit = request.getParameter("checkCode");
		if (codeInSession != null && !(codeInSession.equalsIgnoreCase(codeSubmit))) {
			throw new ValidationException("login.errorCode", "验证码错误!");
		}
	}
}
