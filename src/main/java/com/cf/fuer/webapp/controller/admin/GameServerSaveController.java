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

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IGameServerManager;
import com.cf.fuer.model.GameServer;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.ControllerUtil;

/**
 * 创建或更新文章
 */
@SessionAttributes("serverForm")
@Controller
@RequestMapping("/admin/gameServer.php")
public class GameServerSaveController {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	private IGameServerManager gameServerManager;

	/**
	 * 文章FORM
	 */
	public static final String GAMESERVER_FORM = "serverForm";

	private static final String FORM_VIEW = "admin/gameServer";

	private static final String REDIRECT_URL = "redirect:/admin/gameServer.php";

	@RequestMapping(method = RequestMethod.GET)
	public String prepareForm(HttpServletRequest request, ModelMap model) {
		long id = ServletRequestUtils.getLongParameter(request, "id", -1L);
		GameServer gameServer;
		if (id == -1) {
			gameServer = new GameServer();
		} else {
			gameServer = gameServerManager.get(id);
		}
		model.addAttribute(GAMESERVER_FORM, gameServer);
		return FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doSave(@ModelAttribute(GAMESERVER_FORM) GameServer gameServer, BindingResult result, Model model, HttpServletRequest request, SessionStatus status) {

		Long id = gameServer.getId();
		try {
			boolean create = gameServer.getId() == null || gameServer.getId() <= 0;
			if (create) {
				id = gameServerManager.create(gameServer);
				log.info("管理员[" + AccessHelper.getLoginUser().getUsername() + "]新增游戏服务器" + gameServer);
				ControllerUtil.setSuccessMessage(request, "新增游戏服务器成功!");
			} else {
				id = gameServerManager.update(gameServer);
				log.info("管理员[" + AccessHelper.getLoginUser().getUsername() + "]更新游戏服务器" + gameServer);
				ControllerUtil.setSuccessMessage(request, "游戏服务器更新成功!");
			}
			// emailManager.sendEmailAfterReg(account.getUser().getEmail(),
			// account.getAccountName(), password);
			status.setComplete();
		} catch (ValidationException ve) {
			ControllerUtil.rejectErrors(ve, result);
			model.addAttribute("errors", result.getFieldErrors());
			return FORM_VIEW;
		}
		return REDIRECT_URL + "?id=" + id;
	}
}
