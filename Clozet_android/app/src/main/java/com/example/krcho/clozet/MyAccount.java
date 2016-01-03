package com.example.krcho.clozet;

/**
 * Created by krmpr on 16. 1. 3..
 */
public class MyAccount {

    private static MyAccount instance;

    private MyAccount(){}

    public static MyAccount getInstance() {
        if(instance == null) {
            instance = new MyAccount();
        }
        return instance;
    }


    private String member_code;

    public String getMember_code() {
        return member_code;
    }

    public void setMember_code(String member_code) {
        this.member_code = member_code;
    }
}
