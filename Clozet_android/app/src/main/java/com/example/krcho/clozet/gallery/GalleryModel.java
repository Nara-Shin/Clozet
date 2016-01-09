package com.example.krcho.clozet.gallery;

import android.graphics.drawable.BitmapDrawable;

public class GalleryModel {

	private String brandName;
	private String productName;
	private int price;
	private BitmapDrawable image;
	private boolean like;
	private String date;

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

	public boolean getLike() {
		return like;
	}

	public String getDate() {
		return this.date;
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

	public void setLike(boolean like) {
		this.like = like;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
