package com.example.mygrocerystore.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {

    String customer;
    String store;
    String delivery;
    int type;
    String remark;


    public MyCartModel() {
    }

    public MyCartModel(String store,String customer, String delivery, int type,String remark) {
        this.store = store;
        this.customer = customer;
        this.delivery = delivery;
        this.type = type;
        this.remark=remark;
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

    public void setdelivery(String delivery) {
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
}
