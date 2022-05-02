package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class shopcar2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar2);
    }
    public void viewshop3(View v) {
        Intent intent = new Intent();
        intent.setClass(this,shopcar3Activity.class);
        startActivity(intent);
    }

}