package com.example.mygrocerystore.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    int key;
    String customer;
    String store;
    String delivery;
    int type;
    String remark;
    String payment;
    String address;

    public MyCartModel() {
    }

    public MyCartModel( int key,String store,String customer, String delivery, int type,String remark, String payment, String address) {
        this.key = key;
        this.store = store;
        this.customer = customer;
        this.delivery = delivery;
        this.type = type;
        this.remark=remark;
        this.payment = payment;
        this.address = address;
    }
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
