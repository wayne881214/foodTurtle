package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ShopcarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar);
    }
    public void  viewshop2(View v) {
        Intent intent = new Intent();
        intent.setClass(this,shopcar2Activity.class);
        startActivity(intent);
    }
    public void Coupon(View v) {
        Intent intent = new Intent();
        intent.setClass(this,couponMainActivity.class);
        startActivity(intent);
    }
    public void chat(View v) {
        Intent intent = new Intent();
        intent.setClass(this,chatActivity.class);
        startActivity(intent);
    }
}