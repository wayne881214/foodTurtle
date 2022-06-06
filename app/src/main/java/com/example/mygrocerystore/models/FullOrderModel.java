package com.example.mygrocerystore.models;

public class FullOrderModel {
	OrderModel order;
	String store;
	int type;
	String customer;
//	String 外送員;
//	String 地址;
//  String 附註
	public FullOrderModel(){}

	public FullOrderModel(String store,int type,String customer){
		this.store=store;
		this.type=type;
		this.customer=customer;
	}
	public FullOrderModel(OrderModel order ,String store,int type,String customer){
		this.order=order;
		this.store=store;
		this.type=type;
		this.customer=customer;
	}
//	public FullOrderModel(OrderModel order ,String store,String type,String customer,String 外送員,String 地址){
//		this.order=order;
//		this.store=store;
//		this.type=type;
//		this.customer=customer;
//	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}


}

