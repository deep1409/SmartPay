package com.smart.pay.Retrofit_Models.MobileRechargeModel;

public class PostpaidModel {

    String operator_name,operator_code;

    public PostpaidModel(String operator_name, String operator_code) {
        this.operator_name = operator_name;
        this.operator_code = operator_code;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getOperator_code() {
        return operator_code;
    }

    public void setOperator_code(String operator_code) {
        this.operator_code = operator_code;
    }
}
