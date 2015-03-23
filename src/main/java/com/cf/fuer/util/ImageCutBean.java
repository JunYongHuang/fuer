package com.cf.fuer.util;

import java.io.Serializable;

/**
 * @author kangmor
 * 
 * 实体类，保存所有切割图片后的参数。
 *
 */
public class ImageCutBean implements Serializable{

	private static final long serialVersionUID = 8906453555541006738L;
	
	//x1:图片转成和页面上相同大小后，截图所需的left
	//y1:图片转成和页面上相同大小后，截图所需的top
	//y2-y1=图片的高度
	//x2-x1=图片的宽度
	private long x1;
	private long x2;
	private long y1;
	private long y2;
	
	//图片路径，用于截图
	private String imgPath;

	public long getX1() {
		return x1;
	}

	public void setX1(long x1) {
		this.x1 = x1;
	}

	public long getX2() {
		return x2;
	}

	public void setX2(long x2) {
		this.x2 = x2;
	}

	public long getY1() {
		return y1;
	}

	public void setY1(long y1) {
		this.y1 = y1;
	}

	public long getY2() {
		return y2;
	}

	public void setY2(long y2) {
		this.y2 = y2;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
