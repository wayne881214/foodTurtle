package fcu.app.foodturtle.item;

public class StoreItem {
	private int imgResId;
	private String storeName;
	private int storeFreight;
	private String storeFraction;
	private String storeType;

	public StoreItem(int imgResId, String storeName, int storeFreight, String storeFraction, String storeType) {
		this.imgResId = imgResId;
		this.storeName = storeName;
		this.storeFreight = storeFreight;
		this.storeFraction = storeFraction;
		this.storeType = storeType;
	}


	public int getImgResId() {
		return imgResId;
	}
	public void setImgResId(int imgResId) {
		this.imgResId = imgResId;
	}

	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public int getStoreFreight() {
		return storeFreight;
	}
	public void setStoreFreight(int storeFreight) {
		this.storeFreight = storeFreight;
	}

	public String getStoreFraction() {
		return storeFraction;
	}
	public void setStoreFraction(String storeFraction) {
		this.storeFraction = storeFraction;
	}

	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

}
