package com.example.krcho.clozet.request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krmpr on 16. 1. 3..
 */
public class Options {
    String size;
    String color;
    int stock;
    String code;

    public Options(JSONObject json){
        try {
            this.setSize(json.getString("size"));
            this.setColor(json.getString("color"));
            this.setStock(json.getInt("stock"));
            this.setCode(json.getString("code"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public int getStock() {
        return stock;
    }

    public String getCode() {
        return code;
    }
}
