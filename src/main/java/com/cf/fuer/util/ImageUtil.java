package com.cf.fuer.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	public static final String uploadDir = "E:/java/tomcat-6.0.20/webapps/fuer/images/userImg/";
	
	public static String getUploaddir() {
		return uploadDir;
	}

	public static ConcurrentHashMap<String, Boolean> imageProcessFlag = new ConcurrentHashMap<String, Boolean>();
	// 图片框的长度和宽度
	public static int divWidth = 284;
	public static int divHeight = 266;

	/**
	 * 先把图片同比例缩放成页面上的大小，然后利用5个参数切割图片
	 * 
	 * @throws InterruptedException
	 */
	public static void cutImage(String src, int x, int y, int width, int height) throws IOException {

		File srcFile = new File(src);
		BufferedImage image = ImageIO.read(srcFile);
		// 首先进行缩放
		double system_ratio = ((double) divHeight) / ((double) divWidth);
		double old_h = image.getHeight();
		double old_w = image.getWidth();
		double img_ratio = old_h / old_w;
		double new_w, new_h;
		if (img_ratio >= system_ratio) {
			new_h = old_h >= divHeight ? divHeight : old_h;
			new_w = new_h / img_ratio;
		} else {
			new_w = old_w >= divWidth ? divWidth : old_w;
			new_h = new_w * img_ratio;
		}
		BufferedImage bufferedImage = new BufferedImage((int) new_w, (int) new_h, BufferedImage.TYPE_INT_RGB);
		Image scaledImage = image.getScaledInstance((int) new_w, (int) new_h, Image.SCALE_DEFAULT);
		bufferedImage.getGraphics().drawImage(scaledImage, 0, 0, (int) new_w, (int) new_h, null);

		String fileFormat = srcFile.getName().substring(srcFile.getName().lastIndexOf(".") + 1);
		BufferedImage subBufferedImage = bufferedImage.getSubimage(x, y, width, height);
		//registerImageListener(subBufferedImage, fileFormat, srcFile);
		ImageIO.write(subBufferedImage, fileFormat, srcFile);
	}

}
