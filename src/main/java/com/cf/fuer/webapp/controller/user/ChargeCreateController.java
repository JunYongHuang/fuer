package com.cf.fuer.webapp.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IChargeManager;
import com.cf.fuer.model.Charge;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.ControllerUtil;


@SessionAttributes("chargeForm")
@Controller
@RequestMapping("/account/charge.php")
public class ChargeCreateController {
	@Autowired
	private IChargeManager chargeManager;

	/**
	 * FORM
	 */
	public static final String CHARGE_FORM = "chargeForm";

	private static final String FORM_VIEW = "charge";

	@RequestMapping(method = RequestMethod.GET)
	public String prepareForm(HttpServletRequest request, ModelMap model) {
		Charge charge = new Charge();
		String username = AccessHelper.getLoginUser().getUsername();
		charge.setUser(username);
		charge.setConfirmUser(username);
		charge.setPayUser(username);
		model.addAttribute(CHARGE_FORM, charge);
		return FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doSave(@ModelAttribute(CHARGE_FORM) Charge charge, BindingResult result, Model model, HttpServletRequest request, SessionStatus status) {
		try {
			chargeManager.create(charge);			
			status.setComplete();
			return "redirect:/account/sendCharge.php?orderId="+charge.getOrderId();
		} catch (ValidationException ve) {
			ControllerUtil.rejectErrors(ve, result);
			model.addAttribute("errors", result.getFieldErrors());
			return FORM_VIEW;
		}
	}	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, Object command) {
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
	}
}
