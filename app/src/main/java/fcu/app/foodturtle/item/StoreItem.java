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

	public String getStoreName() {
		return storeName;
	}

	public int getStoreFreight() {
		return storeFreight;
	}

	public String getStoreFraction() {
		return storeFraction;
	}

	public String getStoreType() {
		return storeType;
	}
}
