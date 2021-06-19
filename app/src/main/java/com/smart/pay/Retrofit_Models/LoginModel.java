package com.smart.pay.Retrofit_Models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {


    @SerializedName("email")
    String phone_number;


    String password;

    public LoginModel(String phone_number, String password) {
        this.phone_number = phone_number;
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
