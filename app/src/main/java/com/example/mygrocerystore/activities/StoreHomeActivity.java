package com.example.mygrocerystore.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.StoreMainActivity;
import com.example.mygrocerystore.activities.StoreLoginActivity;
import com.example.mygrocerystore.activities.StoreRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StoreHomeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        if (auth.getCurrentUser() != null) {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(StoreHomeActivity.this, StoreMainActivity.class));
            Toast.makeText(this, "please wait you are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public void login(View view) {
        startActivity(new Intent(StoreHomeActivity.this, StoreLoginActivity.class));
    }

    public void registration(View view) {
        startActivity(new Intent(StoreHomeActivity.this, StoreRegistrationActivity.class));
    }
}