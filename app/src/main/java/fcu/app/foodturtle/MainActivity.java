package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_now);

		ListView lvCoupon = this.findViewById(R.id.lv_coupon);

		ArrayList<CouponItem> couponItemList = new ArrayList<CouponItem>();
		couponItemList.add(new CouponItem("000","try1", 100,"vip888","try1","2002/02/02"));
//		couponItemList.add(new CouponItem("000","try2", 300,"vip999","try2","2003/02/02"));
//		couponItemList.add(new CouponItem("000","try3", 500,"vip666","try3","2999/02/02"));

		CouponArrayAdapter adapter = new CouponArrayAdapter(this, R.layout.listitem_coupon, couponItemList);

		lvCoupon.setAdapter(adapter);

	}
	public void home(View v) {
		setContentView(R.layout.activity_main);
	}
	public void onClick1(View v) {
		setContentView(R.layout.activity_coupon_now);

		ListView lvCoupon = this.findViewById(R.id.lv_coupon);

		ArrayList<CouponItem> couponItemList = new ArrayList<CouponItem>();
		couponItemList.add(new CouponItem("000","try1", 100,"vip888","try1","2002/02/02"));
		couponItemList.add(new CouponItem("000","try2", 300,"vip999","try2","2003/02/02"));
		couponItemList.add(new CouponItem("000","try3", 500,"vip666","try3","2999/02/02"));

		CouponArrayAdapter adapter = new CouponArrayAdapter(this, R.layout.listitem_coupon, couponItemList);

		lvCoupon.setAdapter(adapter);
	}
	public void onClick2(View v) {
		setContentView(R.layout.activity_coupon_expired);
	}
}