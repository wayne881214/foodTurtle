package fcu.app.foodturtle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fcu.app.foodturtle.item.CouponItem;
import fcu.app.foodturtle.item.FoodItem;

public class debugActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);

		//測試資料庫存取
		TextView customer = (TextView) findViewById(R.id.customerTry);
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference storesRef = database.getReference("/stores");
		DatabaseReference couponsRef = database.getReference("/coupons");

//		DatabaseReference getContactsRef = database.getReference("users").child("444");
//		DatabaseReference customerRef = database.getReference("/stores/store1");

		//優惠券資料存取測試
		couponsRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot couponSnapshot : dataSnapshot.getChildren()) {
					System.out.println("Data:"+couponSnapshot);
					CouponItem coupon = couponSnapshot.getValue(CouponItem.class);
					System.out.println("DataName:"+coupon.getName());
				}
			}
			@Override
			public void onCancelled(DatabaseError error) {
				// Failed to read value
			}
		});

	}


	public void addStore(View v) {
		Intent intent = new Intent();
		intent.setClass(this, StoreAddActivity.class);
		startActivity(intent);
	}
	public void couponLink(View v) {
		Intent intent = new Intent();
		intent.setClass(this, couponMainActivity.class);
		startActivity(intent);
	}
	public void BrowseLink(View v) {
		BrowseActivity.VALID_USER = true;
		Intent intent = new Intent();
		intent.setClass(this, BrowseActivity.class);
		startActivity(intent);
	}
	public void addCoupon(View v) {
		Intent intent = new Intent();
		intent.setClass(this, CouponAddActivity.class);
		startActivity(intent);
	}

}
