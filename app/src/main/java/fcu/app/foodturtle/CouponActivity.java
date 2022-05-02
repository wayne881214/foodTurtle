package fcu.app.foodturtle;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CouponActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void home(View v) {
		setContentView(R.layout.activity_main);
	}
	public void onClick1(View v) {
		setContentView(R.layout.activity_coupon_now);
	}
	public void onClick2(View v) {
		setContentView(R.layout.activity_coupon_expired);
	}
}