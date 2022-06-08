package com.example.mygrocerystore.models;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class FullOrderModel extends MyCartModel {
	List<OrderModel> product;
	public FullOrderModel(){
	}


	public FullOrderModel( String delivery,List<OrderModel> product , String address, String payment,String remark,String store,  int type,int key,String customer) {
		this.key = key;
		this.store = store;
		this.customer = customer;
		this.delivery = delivery;
		this.type = type;
		this.remark=remark;
		this.payment = payment;
		this.address = address;
		this.product = product;
	}

//	public List<OrderModel>  getProduct() {
//		return product;
//	}
//
//	public void setProduct(List<OrderModel> product) {
//		this.product =product;
//	}


}

