package fcu.app.foodturtle.item;

public class OrderItem {
    public String orderNumber;
    public String orderFood;
    public String orderRemark;


    public OrderItem( String orderNumber,String orderFood, String orderRemark) {
        this.orderNumber = orderNumber;
        this.orderFood = orderFood;
        this. orderRemark =  orderRemark;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderFood() {
        return orderFood;
    }

    public String getOrderRemark() {
        return orderRemark;
    }


}