package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OrdersHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_home);
    }
    public void Neworder(View v) {
        Intent intent = new Intent();
        intent.setClass(this,ordersAcceptActivity.class);
        startActivity(intent);
    }
}