package com.example.krcho.clozet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krmpr on 16. 1. 8..
 */
public class CurrentFittingRoom {
    private static CurrentFittingRoom instance;

    private CurrentFittingRoom(){}

    public static CurrentFittingRoom getInstance() {
        if(instance == null) {
            instance = new CurrentFittingRoom();
        }
        return instance;
    }


    String brand_name;
    String branch_name;
    String img_url;

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setInfo(JSONObject json){
        try {
            setBrand_name(json.getString("brand_name"));
            setBranch_name(json.getString("branch_name"));
            setImg_url(json.getString("brand_img"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
