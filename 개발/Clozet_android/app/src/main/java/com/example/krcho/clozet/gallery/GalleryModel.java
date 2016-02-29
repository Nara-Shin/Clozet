package com.example.krcho.clozet.gallery;

import android.graphics.Bitmap;

import com.example.krcho.clozet.request.Product;

import org.json.JSONException;
import org.json.JSONObject;

public class GalleryModel extends Product {

	private Bitmap image;
	private String date;
	private String fileName;

	public GalleryModel() {
	}

	public GalleryModel(Bitmap image, String fileName) {
		this.image = image;
		this.fileName = fileName;
	}

	public GalleryModel(JSONObject json, String barcode) throws Exception {
		try {
			this.setBarcode(barcode);
			this.setProduct_code(json.getString("product_code"));
			this.setProduct_brand(json.getString("product_brand"));
			this.setProduct_name(json.getString("product_name"));
			this.setProduct_detail(json.getString("product_detail"));
			this.setProduct_price(json.getInt("product_price"));
			this.setProduct_photo(json.getString("product_image"));
			this.setLike(json.getInt("product_like") == 1);
			this.addSize(json.getString("product_sizes"));
			this.addColor(json.getString("product_colors"));
			this.setProduct_url(json.getString("product_url"));

			this.addOption(json.getJSONArray("options"));

		} catch (JSONException e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	public GalleryModel(JSONObject json, String barcode, Bitmap image, String fileName) throws Exception {
		try {
			this.setBarcode(barcode);
			this.setProduct_code(json.getString("product_code"));
			this.setProduct_brand(json.getString("product_brand"));
			this.setProduct_name(json.getString("product_name"));
			this.setProduct_detail(json.getString("product_detail"));
			this.setProduct_price(json.getInt("product_price"));
			this.setProduct_photo(json.getString("product_image"));
			this.setLike(json.getInt("product_like") == 1);
			this.addSize(json.getString("product_sizes"));
			this.addColor(json.getString("product_colors"));

			this.addOption(json.getJSONArray("options"));

			this.image = image;
			this.fileName = fileName;

		} catch (JSONException e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	public String getDate() {
		return this.date;
	}

	public Bitmap getImage() {
		return this.image;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
