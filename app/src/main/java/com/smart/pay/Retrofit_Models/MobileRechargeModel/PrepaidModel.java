package com.smart.pay.Retrofit_Models.MobileRechargeModel;

public class PrepaidModel {
    String OperatorName,OperatorCode;

    public PrepaidModel() {
    }

    public PrepaidModel(String operatorName, String operatorCode) {
        OperatorName = operatorName;
        OperatorCode = operatorCode;
    }

    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    public String getOperatorCode() {
        return OperatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        OperatorCode = operatorCode;
    }
}
