package fcu.app.foodturtle.item;

public class FoodItem {
	public int imgResId;
	public String foodName;
	public String foodCommit;
	public int foodMoney;
	public String foodType;

  public FoodItem(int imgResId, String foodName, String foodCommit, int foodMoney, String foodType) {
	this.imgResId = imgResId;
	this.foodName = foodName;
	this.foodCommit = foodCommit;
	this.foodMoney = foodMoney;
	this.foodType = foodType;
  }

  public int getImgResId() {
	return imgResId;
  }

  public String getFoodName() {
	return foodName;
  }

  public int getFoodMoney() {
	return foodMoney;
  }

  public String getfoodCommit() {
	return foodCommit;
  }

  public String getFoodType() {
	return foodType;
  }

}
