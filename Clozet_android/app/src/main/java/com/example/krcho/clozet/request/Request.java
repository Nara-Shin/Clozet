package com.example.krcho.clozet.request;

import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by krcho on 2015-11-15.
 */
public class Request {
    String code;
    String brand;
    String name;
    String detail;
    int price;
    String photo_url;
    ArrayList<String> sizes = new ArrayList<>();
    ArrayList<String> colors = new ArrayList<>();


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public ArrayList<String> getSizes() {
        return sizes;
    }

    public void addSize(String[] ar) {
        for (String a : ar) {
            if (!a.equals("")) {
                this.sizes.add(a);
            }
        }
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void addColors(String[] ar) {
        for (String a : ar) {
            if (!a.equals("")) {
                this.colors.add(a);
            }
        }
    }
}
