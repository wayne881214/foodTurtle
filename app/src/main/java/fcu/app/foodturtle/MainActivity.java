package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void homePage(View v) {
		setContentView(R.layout.activity_main);
	}
	public void onClick1(View v) {
		setContentView(R.layout.activity_shop_car);
	}
	public void onClick2(View v) {
		setContentView(R.layout.activity_shop_car_2);
	}
	public void onClick3(View v) {
		setContentView(R.layout.activity_shop_car_3);
	}
}