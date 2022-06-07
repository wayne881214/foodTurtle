package com.example.mygrocerystore.models;

public class FullOrderModel extends MyCartModel {
	OrderModel order;

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
	public FullOrderModel( String delivery,OrderModel order , String address, String payment,String remark,String store,  int type,int key,String customer) {
		this.key = key;
		this.store = store;
		this.customer = customer;
		this.delivery = delivery;
		this.type = type;
		this.remark=remark;
		this.payment = payment;
		this.address = address;
		this.order = order;
	}
	public void showAll(){
		System.out.println("key:"+key);
		System.out.println("store:"+store);
		System.out.println("customer:"+customer);
		System.out.println("delivery:"+delivery);
		System.out.println("type:"+type);
		System.out.println("remark:"+remark);
		System.out.println("payment:"+payment);
		System.out.println("address:"+address);
	}
	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}

}

