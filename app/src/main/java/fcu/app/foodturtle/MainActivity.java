package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Viewmenu(View v) {
        Intent intent = new Intent();
        intent.setClass(this,BrowseActivity.class);
        startActivity(intent);
    }

    public void createmenu(View v) {
        Intent intent = new Intent();
        intent.setClass(this,CreateAccountActivity.class);
        startActivity(intent);
    }

    public void deliverymenu(View v) {
        Intent intent = new Intent();
        intent.setClass(this,deliveryOrderActivity.class);
        startActivity(intent);
    }
    public void Ordermenu(View v) {
        Intent intent = new Intent();
        intent.setClass(this,OrdersHomeActivity.class);
        startActivity(intent);
    }


}