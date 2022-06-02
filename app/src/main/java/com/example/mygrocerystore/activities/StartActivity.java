package com.example.mygrocerystore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mygrocerystore.R;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }

    public void store(View view) {
        Intent it = new Intent(StartActivity.this, StoreHomeActivity.class);
        startActivity(it);
    }

    public void deliveryMan(View view) {
        Intent it = new Intent(StartActivity.this, DeliveryManHomeActivity.class);
        startActivity(it);
    }

    public void order(View view) {
        Intent it = new Intent(StartActivity.this, HomeActivity.class);
        startActivity(it);
    }
}

