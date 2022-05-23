package fcu.app.foodturtle.item;

public class FoodItem {
	public int imgResId;
	public String foodName;
	public String foodCommit;
	public int foodMoney;
	public String foodType;
	public FoodItem(){}

  public FoodItem(int imgResId, String foodName, String foodCommit, int foodMoney, String foodType) {
	this.imgResId = imgResId;
	this.foodName = foodName;
	this.foodCommit = foodCommit;
	this.foodMoney = foodMoney;
	this.foodType = foodType;
	System.out.println("this.imgResId!!!:"+this.imgResId);

	}



  public int getImgResId() {
	return imgResId;
  }
	public void setImgResId(int imgResId) {
		this.imgResId = imgResId;
	}

  public String getFoodName() {
	return foodName;
  }
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

  public int getFoodMoney() {
	return foodMoney;
  }
	public void setFoodMoney(int foodMoney) {
		this.foodMoney = foodMoney;
	}

  public String getFoodCommit() {
	return foodCommit;
  }
	public void setFoodCommit(String foodCommit) {
		this.foodCommit = foodCommit;
	}

  public String getFoodType() {
	return foodType;
  }
	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

}
