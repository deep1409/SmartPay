package com.smart.pay.Retrofit_Models;

import com.google.gson.annotations.SerializedName;

public class RechargeModel {

    public RechargeModel() {
    }

    String number,provider_id,amount,payment_id;

    public RechargeModel(String number, String provider_id, String amount, String payment_id) {
        this.number = number;
        this.provider_id = provider_id;
        this.amount = amount;
        this.payment_id = payment_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }
}
