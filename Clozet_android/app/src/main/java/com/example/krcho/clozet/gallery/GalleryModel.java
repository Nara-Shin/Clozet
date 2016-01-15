package com.example.krcho.clozet.gallery;

import android.graphics.Bitmap;

public class GalleryModel {

	private String brandName;
	private String productName;
	private int price;
	private Bitmap image;
	private boolean like;
	private String date;
	private String fileName;

	public String getBrandName() {
		return brandName;
	}

	public String getProductName() {
		return productName;
	}

	public int getPrice() {
		return price;
	}

	public Bitmap getImage() {
		return image;
	}

	public boolean getLike() {
		return like;
	}

	public String getDate() {
		return this.date;
	}

	public String getFileName() {
		return this.fileName;
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

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
