package com.example.mygrocerystore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mygrocerystore.DeliveryManLoginActivity;
import com.example.mygrocerystore.DeliveryManRegistrstionActivity;
import com.example.mygrocerystore.MainActivity;
import com.example.mygrocerystore.R;
import com.google.firebase.auth.FirebaseAuth;

public class DeliveryManHomeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_man_home);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        if (auth.getCurrentUser() != null) {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(DeliveryManHomeActivity.this, MainActivity.class));
            Toast.makeText(this, "please wait you are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void login(View view) {
        startActivity(new Intent(DeliveryManHomeActivity.this, DeliveryManLoginActivity.class));
    }

    public void registration(View view) {
        startActivity(new Intent(DeliveryManHomeActivity.this, DeliveryManRegistrstionActivity.class));
    }
}