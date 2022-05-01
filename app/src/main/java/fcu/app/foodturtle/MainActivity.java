package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void gonext(View view){
        Intent intent = new Intent();
        intent.setClass(this, deliveryOrderActivity.class);
        startActivity(intent);
    }

    public void god(View view){
        Intent intent = new Intent();
        intent.setClass(this, NavigationActivity.class);
        startActivity(intent);
    }

    public void gotoorder(View view){
        Intent intent = new Intent();
        intent.setClass(this, orderOrderActivity.class);
        startActivity(intent);
    }
}