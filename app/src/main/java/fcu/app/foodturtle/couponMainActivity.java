package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fcu.app.foodturtle.ArrayAdapter.CouponArrayAdapter;
import fcu.app.foodturtle.item.CouponItem;

public class couponMainActivity extends AppCompatActivity {
	Context T=this;

	private EditText etId;
	private EditText etCode;

		ArrayList<CouponItem> couponItemList = new ArrayList<CouponItem>();
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference couponsRef = database.getReference("/coupons");
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_all);
				ListView lvCoupon = this.findViewById(R.id.lv_coupon);

				couponItemList.clear();
				couponsRef.addValueEventListener(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot dataSnapshot) {
							for (DataSnapshot couponSnapshot : dataSnapshot.getChildren()) {
								System.out.println("Data:"+couponSnapshot);
								CouponItem coupon = couponSnapshot.getValue(CouponItem.class);
								couponItemList.add(coupon);
								CouponArrayAdapter adapter = new CouponArrayAdapter(T, R.layout.listitem_coupon, couponItemList);
								lvCoupon.setAdapter(adapter);
							}
						}
						@Override
						public void onCancelled(DatabaseError error) {
							// Failed to read value
						}
					});
    }
    public void home(View v) {
        setContentView(R.layout.activity_coupon_all);
    }
		public void user_add(View v) {
			setContentView(R.layout.activity_coupon_user_add);

		}
		public void addCoupon(View v) {
			etCode = findViewById(R.id.couponCode);
			String Code = etCode.getText().toString();
			String path ="users/"+UserDetail.username+"/coupons";

			System.out.println("Path:"+path);

			couponsRef.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot couponSnapshot : dataSnapshot.getChildren()) {
						System.out.println("Data:"+couponSnapshot);
						CouponItem coupon = couponSnapshot.getValue(CouponItem.class);
						System.out.println(Code+" VS "+coupon.getCode());
						if(coupon.getCode().equals(Code)){
							FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
							DatabaseReference couponsRef = firebaseDatabase.getReference(path);
							Map<String, Object> userCoupon = new HashMap<>();
							DatabaseReference nameRef = couponsRef.child(coupon.getName());
							userCoupon.put("id", coupon.getId());
							userCoupon.put("name", coupon.getName());
							userCoupon.put("money",coupon.getMoney());
							userCoupon.put("code", coupon.getCode());
							userCoupon.put("description", coupon.getDescription());
							userCoupon.put("date", coupon.getDate());
							nameRef.updateChildren(userCoupon);
						}
					}
				}
				@Override
				public void onCancelled(DatabaseError error) {
					// Failed to read value
				}
			});

		}

	public void onClick1(View v) {
        setContentView(R.layout.activity_coupon_all);
				ListView lvCoupon = this.findViewById(R.id.lv_coupon);

				couponItemList.clear();
				couponsRef.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						for (DataSnapshot couponSnapshot : dataSnapshot.getChildren()) {
							System.out.println("Data:"+couponSnapshot);
							CouponItem coupon = couponSnapshot.getValue(CouponItem.class);
							couponItemList.add(coupon);
							CouponArrayAdapter adapter = new CouponArrayAdapter(T, R.layout.listitem_coupon, couponItemList);
							lvCoupon.setAdapter(adapter);
						}
					}
					@Override
					public void onCancelled(DatabaseError error) {
						// Failed to read value
					}
				});
        CouponArrayAdapter adapter = new CouponArrayAdapter(this, R.layout.listitem_coupon, couponItemList);
        lvCoupon.setAdapter(adapter);
    }
    public void onClick2(View v) {
			setContentView(R.layout.activity_coupon_user);
			TextView txv=(TextView)findViewById(R.id.userName1);
			txv.setText(UserDetail.username+"的優惠券");
			ListView lvCoupon = this.findViewById(R.id.lv_coupon);
			String path ="users/"+UserDetail.username+"/coupons";
			DatabaseReference usersRef = database.getReference(path);
			couponItemList.clear();
			usersRef.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot couponSnapshot : dataSnapshot.getChildren()) {
						System.out.println("Data:"+couponSnapshot);
						CouponItem coupon = couponSnapshot.getValue(CouponItem.class);
						couponItemList.add(coupon);
						CouponArrayAdapter adapter = new CouponArrayAdapter(T, R.layout.listitem_coupon, couponItemList);
						lvCoupon.setAdapter(adapter);
					}
				}
				@Override
				public void onCancelled(DatabaseError error) {
					// Failed to read value
				}
			});

			CouponArrayAdapter adapter = new CouponArrayAdapter(this, R.layout.listitem_coupon, couponItemList);
			lvCoupon.setAdapter(adapter);
    }
}