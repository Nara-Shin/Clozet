package com.example.krcho.clozet.promotion;

import android.graphics.drawable.BitmapDrawable;

public class PromotionModel {

	private String brandName;
	private String productName;
	private int price;
	private BitmapDrawable image;

	public String getBrandName() {
		return brandName;
	}

	public String getProductName() {
		return productName;
	}

	public int getPrice() {
		return price;
	}

	public BitmapDrawable getImage() {
		return image;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setImage(BitmapDrawable image) {
		this.image = image;
	}

}
