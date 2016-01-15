package com.example.krcho.clozet.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by krcho on 2015-11-15.
 */
public class Product {
    String barcode;
    String product_code;
    String product_brand;
    String product_name;
    String product_detail;
    int product_price;
    String product_photo;
    String product_url;
    boolean like;
    ArrayList<String> sizes = new ArrayList<>();
    ArrayList<String> colors = new ArrayList<>();
    ArrayList<Options> options = new ArrayList<>();

    String selected_size;
    String selected_color;

    public Product() {
    }

    public Product(JSONObject json, String barcode) throws Exception {
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

        } catch (JSONException e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(String product_detail) {
        this.product_detail = product_detail;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public void addSize(String size) {
        String[] array = size.split(",");
        for (String item : array) {
            this.sizes.add(item);
        }
    }

    public void addColor(String color) {
        String[] array = color.split(",");
        for (String item : array) {
            this.colors.add(item);
        }
    }

    public void addOption(JSONArray ops) {
        for (int i = 0; i < ops.length(); i++) {
            try {
                this.options.add(new Options(ops.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getSizes() {
        return sizes;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public ArrayList<Options> getOptions() {
        return options;
    }

    public void setSelectedSize(String size){
        this.selected_size = size;
    }

    public void setSelectedColor(String color){
        this.selected_color = color;
    }

    public String getOptionCode() {
        for(Options op : options){
            if (op.getSize().equals(selected_size) && op.getColor().equals(selected_color)){
                return op.getCode();
            }
        }
        return "-1";
    }

    public String getProduct_url() {
        return this.product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }
}
