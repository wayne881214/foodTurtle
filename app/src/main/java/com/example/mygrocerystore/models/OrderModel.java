package com.example.mygrocerystore.models;

public class OrderModel {

    String name;
    String count;
    int price;
    int totalPrice;
    String img_url;
		public OrderModel(){}

    public OrderModel(String name, String count, int  price, int totalPrice, String img_url) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.img_url= img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int  getPrice() {
        return price;
    }

    public void setPrice(int  price) {
        this.price =price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
