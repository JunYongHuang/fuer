package com.cf.fuer;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.cf.fuer.util.SpreadUtils;

@Component
public class InitData implements ApplicationContextAware {

	/**
	 * 头像上传目录
	 */
	private static File uploadDir;

	/**
	 * 备份目录
	 */
	private static File uploadBackupDir;

	Log log = LogFactory.getLog(getClass());

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpreadUtils.startJob();//点一点活动线程启动
		initAvatar(applicationContext);
	}

	/**
	 * 初始化头像操作(创建头像上传文件夹和备份文件夹,如果两个文件夹内容不相同恢复备份文件夹到头像文件夹)
	 */
	private void initAvatar(ApplicationContext applicationContext) {
		try {			
			// 获取项目路径
			File appDir = applicationContext.getResource("").getFile();
			uploadDir = new File(appDir, "images");
			String avatarDirName = "avatar";
			File avatarDir = new File(uploadDir,avatarDirName);
			boolean needRestore = false;
			// 创建头像目录
			if (!avatarDir.exists()) {
				log.warn("创建头像文件夹(Create avatar upload dir)");
				avatarDir.mkdir();
				//恢复头像
				needRestore = true;
			}
			
			// 备份目录:TOMCAT目录/upload_backup
			File tomcatDir = appDir.getParentFile().getParentFile();
			uploadBackupDir = new File(tomcatDir, "upload_backup");
			// 创建备份目录
			if (!uploadBackupDir.exists()) {
				log.warn("创建上传资源备份文件夹(Create upload backup dir)");
				uploadBackupDir.mkdir();
			} else {
				if (needRestore) {
					// 复制备份文件中的头像到头像目录
					File[] uploads = uploadBackupDir.listFiles();
					log.warn("开始恢复上传的资源(Restoring upload form upload_backup dir to upload dir) ...");
					File editorUploadDir = new File(appDir, "scripts/ueditor/jsp");
					for (File upload : uploads) {
						String fileName = upload.getName();
						if(upload.isDirectory()){
							if(fileName.equals(avatarDirName)){
								FileUtils.copyDirectory(upload, avatarDir);
							}else{
								FileUtils.copyDirectory(upload, new File(editorUploadDir, fileName));
							}
							
						}
					}
					log.warn("恢复上传的资源结束(Restore upload finished)");
				}
			}
		} catch (Exception e) {
			log.error("上传的资源操作失败(upload operator error)", e);
		}
	}

	/**
	 * 头像上传文件目录
	 */
	public static File getUploadDir() {
		return uploadDir;
	}

	/**
	 * 上传资源备份目录
	 * @return
	 */
	public static File getUploadBackupDir() {
		return uploadBackupDir;
	}
}
