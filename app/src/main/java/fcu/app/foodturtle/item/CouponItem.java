package fcu.app.foodturtle.item;

public class CouponItem {
	private String id;
	private String name;
	private int money;
	private String code;
	private String description;
	private String date;

	public CouponItem(String id, String name, int money, String code, String description, String date) {
		this.id = id;
		this.name = name;
		this.money = money;
		this.code=code;
		this.description = description;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
