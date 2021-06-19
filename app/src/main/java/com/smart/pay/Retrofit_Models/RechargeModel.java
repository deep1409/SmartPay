package com.smart.pay.Retrofit_Models;

import com.google.gson.annotations.SerializedName;

public class RechargeModel {

    public RechargeModel(String phonenumber, String tokenid, String admin_email, String amount, String operator_code, String recharge_id) {
        this.phonenumber = phonenumber;
        Tokenid = tokenid;
        this.admin_email = admin_email;
        Amount = amount;
        this.operator_code = operator_code;
        this.recharge_id = recharge_id;
    }

    @SerializedName("Customernumber")
    String phonenumber;

    @SerializedName("Tokenid") //generated token
    String Tokenid ;

    @SerializedName("Userid")  //registered email address of admin
     String admin_email ;


    @SerializedName("Amount")
     String Amount ;

    @SerializedName("Optcode") //operator code
     String operator_code ;

    @SerializedName("Yourrchid") //unique recharge id
     String recharge_id;

    public String getTokenid() {
        return Tokenid;
    }

    public void setTokenid(String tokenid) {
        Tokenid = tokenid;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getOperator_code() {
        return operator_code;
    }

    public void setOperator_code(String operator_code) {
        this.operator_code = operator_code;
    }

    public String getRecharge_id() {
        return recharge_id;
    }

    public void setRecharge_id(String recharge_id) {
        this.recharge_id = recharge_id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
