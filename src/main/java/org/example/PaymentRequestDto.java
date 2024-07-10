package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentRequestDto {
    private String AMOUNT;
    private String CURRENCY_CODE;
    private String CUST_CITY;
    private String CUST_COUNTRY;
    private String CUST_EMAIL;
    private String CUST_FIRST_NAME;
    private String CUST_LAST_NAME;
    private String CUST_NAME;
    private String CUST_PHONE;
    private String CUST_STATE;
    private String CUST_STREET_ADDRESS1;
    private String CUST_ZIP;
    private String HASH;
    private String ORDER_ID;
    private String PAYMENT_TYPE;
    private String PAY_ID;
    private String PAY_TYPE;
    private String PRODUCT_DESC;
    private String RETURN_URL;
    private String TXNTYPE;

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getCURRENCY_CODE() {
        return CURRENCY_CODE;
    }

    public void setCURRENCY_CODE(String CURRENCY_CODE) {
        this.CURRENCY_CODE = CURRENCY_CODE;
    }

    public String getCUST_CITY() {
        return CUST_CITY;
    }

    public void setCUST_CITY(String CUST_CITY) {
        this.CUST_CITY = CUST_CITY;
    }

    public String getCUST_COUNTRY() {
        return CUST_COUNTRY;
    }

    public void setCUST_COUNTRY(String CUST_COUNTRY) {
        this.CUST_COUNTRY = CUST_COUNTRY;
    }

    public String getCUST_EMAIL() {
        return CUST_EMAIL;
    }

    public void setCUST_EMAIL(String CUST_EMAIL) {
        this.CUST_EMAIL = CUST_EMAIL;
    }

    public String getCUST_FIRST_NAME() {
        return CUST_FIRST_NAME;
    }

    public void setCUST_FIRST_NAME(String CUST_FIRST_NAME) {
        this.CUST_FIRST_NAME = CUST_FIRST_NAME;
    }

    public String getCUST_LAST_NAME() {
        return CUST_LAST_NAME;
    }

    public void setCUST_LAST_NAME(String CUST_LAST_NAME) {
        this.CUST_LAST_NAME = CUST_LAST_NAME;
    }

    public String getCUST_NAME() {
        return CUST_NAME;
    }

    public void setCUST_NAME(String CUST_NAME) {
        this.CUST_NAME = CUST_NAME;
    }

    public String getCUST_PHONE() {
        return CUST_PHONE;
    }

    public void setCUST_PHONE(String CUST_PHONE) {
        this.CUST_PHONE = CUST_PHONE;
    }

    public String getCUST_STATE() {
        return CUST_STATE;
    }

    public void setCUST_STATE(String CUST_STATE) {
        this.CUST_STATE = CUST_STATE;
    }

    public String getCUST_STREET_ADDRESS1() {
        return CUST_STREET_ADDRESS1;
    }

    public void setCUST_STREET_ADDRESS1(String CUST_STREET_ADDRESS1) {
        this.CUST_STREET_ADDRESS1 = CUST_STREET_ADDRESS1;
    }

    public String getCUST_ZIP() {
        return CUST_ZIP;
    }

    public void setCUST_ZIP(String CUST_ZIP) {
        this.CUST_ZIP = CUST_ZIP;
    }

    public String getHASH() {
        return HASH;
    }

    public void setHASH(String HASH) {
        this.HASH = HASH;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getPAYMENT_TYPE() {
        return PAYMENT_TYPE;
    }

    public void setPAYMENT_TYPE(String PAYMENT_TYPE) {
        this.PAYMENT_TYPE = PAYMENT_TYPE;
    }

    public String getPAY_ID() {
        return PAY_ID;
    }

    public void setPAY_ID(String PAY_ID) {
        this.PAY_ID = PAY_ID;
    }

    public String getPAY_TYPE() {
        return PAY_TYPE;
    }

    public void setPAY_TYPE(String PAY_TYPE) {
        this.PAY_TYPE = PAY_TYPE;
    }

    public String getPRODUCT_DESC() {
        return PRODUCT_DESC;
    }

    public void setPRODUCT_DESC(String PRODUCT_DESC) {
        this.PRODUCT_DESC = PRODUCT_DESC;
    }

    public String getRETURN_URL() {
        return RETURN_URL;
    }

    public void setRETURN_URL(String RETURN_URL) {
        this.RETURN_URL = RETURN_URL;
    }

    public String getTXNTYPE() {
        return TXNTYPE;
    }

    public void setTXNTYPE(String TXNTYPE) {
        this.TXNTYPE = TXNTYPE;
    }

    // Method to convert the object to a JSON string
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
