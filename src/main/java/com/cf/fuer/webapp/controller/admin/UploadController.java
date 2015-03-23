package com.cf.fuer.webapp.controller.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UploadController {
	
	private Log log = LogFactory.getLog(UploadController.class);

	@RequestMapping("/scripts/ueditor/imageUp")
	public String index() {
		return "admin/index";
	}

}
