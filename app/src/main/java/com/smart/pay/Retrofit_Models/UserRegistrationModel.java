package com.smart.pay.Retrofit_Models;

public class UserRegistrationModel {

    String phone_number,name,email,password;

    public UserRegistrationModel(String phone_number, String name, String email, String password) {
        this.phone_number = phone_number;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
