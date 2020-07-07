package com._520it.base.uitls.qrcode;

import jp.sourceforge.qrcode.data.QRCodeImage;

import java.awt.image.BufferedImage;

/**
 * 二维码
 * 创建人：FH 创建时间：2015年4月10日
 * @version
 */
public class QRimensionCodeImage implements QRCodeImage {

	BufferedImage bufImg;

	public QRimensionCodeImage(BufferedImage bufImg) {
		this.bufImg = bufImg;
	}

	
	public int getHeight() {
		return bufImg.getHeight();
	}

	
	public int getPixel(int x, int y) {
		return bufImg.getRGB(x, y);
	}

	
	public int getWidth() {
		return bufImg.getWidth();
	}

}
