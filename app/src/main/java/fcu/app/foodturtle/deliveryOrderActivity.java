package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class deliveryOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_order);
    }

    public void navmenu(View v) {
        Intent intent = new Intent();
        intent.setClass(this,NavigationActivity.class);
        startActivity(intent);
    }
}