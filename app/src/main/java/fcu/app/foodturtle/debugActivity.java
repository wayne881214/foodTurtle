package fcu.app.foodturtle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import fcu.app.foodturtle.item.StoreItem;
import fcu.app.foodturtle.item.UserItem;

public class debugActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);

		//測試資料庫存取
		TextView customer = (TextView) findViewById(R.id.customerTry);
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference storesRef = database.getReference("/stores");
		DatabaseReference usersRef = database.getReference("/users");
//		DatabaseReference getContactsRef = database.getReference("users").child("444");
//		DatabaseReference customerRef = database.getReference("/stores/store1");

		usersRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
					System.out.println("Data:"+userSnapshot);
					System.out.println("email:"+userSnapshot.child("email").getValue());
					System.out.println("password:"+userSnapshot.child("password").getValue());
					UserItem user = userSnapshot.getValue(UserItem.class);
					System.out.println("!!!!!"+user.toString());
					System.out.println("?????"+user.getEmail()+":"+user.getPassword());
				}
			}
			@Override
			public void onCancelled(DatabaseError error) {
				// Failed to read value
			}
		});

		storesRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
					System.out.println("Data:"+storeSnapshot);
					String[] item = {"imgResId", "storeName", "storeFreight", "storeFraction","storeType"};
					for(String i:item) {
						System.out.println(i + ":" + storeSnapshot.child(i).getValue());
					}
//					StoreItem store = storeSnapshot.getValue(StoreItem.class);
//					System.out.println("??"+store.getStoreName()+":"+store.getStoreFraction()+":"+store.getStoreFreight()+":"+store.getStoreType());
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
		System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");

		Intent intent = new Intent();
//		intent.setClass(this, couponMainActivity.class);
		intent.setClass(this, CouponAddActivity.class);
		startActivity(intent);
	}

}
