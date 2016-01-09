package com.example.krcho.clozet;

import android.util.Log;

import com.example.krcho.clozet.network.CommonHttpClient;
import com.example.krcho.clozet.network.NetDefine;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by krmpr on 16. 1. 3..
 */
public class MyAccount {
    private static MyAccount instance;
    public static String MEMBERCODE = "membercode";

    private MyAccount(){}

    public static MyAccount getInstance() {
        if(instance == null) {
            instance = new MyAccount();
        }
        return instance;
    }


    private String member_code;

    public String getMember_code() throws Exception {
        if (member_code != null) {
            return member_code;
        }else {
            throw new Exception();
        }
    }

    public void setMember_code(String member_code) {
        this.member_code = member_code;
    }

}
