package fcu.app.foodturtle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class debugActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_debug);
	}
	public void addStore(View v) {
		Intent intent = new Intent();
		intent.setClass(this, StoreAddActivity.class);
		startActivity(intent);
	}
}
