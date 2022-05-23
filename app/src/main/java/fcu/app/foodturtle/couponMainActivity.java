package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fcu.app.foodturtle.ArrayAdapter.CouponArrayAdapter;
import fcu.app.foodturtle.item.CouponItem;

public class couponMainActivity extends AppCompatActivity {
		Context T=this;
		ArrayList<CouponItem> couponItemList = new ArrayList<CouponItem>();
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference couponsRef = database.getReference("/coupons");
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_now);
				ListView lvCoupon = this.findViewById(R.id.lv_coupon);

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
        setContentView(R.layout.activity_coupon_now);
    }
    public void onClick1(View v) {
        setContentView(R.layout.activity_coupon_now);
				ListView lvCoupon = this.findViewById(R.id.lv_coupon);

        CouponArrayAdapter adapter = new CouponArrayAdapter(this, R.layout.listitem_coupon, couponItemList);
        lvCoupon.setAdapter(adapter);
    }
    public void onClick2(View v) {
//			setContentView(R.layout.activity_coupon_expired);
//			ListView lvCoupon = this.findViewById(R.id.lv_coupon);
//
//			CouponArrayAdapter adapter = new CouponArrayAdapter(this, R.layout.listitem_coupon, couponItemList);
//			lvCoupon.setAdapter(adapter);
    }
}