package com.example.krcho.clozet.request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by krcho on 2015-11-15.
 */
public class Product {
    String product_code;
    String product_brand;
    String product_name;
    String product_detail;
    int product_price;
    String product_photo;
    ArrayList<String> sizes = new ArrayList<>();
    ArrayList<String> colors = new ArrayList<>();
    ArrayList<Options> options = new ArrayList<>();

    public Product(){}

    public Product(JSONObject json){
        try{
            this.setProduct_code(json.getString("product_code"));
            this.setProduct_brand(json.getString("product_brand"));
            this.setProduct_name(json.getString("product_name"));
            this.setProduct_detail(json.getString("product_detail"));
            this.setProduct_price(json.getInt("product_price"));
            this.setProduct_photo(json.getString("product_photo"));

            this.addSize(json.getString("product_sizes"));
            this.addColor(json.getString("product_colors"));

        }catch (JSONException e){
            e.printStackTrace();
        }
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

    public void addSize(String size){
        String[] array = size.split(",");
        for(String item : array){
            this.sizes.add(item);
        }
    }

    public void addColor(String color){
        String[] array = color.split(",");
        for(String item : array){
            this.colors.add(item);
        }
    }

    public void addOption(Options op){
        this.options.add(op);
    }
}
