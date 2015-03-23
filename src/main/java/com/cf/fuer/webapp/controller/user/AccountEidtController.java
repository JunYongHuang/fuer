package com.cf.fuer.webapp.controller.user;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.cf.fuer.InitData;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.model.User;
import com.cf.fuer.security.AccessHelper;
import com.cf.fuer.util.ControllerUtil;

/**
 * 注册
 */
@SessionAttributes("accountForm")
@Controller
@RequestMapping("/account/edit.php")
public class AccountEidtController {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserManager userManager;

	/**
	 * 账户FORM
	 */
	public static final String USER_FORM = "accountForm";

	private static final String FORM_VIEW = "/account/accountEdit";

	private static final String REDIRECT_URL = "redirect:/account/edit.php";

	@RequestMapping(method = RequestMethod.GET)
	public String prepareForm(HttpServletRequest request, ModelMap model) throws Exception {
		User loginUser = AccessHelper.getLoginUser();
		User user = new User();
		BeanUtils.copyProperties(user, loginUser);
		model.addAttribute(USER_FORM, user);
		return FORM_VIEW;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doSave(@ModelAttribute(USER_FORM) User user, BindingResult result, Model model, HttpServletRequest request, SessionStatus status) {
		if (!ServletFileUpload.isMultipartContent(request)) {
			return FORM_VIEW;
		}
		User loginUser = AccessHelper.getLoginUser();
		boolean avatarChange = false;
		DefaultMultipartHttpServletRequest mRequest = (DefaultMultipartHttpServletRequest) request;
		MultipartFile file = mRequest.getFile("picFile");
		if (file != null && !file.isEmpty()) {
			if (!isImage(file.getOriginalFilename())) {// 不是图片
				ControllerUtil.setErrorMessage(request, "头像文件格式只能为GIF, PNG, JPG, JPEG或BMP!");
				return FORM_VIEW;
			}
			try {
				// 头像名称:用户ID+当前毫秒数
				String fileInnerName = user.getId() + getFileInnerName(file);
				String fileUploadName = "avatar/" + fileInnerName;
				File avatarFile = new File(InitData.getUploadDir(), fileUploadName);
				// 上传
				file.transferTo(avatarFile);
				// 备份
				FileUtils.copyFile(avatarFile, new File(InitData.getUploadBackupDir(), fileUploadName));
				user.setPic(fileInnerName);
				avatarChange = true;
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} else {
			user.setPic(loginUser.getPic());
		}
		try {
			boolean needResetPwd = false;
			if("thirdlogin".equalsIgnoreCase(loginUser.getPassword())){//第三方登陆且没有设置过邮箱的用户发送邮件
				if(loginUser.getEmail() == null || loginUser.getEmail().isEmpty()){
					needResetPwd = true;
				}
			}
			if(needResetPwd){
				boolean finishedEmail = userManager.resetPwdAndSendEmail(user);
				if(!finishedEmail){//发送邮件失败
					ControllerUtil.setErrorMessage(request, "邮件发送失败,请检查您的邮箱是否填写正确!");
					return FORM_VIEW;
				}
				log.debug("完善邮箱:"+user.getEmail()+" 用户:"+user.getUsername());
			}else{
				userManager.update(user);
			}
			status.setComplete();
			try {
				if (avatarChange) {
					// 清除之前的头像
					File oldAvatar = new File(InitData.getUploadDir(), loginUser.getPic());
					oldAvatar.delete();
					File oldBackupAvatar = new File(InitData.getUploadBackupDir(), loginUser.getPic());
					oldBackupAvatar.delete();
				}
			} catch (Exception e) {
				log.error("清除旧头像失败");
			}
			AccessHelper.login(user);
			ControllerUtil.setSuccessMessage(request, "资料更新成功!");
		} catch (ValidationException ve) {
			ControllerUtil.rejectErrors(ve, result);
			model.addAttribute("errors", result.getFieldErrors());
			return FORM_VIEW;
		}
		return REDIRECT_URL;
	}

	/**
	 * 根据上传的文件名后缀获取实际文件名
	 */
	private String getFileInnerName(MultipartFile file) {
		String originalFileName = file.getOriginalFilename();
		int lastDotIndex = originalFileName.lastIndexOf(".");
		String extensionName = "";
		if (lastDotIndex != -1) {
			extensionName = originalFileName.substring(lastDotIndex);
		}
		Long currentTime = System.currentTimeMillis();
		String fileInnerName = currentTime + extensionName;
		return fileInnerName;
	}

	/**
	 * 文件类型判断是否是图片
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean isImage(String fileName) {
		for (String ext : allowFiles) {
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	// 文件允许格式
	private String[] allowFiles = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
}
